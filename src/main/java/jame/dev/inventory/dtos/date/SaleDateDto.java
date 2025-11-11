package jame.dev.inventory.dtos.date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@JsonSerialize
@JsonDeserialize
public record SaleDateDto(
        @JsonProperty("date") LocalDate date,
        @JsonProperty("sales") List<SaleDto> sales,
        @JsonProperty("totalSell") BigDecimal amount
        ) { }
