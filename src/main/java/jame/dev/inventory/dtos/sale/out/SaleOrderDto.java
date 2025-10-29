package jame.dev.inventory.dtos.sale.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
@JsonSerialize
public record SaleOrderDto(
        @JsonProperty("id") Long id,
        @JsonProperty("products") List<ProductDto> products,
        @JsonProperty("customer") CustomerDto customer,
        @JsonProperty("orderCost") BigDecimal orderCost
) {
}

