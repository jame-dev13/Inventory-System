package jame.dev.inventory.dtos.provider.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.math.BigInteger;

@Builder
@JsonDeserialize
public record ProviderInDto (
        @JsonProperty("name") String name,
        @JsonProperty("phone")BigInteger phone,
        @JsonProperty("email") @Email String email
        ){
}
