package jame.dev.inventory.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.jwt.in.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

   @Autowired
   private final JwtService jwtService;
   private final UserDetailsService userDetailsService;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String tokenHeader = request.getHeader("Authorization");
      if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }
      String jwt = tokenHeader.substring(7);
      String username = jwtService.extractUsername(jwt);
      boolean isValid = jwtService.isTokenValid(jwt, username);

      if (!isValid) {
         filterChain.doFilter(request, response);
         return;
      }

      //re-authentication.
      if (SecurityContextHolder.getContext().getAuthentication() != null) {
         filterChain.doFilter(request, response);
         return;
      }

      UserDetails user = userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      filterChain.doFilter(request, response);
   }
}
