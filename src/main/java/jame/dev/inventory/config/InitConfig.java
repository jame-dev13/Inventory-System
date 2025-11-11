package jame.dev.inventory.config;

import jame.dev.inventory.models.dao.EmployeeEntity;
import jame.dev.inventory.models.dao.RoleEntity;
import jame.dev.inventory.models.dao.UserEntity;
import jame.dev.inventory.models.enums.ERole;
import jame.dev.inventory.models.enums.EShift;
import jame.dev.inventory.service.in.EmployeeService;
import jame.dev.inventory.service.in.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

@Configuration
public class InitConfig {

   private static final Logger log = LoggerFactory.getLogger(InitConfig.class);
   @Value("${app.user.admin.email}")
   private String emailAdmin;

   @Value("${app.user.admin.pwd}")
   private String pwdAdmin;

   @Bean
   CommandLineRunner init(UserService service, EmployeeService employeeService, PasswordEncoder encoder) {
      return args -> {
         UserEntity userEntity = UserEntity.builder()
                 .name("admin")
                 .lastName("adm")
                 .email(emailAdmin)
                 .password(encoder.encode(pwdAdmin))
                 .roles(Set.of(new RoleEntity(null, ERole.ADMIN), new RoleEntity(null, ERole.EMPLOYEE)))
                 .active(true)
                 .build();
         UserEntity userPresent = service.getUserByEmail(userEntity.getEmail())
                 .orElse(null);
         if (userPresent != null) {
            return;
         }
         service.save(userEntity);

         EmployeeEntity employee = EmployeeEntity.builder()
                 .user(userEntity)
                 .salary(BigDecimal.valueOf(30_000.00))
                 .shift(EShift.MORNING)
                 .active(true)
                 .build();
         employeeService.save(employee);
         log.info("User admin and Employee saved.");
      };
   }
}
