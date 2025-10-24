package jame.dev.inventory.dtos.auth.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.models.enums.ERole;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonSerialize
@JsonDeserialize
public record TokenResponse(
        @JsonProperty("access") String access,
        @JsonProperty("refresh") String refresh,
        @JsonProperty("subject") String subject,
        @JsonProperty("roles") Set<ERole> role
) {
}
