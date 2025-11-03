package jame.dev.inventory.dtos.provider.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.math.BigInteger;

@Builder
@JsonSerialize
@JsonDeserialize
public record ProviderDto(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("phone") BigInteger phone,
        @JsonProperty("email") @Email String email
        ) {
}
