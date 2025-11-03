package jame.dev.inventory.restController.pub;

import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import jame.dev.inventory.factories.CookieTokenFactory;
import jame.dev.inventory.service.in.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.mapping.auth}")
@AllArgsConstructor
public class AuthenticationController {
   @Autowired
   private final AuthService authService;
   private final CookieTokenFactory cookieFactory;
   private final TokenService tokenService;

   @PostMapping("/signIn")
   public ResponseEntity<TokenResponse> signIn(@RequestBody LoginRequest request,
                                               HttpServletResponse response)
           throws AuthenticationException {
      TokenResponse tokenResponse = authService.login(request, response);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(tokenResponse);
   }

   @PostMapping("/refresh")
   public ResponseEntity<TokenResponse> refreshToken(@CookieValue(value = "_HOST-JWT_REFRESH") final String token,
                                                     HttpServletResponse response)
           throws RefreshTokenException {
      TokenResponse tokenResponse = authService.refresh(token, response);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(tokenResponse);
   }
}
