package jame.dev.inventory.dtos.user.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jame.dev.inventory.models.enums.ERole;
import jame.dev.inventory.models.enums.EShift;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

@JsonDeserialize
@Builder
public record UserEmpInputInfo (
        @JsonProperty("name") String name,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("role") Set<ERole> role,
        @JsonProperty("salary") BigDecimal salary,
        @JsonProperty("shift") EShift shift
){
   public UserEmpInputInfo{
      password = new BCryptPasswordEncoder().encode(password);
   }
}
