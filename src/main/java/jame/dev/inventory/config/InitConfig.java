package jame.dev.inventory.config;

import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.models.enums.ERole;
import jame.dev.inventory.service.in.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class InitConfig {

   private static final Logger log = LoggerFactory.getLogger(InitConfig.class);
   @Value("${app.user.admin.email}")
   private String emailAdmin;

   @Value("${app.user.admin.pwd}")
   private String pwdAdmin;

   @Bean
   CommandLineRunner init(UserService service, PasswordEncoder encoder) {
      return args -> {
         UserEntity userEntity = UserEntity.builder()
                 .id(null)
                 .name("admin")
                 .lastName("adm")
                 .email(emailAdmin)
                 .password(encoder.encode(pwdAdmin))
                 .roles(Set.of(new RoleEntity(null, ERole.ADMIN)))
                 .build();
         UserEntity userPresent = service.getUserByEmail(userEntity.getEmail())
                 .orElse(null);
         if (userPresent != null) {
            return;
         }
         service.save(userEntity);
         log.info("User admin saved.");
      };
   }
}
