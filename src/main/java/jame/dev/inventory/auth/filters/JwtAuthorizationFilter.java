package jame.dev.inventory.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.exceptions.ClaimsNullException;
import jame.dev.inventory.jwt.in.JwtService;
import jame.dev.inventory.models.enums.CookieName;
import jame.dev.inventory.service.in.TokenService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   private final UserDetailsService userDetailsService;
   private final TokenService blacklist;

   @Value("${app.mapping.auth}/refresh")
   private String pathAuth;

   public JwtAuthorizationFilter(JwtService jwtService, UserDetailsService userDetailsService, TokenService blacklist) {
      this.jwtService = jwtService;
      this.userDetailsService = userDetailsService;
      this.blacklist = blacklist;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
           throws ServletException, IOException {

      if(request.getRequestURI().equals(pathAuth)){
         filterChain.doFilter(request, response);
         return;
      }
      Cookie[] cookies = request.getCookies();

      if(cookies == null){
         filterChain.doFilter(request, response);
         return;
      }

      Map<String, String> cookieValues = getTokenFromCookie(cookies);
      String jwtAccess = cookieValues.get(NameValueJwt.ACCESS.getName());

      if(jwtAccess == null){
         sendMessageError(response, "Token expired.");
         return;
      }

      if(blackListHelper(jwtAccess)){
         sendMessageError(response, "Token is revoked.");
         return;
      }

      //token access valid.
      if (isValidTokenHelper(jwtAccess)) {
         String username = jwtService.extractUsername(jwtAccess);
         authorizationHelper(username);
         filterChain.doFilter(request, response);
         return;
      }

      String refreshToken = cookieValues.get(NameValueJwt.REFRESH.getName());
      if(refreshToken == null){
         sendMessageError(response,"Refresh token expired");
         return;
      }

      if(blackListHelper(refreshToken)){
         sendMessageError(response, "Token revoked.");
         return;
      }

      if(isValidTokenHelper(refreshToken)){
         String username = jwtService.extractUsername(refreshToken);
         authorizationHelper(username);
         filterChain.doFilter(request, response);
         return;
      }

      filterChain.doFilter(request, response);
   }

   private  void sendMessageError(final HttpServletResponse response, final String msg) throws IOException {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      final String errorMsg = """
              {"error": "%s"}
              """.formatted(msg);
      response.getWriter().write(errorMsg);
      response.getWriter().flush();
   }

   private void authorizationHelper(final String username) {
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if(SecurityContextHolder.getContext().getAuthentication() == null) {
         UsernamePasswordAuthenticationToken authenticationToken =
                 new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
   }

   private Map<String, String> getTokenFromCookie(final Cookie[] cookies){
      Map<String, String> values = new HashMap<>();
      for (Cookie cookie : cookies) {
         if(cookie.getName().equals(CookieName.JWT_ACCESS.getName())){
            values.put(NameValueJwt.ACCESS.getName(), cookie.getValue());
         } else if (cookie.getName().equals(CookieName.JWT_REFRESH.getName())) {
            values.put(NameValueJwt.REFRESH.getName(), cookie.getValue());
         }
      }
      return values;
   }

   private boolean isValidTokenHelper(final String token){
      String username = Optional.ofNullable(jwtService.extractUsername(token))
              .orElseThrow(() -> new ClaimsNullException("Claims are null, cannot extract, token invalid."));
      return jwtService.isTokenValid(token, username);
   }

   private boolean blackListHelper(final String token){
      return blacklist.contains(token);
   }

   @Getter
   private enum NameValueJwt{
      ACCESS("access"), REFRESH("refresh");
      private final String name;
      NameValueJwt(final String name){
         this.name = name;
      }
   }
}
