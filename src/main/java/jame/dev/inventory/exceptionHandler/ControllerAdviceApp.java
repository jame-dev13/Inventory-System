package jame.dev.inventory.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceApp {
   @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handlerException(Exception ex){
      return ResponseEntity.badRequest().build();
   }
}
