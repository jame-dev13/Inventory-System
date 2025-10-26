package jame.dev.inventory.auth.out;

import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.service.in.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private final UserService service;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserEntity userEntity = service.getUserByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found."));
      Set<? extends GrantedAuthority> authorities = userEntity.getRoles()
              .stream()
              .map(r -> new SimpleGrantedAuthority("ROLE_".concat(r.getRole().name())))
              .collect(Collectors.toSet());
      return User.builder()
              .username(userEntity.getEmail())
              .password(userEntity.getPassword())
              .disabled(false)
              .accountExpired(false)
              .credentialsExpired(false)
              .accountLocked(false)
              .authorities(authorities)
              .build();

   }
}
