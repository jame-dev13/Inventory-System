package jame.dev.inventory.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.exceptions.TokenReusedException;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.service.in.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   private final UserDetailsService userDetailsService;
   private final TokenService blacklist;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String tokenHeader = request.getHeader("Authorization");
      if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }

      String jwt = tokenHeader.substring(7);
//      System.out.println(blacklist.contains(jwt));
      if(blacklist.contains(jwt)){
         throw new TokenReusedException("Token revoked.");
      }
      String username = Optional.ofNullable(jwtService.extractUsername(jwt))
              .orElseThrow(() -> new NullPointerException("Cannot extract, claims are null."));
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
