package jame.dev.inventory.dtos.date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.util.List;

@Builder
@JsonSerialize
public record SellerNameDto(
        @JsonProperty("sellerNames") List<String> names) { }
