package jame.dev.inventory.auth.out;

import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.in.RegisterRequest;
import jame.dev.inventory.dtos.auth.out.RegisterResponse;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.exceptions.RefreshTokenException;
import jame.dev.inventory.exceptions.UserAlreadyExistsException;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.models.enums.ERole;
import jame.dev.inventory.service.in.UserService;
import jame.dev.inventory.utils.TokenGeneratorUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

   @Autowired
   private final UserService userService;
   private final JwtService jwtService;
   private final AuthenticationManager auth;

   @Override
   public RegisterResponse register(RegisterRequest request) throws UserAlreadyExistsException {
      UserEntity existingUser = userService.getUserByEmail(request.email()).orElse(null);
      if (existingUser != null) {
         throw new UserAlreadyExistsException("User already exists.");
      }
      UserEntity userEntity = userService.save(
              UserEntity.builder()
                      .name(request.name())
                      .lastName(request.lastName())
                      .email(request.email())
                      .password(request.password())
                      .token(TokenGeneratorUtil.generate())
                      .verified(false)
                      .roles(Set.of(new RoleEntity(null, ERole.valueOf(request.role().name().split("_")[1]))))
                      .build()
      );
      return RegisterResponse.builder()
              .name(userEntity.getName() + " " + userEntity.getLastName())
              .email(userEntity.getEmail())
              .build();
   }

   @Override
   public TokenResponse login(LoginRequest request) throws AuthenticationException {
      try {
         Authentication authentication = new UsernamePasswordAuthenticationToken(
                 request.email(), request.password());
         Authentication authenticated = auth.authenticate(authentication);

         User user = (User) authenticated.getPrincipal();
         Set<ERole> roles = user.getAuthorities().stream()
                 .map(r -> r.getAuthority().substring(5))
                 .map(ERole::valueOf)
                 .collect(Collectors.toSet());
         String access = jwtService.generateAccessToken(user.getUsername());
         String refresh = jwtService.generateRefreshToken(user.getUsername());

         return TokenResponse.builder()
                 .access(access)
                 .refresh(refresh)
                 .build();
      } catch (BadCredentialsException e) {
         throw new BadCredentialsException("Invalid Credentials.");
      }
   }

   @Override
   public TokenResponse refresh(String refreshToken) throws RefreshTokenException {
      if(refreshToken == null || !refreshToken.startsWith("Bearer ")){
         throw new IllegalArgumentException("Invalid Token format.");
      }
      String jwtRefresh = refreshToken.substring(7);
      String subject = Optional.ofNullable(jwtService.extractUsername(jwtRefresh))
              .orElseThrow(() -> new NoSuchElementException("No subject present."));

      UserEntity userEntity = userService.getUserByEmail(subject).orElseThrow(() ->
              new UsernameNotFoundException("User not found."));

      if(!jwtService.isTokenValid(jwtRefresh, userEntity.getEmail())){
         throw new RefreshTokenException("Refresh token is invalid or expired.");
      }
      String newAccessToken = jwtService.generateAccessToken(userEntity.getEmail());
      String newRefreshToken = jwtService.generateRefreshToken(userEntity.getEmail());
      return new TokenResponse(newAccessToken, newRefreshToken);
   }

}
