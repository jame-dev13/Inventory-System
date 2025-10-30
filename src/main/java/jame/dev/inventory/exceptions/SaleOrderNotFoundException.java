package jame.dev.inventory.exceptions;

import java.util.NoSuchElementException;

public class SaleOrderNotFoundException extends NoSuchElementException {
   public SaleOrderNotFoundException(String message) {
      super(message);
   }
}
