package jame.dev.inventory.config;

import jakarta.servlet.http.HttpServletResponse;
import jame.dev.inventory.auth.filters.JwtAuthorizationFilter;
import jame.dev.inventory.auth.in.LogoutService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebConfig {

   @Autowired
   private final JwtAuthorizationFilter filter;
   private final LogoutService logoutService;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
              .csrf(AbstractHttpConfigurer::disable)
              .cors(cors -> corsConfigurationSource())
              .authorizeHttpRequests(r ->
                      r.requestMatchers("/v1/auth/**")
                              .permitAll()
                              .anyRequest()
                              .authenticated())
              .sessionManagement(s ->
                      s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
              .logout(logout -> {
                 logout.logoutUrl("/v1/auth/logout")
                         .addLogoutHandler((request, response, authentication) -> {
                            String authHeader = request.getHeader("Authorization");
                            logoutService.logout(authHeader);
                         })
                         .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                            SecurityContextHolder.clearContext();
                         });
              })
              .build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
      DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider(userDetailsService);
      daoAuth.setPasswordEncoder(passwordEncoder);
      return new ProviderManager(daoAuth);
   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration cors = new CorsConfiguration();
      cors.setAllowedHeaders(List.of("Content-Type", "Access-Control-Allow-Headers", "Authorization"));
      cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
      cors.setAllowedOrigins(List.of("http://localhost:5173"));
      cors.setAllowCredentials(true);
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", cors);
      return source;
   }

}
