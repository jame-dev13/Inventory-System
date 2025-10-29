package jame.dev.inventory.exceptions;

import java.util.NoSuchElementException;

public class ProviderProductNotFoundException extends NoSuchElementException {
   public ProviderProductNotFoundException(String message) {
      super(message);
   }
}
