package jame.dev.inventory.exceptionHandler;

import jame.dev.inventory.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(UserAlreadyExistsException.class)
   public ResponseEntity<Map<String, String>> handleIUserAlreadyExistsException(UserAlreadyExistsException ex){
      return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(Map.of("error", ex.getMessage()));
   }

   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Map<String, String>> handelIllegalArgumentException(IllegalArgumentException ex){
      return ResponseEntity
              .badRequest()
              .body(Map.of("error", "Not valid arguments." + ex.getMessage()));
   }

   @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex){
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Authentication Error: " + ex.getMessage()));
   }

   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex){
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Bad credentials: " + ex.getMessage()));
   }

//   @ExceptionHandler(Exception.class)
//   public ResponseEntity<Map<String, String>> handleInternalServerError(Exception ex){
//      return ResponseEntity
//              .status(HttpStatus.INTERNAL_SERVER_ERROR)
//              .body(Map.of("error", "Internal server error."));
//   }
}
