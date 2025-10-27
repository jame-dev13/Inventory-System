package jame.dev.inventory.factories;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieFactory {

   private static final String COOKIE_NAME = "_HOST-JWT_ACCESS";

   @Value("${app.path}")
   private String path;

   @Value("${jwt.secret.expiration}")
   private long expiration;

   public Cookie createCookie(String token){
      Cookie cookie = new Cookie(COOKIE_NAME, token);
      cookie.setSecure(false);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge((int) expiration / 1000);
      return cookie;
   }

   public void clearCookie(){
      Cookie cookie = new Cookie(COOKIE_NAME, "");
      cookie.setSecure(false);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge(0);
   }
}
