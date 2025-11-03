package jame.dev.inventory.dtos.sale.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jame.dev.inventory.dtos.product.out.ProductDto;
import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize
public record SaleOrderInDto(
        @JsonProperty("products") List<ProductDto> productList,
        @JsonProperty("customerId") Long customerId
        ) {
}
