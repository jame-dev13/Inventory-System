package jame.dev.inventory.auth.in;

import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
   TokenResponse login(LoginRequest request, HttpServletResponse response) throws AuthenticationException;
   TokenResponse refresh(String token, HttpServletResponse response) throws RefreshTokenException;
}
