package jame.dev.inventory.exceptions;

import java.util.NoSuchElementException;

public class SaleDateNotFound extends NoSuchElementException {
   public SaleDateNotFound(String message) {
      super(message);
   }
}
