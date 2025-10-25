package jame.dev.inventory.service.out;

import jame.dev.inventory.exceptions.TokenAlreadyBlacklisted;
import jame.dev.inventory.service.in.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@Service
@AllArgsConstructor
public class TokenServiceImp implements TokenService {

   @Autowired
   private final JedisPooled jedis;

   @Override
   public void save(String token, long ttl) throws TokenAlreadyBlacklisted {
      if(jedis.exists(token)){
         throw new TokenAlreadyBlacklisted("Token is blacklisted.");
      }
      String res = jedis.setex(token, ttl,"blacklisted");
      System.out.println(res);
   }

   @Override
   public boolean contains(String token) {
      return jedis.exists(token);
   }
}
