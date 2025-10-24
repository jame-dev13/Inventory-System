package jame.dev.inventory.auth.in;

import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.in.RegisterRequest;
import jame.dev.inventory.dtos.auth.out.RegisterResponse;
import jame.dev.inventory.dtos.auth.out.TokenResponse;

public interface AuthService {
   RegisterResponse register(RegisterRequest request) throws IllegalAccessException;
   TokenResponse login(LoginRequest request);
}
