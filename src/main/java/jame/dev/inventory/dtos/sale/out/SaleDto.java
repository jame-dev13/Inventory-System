package jame.dev.inventory.dtos.sale.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@JsonSerialize
public record SaleDto(
        @JsonProperty("id") Long id,
        @JsonProperty("orders") List<SaleOrderDto> orders,
        @JsonProperty("employee") EmployeeDto employee,
        @JsonProperty("saleDate") LocalDate saleDate,
        @JsonProperty("saleCost") BigDecimal saleCost
        ) {
}
