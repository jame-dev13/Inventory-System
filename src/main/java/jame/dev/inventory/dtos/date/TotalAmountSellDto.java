package jame.dev.inventory.dtos.date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonSerialize
public record TotalAmountSellDto(
        @JsonProperty("total") BigDecimal total
        ) { }
