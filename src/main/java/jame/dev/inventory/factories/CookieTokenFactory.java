package jame.dev.inventory.factories;

import jakarta.servlet.http.Cookie;
import jame.dev.inventory.models.enums.ECookieName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieTokenFactory {

   private static final String COOKIE_ACCESS_NAME = ECookieName.JWT_ACCESS.getName();
   private static final String COOKIE_REFRESH_NAME= ECookieName.JWT_REFRESH.getName();

   @Value("${jwt.secret.expiration}")
   private long expirationAccess;

   @Value("${jwt.refresh.expiration}")
   private long expirationRefresh;

   public Cookie createAccessCookie(String token){
      return buildCookie(COOKIE_ACCESS_NAME, token, expirationAccess);
   }

   public Cookie createtRefreshCookie(String refresh){
      return buildCookie(COOKIE_REFRESH_NAME, refresh, expirationRefresh);
   }

   public void clearAccessCookie(){
      clearCookie(COOKIE_ACCESS_NAME);
   }

   public void clearRefreshCookie(){
      clearCookie(COOKIE_REFRESH_NAME);
   }

   private void clearCookie(String name){
      Cookie cookie = new Cookie(name, "");
      cookie.setSecure(false);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge(0);
   }

   private Cookie buildCookie(String name, String value, long expiration){
      Cookie cookie = new Cookie(name, value);
      cookie.setSecure(false);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge((int) expiration / 1000);
      return cookie;
   }
}
