package jame.dev.inventory.auth.out;

import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import jame.dev.inventory.exceptions.UserNotFoundException;
import jame.dev.inventory.factories.CookieTokenFactory;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.service.in.TokenService;
import jame.dev.inventory.service.in.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

   private final UserService userService;
   private final JwtService jwtService;
   private final AuthenticationManager auth;
   private final CookieTokenFactory cookieTokenFactory;
   private final TokenService tokenService;

   public AuthServiceImpl(UserService userService, JwtService jwtService, AuthenticationManager auth, CookieTokenFactory cookieTokenFactory, TokenService tokenService) {
      this.userService = userService;
      this.jwtService = jwtService;
      this.auth = auth;
      this.cookieTokenFactory = cookieTokenFactory;
      this.tokenService = tokenService;
   }

   @Override
   public TokenResponse login(LoginRequest request, HttpServletResponse response) throws AuthenticationException {
      try {
         Authentication authentication = new UsernamePasswordAuthenticationToken(
                 request.email(), request.password());
         Authentication authenticated = auth.authenticate(authentication);

         User user = (User) authenticated.getPrincipal();
         String access = jwtService.generateAccessToken(user.getUsername());
         String refresh = jwtService.generateRefreshToken(user.getUsername());

         setUpCookies(access, refresh, response);

         return TokenResponse.builder()
                 .access(access)
                 .refresh(refresh)
                 .build();
      } catch (BadCredentialsException e) {
         throw new BadCredentialsException("Provided credentials are invalid.");
      }
   }

   @Override
   public TokenResponse refresh(String refreshToken, HttpServletResponse response) throws RefreshTokenException {
      String subject = Optional.ofNullable(jwtService.extractUsername(refreshToken))
              .orElseThrow(() -> new NullPointerException("No subject present."));

      UserEntity userEntity = userService.getUserByEmail(subject).orElseThrow(() ->
              new UserNotFoundException("User not found."));

      if (!jwtService.isTokenValid(refreshToken, userEntity.getEmail())) {
         throw new RefreshTokenException("Refresh token is invalid or expired.");
      }

      //blackList token
      tokenService.blacklistToken(refreshToken);

      String newAccessToken = jwtService.generateAccessToken(userEntity.getEmail());
      String newRefreshToken = jwtService.generateRefreshToken(userEntity.getEmail());

      this.setUpCookies(newAccessToken, newRefreshToken, response);

      return new TokenResponse(newAccessToken, newRefreshToken);
   }


   /**
    * Generates access token cookie and refresh token cookie and set their to the {@link HttpServletResponse} object.
    * See {@link CookieTokenFactory}
    * @param access the Jwt Access token.
    * @param refresh the Jwt Refresh token.
    * @param response the Http response.
    */
   private void setUpCookies(String access, String refresh, HttpServletResponse response){
      response.addCookie(cookieTokenFactory.createAccessCookie(access));
      response.addCookie(cookieTokenFactory.createtRefreshCookie(refresh));
   }
}
