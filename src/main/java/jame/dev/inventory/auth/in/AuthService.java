package jame.dev.inventory.auth.in;

import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
   TokenResponse login(LoginRequest request) throws AuthenticationException;
   TokenResponse refresh(String token) throws RefreshTokenException;
}
