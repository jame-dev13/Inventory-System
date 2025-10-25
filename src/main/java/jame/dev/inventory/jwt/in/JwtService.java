package jame.dev.inventory.jwt.in;

import java.util.Date;

public interface JwtService {
   String buildToken(String username, long expiration);
   String generateAccessToken(String username);
   String generateRefreshToken(String username);
   boolean isTokenValid(String jwt, String username);
   boolean isExpired(String jwt);
   String extractUsername(String jwt);
   Date extractExpiration(String jwt);
}
