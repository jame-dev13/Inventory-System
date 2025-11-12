package jame.dev.inventory.jwt.out;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jame.dev.inventory.jwt.in.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImp implements JwtService {
   @Value("${jwt.secret.key}")
   private String SECRET_KEY;

   @Value("${jwt.secret.expiration}")
   private String SECRET_EXPIRATION;

   @Value("${jwt.refresh.expiration}")
   private String REFRESH_EXPIRATION;

   private Key signSecret() {
      byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(bytes);
   }

   private <T> T getClaim(String jwt, Function<Claims, T> function) {
      try {
         Claims claims = Jwts.parserBuilder()
                 .setSigningKey(signSecret())
                 .build()
                 .parseClaimsJws(jwt)
                 .getBody();
         return function.apply(claims);
      } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException e) {
         log.error("Cannot parse claims fot the give JWT. ", e);
         return null;
      }
   }

   @Override
   public String buildToken(String username, long expiration) {
      return Jwts.builder()
              .signWith(signSecret())
              .setSubject(username)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .compact();
   }

   @Override
   public String generateAccessToken(String username) {
      return buildToken(username, Long.parseLong(SECRET_EXPIRATION));
   }

   @Override
   public String generateRefreshToken(String username) {
      return buildToken(username, Long.parseLong(REFRESH_EXPIRATION));
   }

   @Override
   public boolean isTokenValid(String jwt, String username) {
      try {
         return isSameSubject(jwt, username) && !isExpired(jwt);
      } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException e) {
         return false;
      }
   }

   @Override
   public boolean isExpired(String jwt) {
      Date expiration = extractExpiration(jwt);
      if(expiration == null){
         return true; // expired because getClaim returned true
      }
      return expiration.before(new Date());
   }

   @Override
   public String extractUsername(String jwt) {
      return getClaim(jwt, Claims::getSubject);
   }

   @Override
   public Date extractExpiration(String jwt) {
      return getClaim(jwt, Claims::getExpiration);
   }

   private boolean isSameSubject(String jwt, String username){
      String subject = extractUsername(jwt);
      if(subject == null){
         return false;
      }
      return Objects.equals(subject, username);
   }
}
