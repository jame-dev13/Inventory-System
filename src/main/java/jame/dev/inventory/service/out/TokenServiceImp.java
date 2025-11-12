package jame.dev.inventory.service.out;

import jame.dev.inventory.exceptions.TokenAlreadyBlacklisted;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.service.in.TokenService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@Service
public class TokenServiceImp implements TokenService {

   private final JedisPooled jedis;
   private final JwtService jwtService;

   public TokenServiceImp(JedisPooled jedis, JwtService jwtService) {
      this.jedis = jedis;
      this.jwtService = jwtService;
   }

   @Override
   public void save(String token, long ttl) {
      if(jedis.exists(token)){
         throw new TokenAlreadyBlacklisted("Token is blacklisted.");
      }
      jedis.setex(token, ttl,"blacklisted");
   }

   @Override
   public boolean contains(String token) {
      return jedis.exists(token);
   }

   @Override
   public void blacklistToken(String token) {
      if(jedis.exists(token)){
         throw new TokenAlreadyBlacklisted("Token is blacklisted.");
      }
      long expiration = jwtService.extractExpiration(token).getTime();
      long ttl = (expiration - System.currentTimeMillis()) / 1000;
      jedis.setex(token, ttl, "blacklisted");
   }
}
