package com.quantity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {
    @NotNull(message = "Value is required")
    private Double value;

    @NotBlank(message = "Unit is required")
    private String unit;      // e.g. "METER", "KILOGRAM"

    @NotBlank(message = "Measurement type is required")
    private String type;      // LENGTH | WEIGHT | VOLUME | TEMPERATURE

    // For conversion: target unit
    private String targetUnit;

    // For binary ops (add/subtract/divide): second operand
    private Double secondValue;
    private String secondUnit;
}
