package jame.dev.inventory.exceptions;

public class TokenAlreadyBlacklisted extends RuntimeException {
   public TokenAlreadyBlacklisted(String message) {
      super(message);
   }
}
