package jame.dev.inventory.dtos.employee.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jame.dev.inventory.models.enums.EJobTitle;
import jame.dev.inventory.models.enums.EShift;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonSerialize
public record EmployeeDto(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("jobTitle")EJobTitle jobTitle,
        @JsonProperty("salary")BigDecimal salary,
        @JsonProperty("shift")EShift shift
        ) {
}
