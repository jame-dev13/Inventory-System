package jame.dev.inventory.exceptions;

import io.jsonwebtoken.JwtException;

public class TokenExpiredException extends JwtException {
   public TokenExpiredException(String message) {
      super(message);
   }
}
