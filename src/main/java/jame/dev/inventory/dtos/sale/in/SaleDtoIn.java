package jame.dev.inventory.dtos.sale.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize
public record SaleDtoIn(
        @JsonProperty("orders") List<SaleOrderDto> orders,
        @JsonProperty("employeeId") Long employeeId
) {
}
