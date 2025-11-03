package jame.dev.inventory.exceptions;

import io.jsonwebtoken.JwtException;

public class ClaimsNullException extends JwtException {
   public ClaimsNullException(String message) {
      super(message);
   }
}
