package jame.dev.inventory.dtos.product.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonSerialize
@JsonDeserialize
public record ProductDto(
        @JsonProperty("id") Long id,
        @JsonProperty("desc") String description,
        @JsonProperty("stock") int stock,
        @JsonProperty("idProvider") Long idProvider,
        @JsonProperty("unitPrice") BigDecimal unitPrice
        ) {
}
