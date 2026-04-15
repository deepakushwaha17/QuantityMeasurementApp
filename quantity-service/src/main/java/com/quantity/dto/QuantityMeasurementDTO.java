package com.quantity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {
    private Double result;
    private String unit;
    private String type;
    private String operation;
    private String description;
    private LocalDateTime timestamp;
}