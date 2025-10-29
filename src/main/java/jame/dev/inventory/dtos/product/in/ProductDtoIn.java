package jame.dev.inventory.dtos.product.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonDeserialize
public record ProductDtoIn(
        @JsonProperty("desc") String description,
        @JsonProperty("stock") int stock,
        @JsonProperty("idProvider") Long providerId,
        @JsonProperty("unitPrice") BigDecimal unitPrice
) {
}
