package jame.dev.inventory.auth.out;

import jame.dev.inventory.models.dao.UserEntity;
import jame.dev.inventory.service.in.UserService;
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
public class UserDetailsServiceImpl implements UserDetailsService {

   private final UserService service;
   public UserDetailsServiceImpl(UserService service) {
      this.service = service;
   }

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
