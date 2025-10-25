package jame.dev.inventory.auth.out;

import jame.dev.inventory.auth.in.LogoutService;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.service.in.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class LogoutServiceImp implements LogoutService {
   private final TokenService tokenService;
   private final JwtService jwtService;

   @Override
   public void logout(String token) {
      if(token == null || !token.startsWith("Bearer ")){
         throw new IllegalArgumentException("Token format invalid.");
      }
      String jwt = token.substring(7);
      Date expirationDate = jwtService.extractExpiration(jwt);
      long ttlSeconds = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;

      if (ttlSeconds > 0) {
         tokenService.save(jwt, ttlSeconds);
         System.out.println("Token blacklisted for " + ttlSeconds + " seconds");
      } else {
         System.out.println("Token already expired, no need to blacklist");
      }
   }
}
