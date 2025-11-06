package jame.dev.inventory.dtos.user.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jame.dev.inventory.models.enums.ERole;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Builder
@JsonDeserialize
public record UserDtoIn(
        @JsonProperty("name") String name,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("role") Set<ERole> role
) {
   public UserDtoIn {
      password = new BCryptPasswordEncoder().encode(password);
   }
}
