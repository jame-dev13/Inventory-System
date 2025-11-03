package jame.dev.inventory.dtos.employee.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.models.enums.EShift;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonSerialize
public record EmployeeDto(
        @JsonProperty("id") Long id,
        @JsonProperty("idUser") Long idUser,
        @JsonProperty("name") String name,
        @JsonProperty("salary") BigDecimal salary,
        @JsonProperty("shift") EShift shift
        ) {
}
