package jame.dev.inventory.dtos.auth.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
@JsonDeserialize
public record TokenResponse(
        @JsonProperty("access") String access,
        @JsonProperty("refresh") String refresh
) {
}
