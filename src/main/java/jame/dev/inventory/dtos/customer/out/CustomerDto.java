package jame.dev.inventory.dtos.customer.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.math.BigInteger;

@Builder
@JsonSerialize
public record CustomerDto(
        @JsonProperty("id") long id,
        @JsonProperty("fullName") String name,
        @JsonProperty("email") @Email String email,
        @JsonProperty("phone") BigInteger phone,
        @JsonProperty("age") int age
) {
}
