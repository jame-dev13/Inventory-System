package jame.dev.inventory.dtos.sale.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize
public record SaleDtoIn(
        @JsonProperty("orderIds") List<Long> saleOrderIds,
        @JsonProperty("employeeId") Long employeeId
) {
}
