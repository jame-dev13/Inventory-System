package jame.dev.inventory.dtos.auth.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
@JsonSerialize
public record RegisterResponse(
        @JsonProperty("name") String name,
        @JsonProperty("email")
        @Email String email
) {
}
