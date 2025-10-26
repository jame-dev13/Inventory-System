package jame.dev.inventory.auth.out;

import jame.dev.inventory.auth.in.LogoutService;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.service.in.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LogoutServiceImp implements LogoutService {
   private final TokenService tokenService;
   private final JwtService jwtService;

   @Override
   public void logout(String token) {
      if (token == null || !token.startsWith("Bearer ")) {
         throw new IllegalArgumentException("Token format invalid.");
      }
      String jwt = token.substring(7);
      Date expirationDate = Optional.ofNullable(jwtService.extractExpiration(jwt))
              .orElseThrow(() -> new NullPointerException("Cannot extract expiration, claims are null."));
      long ttlSeconds = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;

      if (ttlSeconds > 0) {
         tokenService.save(jwt, ttlSeconds);
         System.out.println("Token blacklisted for " + ttlSeconds + " seconds");
         return;
      }

      System.out.println("Token already expired, no need to blacklist");
   }
}
