package jame.dev.inventory.exceptions;

import java.util.NoSuchElementException;

public class SaleNotFoundException extends NoSuchElementException {
   public SaleNotFoundException(String message) {
      super(message);
   }
}
