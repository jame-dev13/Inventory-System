package jame.dev.inventory.dtos.user.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.models.enums.ERole;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonSerialize
public record UserDto(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("role") Set<ERole> role
) {
}
