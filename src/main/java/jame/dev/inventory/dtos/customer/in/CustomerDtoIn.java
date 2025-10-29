package jame.dev.inventory.dtos.customer.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.math.BigInteger;

@Builder
@JsonDeserialize
public record CustomerDtoIn(
        @JsonProperty("name") String name,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("phone") BigInteger phone,
        @JsonProperty("age") int age
) {
}
