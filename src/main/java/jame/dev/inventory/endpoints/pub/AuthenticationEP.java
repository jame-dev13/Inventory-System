package jame.dev.inventory.endpoints.pub;

import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.dtos.auth.in.RegisterRequest;
import jame.dev.inventory.dtos.auth.out.RegisterResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthenticationEP {

   @Autowired
   private final AuthService authService;

   @PostMapping("/signUp")
   public ResponseEntity<RegisterResponse> signUp(@RequestBody RegisterRequest request) throws IllegalAccessException {
      RegisterResponse response = authService.register(request);
      return ResponseEntity.ok(response);
   }
}
