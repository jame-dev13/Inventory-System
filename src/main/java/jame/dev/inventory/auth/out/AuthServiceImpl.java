package jame.dev.inventory.auth.out;

import jame.dev.inventory.auth.in.AuthService;
import jame.dev.inventory.dtos.auth.in.LoginRequest;
import jame.dev.inventory.dtos.auth.in.RegisterRequest;
import jame.dev.inventory.dtos.auth.out.RegisterResponse;
import jame.dev.inventory.dtos.auth.out.TokenResponse;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.models.enums.ERole;
import jame.dev.inventory.service.in.IUserService;
import jame.dev.inventory.utils.TokenGeneratorUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

   private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
   @Autowired
   private final IUserService userService;
   private final JwtService jwtService;
   private final AuthenticationManager auth;

   @Override
   public RegisterResponse register(RegisterRequest request) throws IllegalAccessException {
      UserEntity existingUser = userService.getUserByEmail(request.email()).orElse(null);
      if(existingUser != null){
         throw new IllegalAccessException("User already exists.");
      }
      UserEntity userEntity = userService.save(
              UserEntity.builder()
                      .name(request.name())
                      .lastName(request.lastName())
                      .email(request.email())
                      .password(request.password())
                      .token(TokenGeneratorUtil.generate())
                      .verified(false)
                      .roles(Set.of(new RoleEntity(null, request.role())))
                      .build()
      );
      return RegisterResponse.builder()
              .name(userEntity.getName() + " " + userEntity.getLastName())
              .email(userEntity.getEmail())
              .build();
   }

   @Override
   public TokenResponse login(LoginRequest request) {
      try {
         Authentication authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
         Authentication authenticated = auth.authenticate(authentication);
         User user = (User) authenticated.getPrincipal();
         String access = jwtService.getAccessToken(user.getUsername());
         String refresh = jwtService.getRefreshToken(user.getUsername());
         return TokenResponse.builder()
                 .access(access)
                 .refresh(refresh)
                 .subject(user.getUsername())
                 .role(user.getAuthorities()
                         .stream()
                         .map(a -> ERole.valueOf(a.getAuthority()))
                         .collect(Collectors.toSet()))
                 .build();
      } catch (AuthenticationException e) {
         return null;
      } finally {
         log.info("Authentication attempt by: {}", request.email());
      }
   }
}
