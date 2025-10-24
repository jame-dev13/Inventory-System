package jame.dev.inventory.dtos.auth.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jame.dev.inventory.models.enums.ERole;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Builder
@JsonDeserialize
public record RegisterRequest(
        @JsonProperty("name") String name,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") @Email String email,
        @JsonProperty("password") String password,
        @JsonProperty("role") ERole role) {
   public RegisterRequest {
      password = new BCryptPasswordEncoder().encode(password);
   }
}
