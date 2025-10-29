package jame.dev.inventory.exceptions;

import java.util.NoSuchElementException;

public class CustomerNotFoundException extends NoSuchElementException {
   public CustomerNotFoundException(String message) {
      super(message);
   }
}
