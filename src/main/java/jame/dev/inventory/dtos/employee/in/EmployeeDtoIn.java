package jame.dev.inventory.dtos.employee.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jame.dev.inventory.models.enums.EShift;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonDeserialize
public record EmployeeDtoIn(
        @JsonProperty("idUser") Long idUser,
        @JsonProperty("salary") BigDecimal salary,
        @JsonProperty("shift") EShift shift
        ) {
}
