package jame.dev.inventory.restController.pub;

import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.auth.in.LogoutService;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

   @Autowired
   private final AuthService authService;
   private final LogoutService logoutService;

   @PostMapping("/signIn")
   public ResponseEntity<TokenResponse> signIn(@RequestBody LoginRequest request)
           throws AuthenticationException {
      TokenResponse response = authService.login(request);
      return ResponseEntity.ok()
              .header("Authorization", "Bearer " + response.access())
              .body(response);
   }

   @PostMapping("/refresh")
   public ResponseEntity<TokenResponse> refreshToken(@RequestHeader("X-Refresh-Token") final String token)
           throws RefreshTokenException {
      TokenResponse response = authService.refresh(token);
      return ResponseEntity.ok()
              .header("Authorization", "Bearer " + response.access())
              .body(response);
   }
}
