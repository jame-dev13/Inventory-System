package jame.dev.inventory.exceptionHandler;

import jame.dev.inventory.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

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
              .body(Map.of("error", "Not valid arguments: " + ex.getMessage()));
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

   @ExceptionHandler(NullPointerException.class)
   public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException ex){
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
              .body(Map.of("error", "No content associated."));
   }

   @ExceptionHandler(RefreshTokenException.class)
   public ResponseEntity<Map<String, String>> handleRefreshTokenException(RefreshTokenException ex){
      return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(Map.of("error", "Cannot do refresh tokens now."));
   }

   @ExceptionHandler(UsernameNotFoundException.class)
   public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", "Username not found."));
   }

   @ExceptionHandler(NoSuchElementException.class)
   public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(NoSuchElementException ex){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", "Resource not found."));
   }

   @ExceptionHandler(TokenReusedException.class)
   public ResponseEntity<Map<String, String>> handleTokenReusedException(TokenReusedException ex){
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Token Exception: " + ex.getMessage()));
   }
   @ExceptionHandler(TokenAlreadyBlacklisted.class)
   public ResponseEntity<Map<String, String>> handleTokenAlreadyBlackListedException(TokenAlreadyBlacklisted ex){
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", ex.getMessage()));
   }

   @ExceptionHandler(ProviderProductNotFoundException.class)
   public ResponseEntity<Map<String, String>> handleProvideProductNotFoundException(ProviderProductNotFoundException ex){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", ex.getMessage()));
   }

   @ExceptionHandler(ProductNotFoundException.class)
   public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", ex.getMessage()));
   }

   @ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex){
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
              .body(Map.of("error", "Access denied."));
   }

   @ExceptionHandler(SaleOrderNotFoundException.class)
   public ResponseEntity<Map<String, String>> handleSaleOrderNotFoundException(SaleOrderNotFoundException ex){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", "Sale order not found."));
   }

//   @ExceptionHandler(Exception.class)
//   public ResponseEntity<Map<String, String>> handleInternalServerError(Exception ex){
//      return ResponseEntity
//              .status(HttpStatus.INTERNAL_SERVER_ERROR)
//              .body(Map.of("error", "Internal server error."));
//   }
}
