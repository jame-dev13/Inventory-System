package jame.dev.inventory.dtos.auth.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
@JsonDeserialize
public record LoginRequest(
        @JsonProperty("email") @Email String email,
        @JsonProperty("password") String password
) {
}
