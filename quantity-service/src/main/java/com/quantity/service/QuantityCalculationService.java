package com.quantity.service;

import com.quantity.dto.QuantityInputDTO;
import com.quantity.dto.QuantityMeasurementDTO;
import com.quantity.enums.LengthUnit;
import com.quantity.enums.TemperatureUnit;
import com.quantity.enums.VolumeUnit;
import com.quantity.enums.WeightUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class QuantityCalculationService {

    // ──────────────── CONVERT ────────────────
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {
        double result = switch (input.getType().toUpperCase()) {
            case "LENGTH" -> convertLength(input.getValue(),
                    LengthUnit.valueOf(input.getUnit()),
                    LengthUnit.valueOf(input.getTargetUnit()));

            case "WEIGHT" -> convertWeight(input.getValue(),
                    WeightUnit.valueOf(input.getUnit()),
                    WeightUnit.valueOf(input.getTargetUnit()));

            case "VOLUME" -> convertVolume(input.getValue(),
                    VolumeUnit.valueOf(input.getUnit()),
                    VolumeUnit.valueOf(input.getTargetUnit()));

            case "TEMPERATURE" -> convertTemperature(input.getValue(),
                    TemperatureUnit.valueOf(input.getUnit()),
                    TemperatureUnit.valueOf(input.getTargetUnit()));

            default -> throw new IllegalArgumentException("Unknown type: " + input.getType());
        };

        return QuantityMeasurementDTO.builder()
                .result(result)
                .unit(input.getTargetUnit())
                .type(input.getType())
                .operation("CONVERT")
                .description(input.getValue() + " " + input.getUnit() +
                        " = " + result + " " + input.getTargetUnit())
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── COMPARE ────────────────
    public QuantityMeasurementDTO compare(QuantityInputDTO input) {

        double val1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double val2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        boolean isEqual = Math.abs(val1 - val2) < 1e-5;

        return QuantityMeasurementDTO.builder()
                .result((double) (isEqual ? 1 : 0))   // keep double
                .unit("BOOLEAN_RESULT")
                .type(input.getType())
                .operation("COMPARE")
                .description(isEqual ? "EQUAL" : "NOT_EQUAL")
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── ADD ────────────────
    public QuantityMeasurementDTO add(QuantityInputDTO input) {
        double v1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double v2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        double sum = v1 + v2;
        String baseUnit = getBaseUnit(input.getType());

        return QuantityMeasurementDTO.builder()
                .result(sum)
                .unit(baseUnit)
                .type(input.getType())
                .operation("ADD")
                .description(input.getValue() + " " + input.getUnit()
                        + " + " + input.getSecondValue() + " " + input.getSecondUnit()
                        + " = " + sum + " " + baseUnit)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public QuantityMeasurementDTO addWithTarget(QuantityInputDTO input) {

        double v1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double v2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        double sum = v1 + v2;

        // convert result to target unit (if provided, else base unit)
        String targetUnit = input.getTargetUnit() != null
                ? input.getTargetUnit()
                : getBaseUnit(input.getType());

        double finalResult = fromBase(sum, targetUnit, input.getType());;

        return QuantityMeasurementDTO.builder()
                .result(finalResult)
                .unit(targetUnit)
                .type(input.getType())
                .operation("ADD_WITH_TARGET")
                .description(input.getValue() + " " + input.getUnit()
                        + " + " + input.getSecondValue() + " " + input.getSecondUnit()
                        + " = " + finalResult + " " + targetUnit)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── SUBTRACT ────────────────
    public QuantityMeasurementDTO subtract(QuantityInputDTO input) {
        double v1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double v2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        double diff = v1 - v2;
        String baseUnit = getBaseUnit(input.getType());

        return QuantityMeasurementDTO.builder()
                .result(diff)
                .unit(baseUnit)
                .type(input.getType())
                .operation("SUBTRACT")
                .description(input.getValue() + " " + input.getUnit()
                        + " - " + input.getSecondValue() + " " + input.getSecondUnit()
                        + " = " + diff + " " + baseUnit)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public QuantityMeasurementDTO subtractWithTarget(QuantityInputDTO input) {

        double v1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double v2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        double diff = v1 - v2;

        String targetUnit = input.getTargetUnit() != null
                ? input.getTargetUnit()
                : getBaseUnit(input.getType());

        double finalResult = fromBase(diff, targetUnit, input.getType());

        return QuantityMeasurementDTO.builder()
                .result(finalResult)
                .unit(targetUnit)
                .type(input.getType())
                .operation("SUBTRACT_WITH_TARGET")
                .description(input.getValue() + " " + input.getUnit()
                        + " - " + input.getSecondValue() + " " + input.getSecondUnit()
                        + " = " + finalResult + " " + targetUnit)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── MULTIPLY ────────────────
    public QuantityMeasurementDTO multiply(QuantityInputDTO input) {

        double base = toBase(input.getValue(), input.getUnit(), input.getType());

        double multiplier = input.getSecondValue();

        double result = base * multiplier;

        String unit = input.getTargetUnit() != null
                ? input.getTargetUnit()
                : getBaseUnit(input.getType());

        double finalResult = fromBase(result, unit, input.getType());

        return QuantityMeasurementDTO.builder()
                .result(finalResult)
                .unit(unit)
                .type(input.getType())
                .operation("MULTIPLY")
                .description(input.getValue() + " " + input.getUnit()
                        + " × " + multiplier
                        + " = " + finalResult + " " + unit)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── DIVIDE ────────────────
    public QuantityMeasurementDTO divide(QuantityInputDTO input) {
        if (input.getSecondValue() == null || input.getSecondValue() == 0)
            throw new IllegalArgumentException("Cannot divide by zero");

        double v1 = toBase(input.getValue(), input.getUnit(), input.getType());
        double v2 = toBase(input.getSecondValue(), input.getSecondUnit(), input.getType());

        double quotient = v1 / v2;

        return QuantityMeasurementDTO.builder()
                .result(quotient)
                .unit("RATIO")
                .type(input.getType())
                .operation("DIVIDE")
                .description(input.getValue() + " " + input.getUnit()
                        + " ÷ " + input.getSecondValue() + " " + input.getSecondUnit()
                        + " = " + quotient)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ──────────────── CONVERSION METHODS ────────────────

    private double convertLength(double value, LengthUnit from, LengthUnit to) {
        return value * from.getToInchFactor() / to.getToInchFactor();
    }

    private double convertWeight(double value, WeightUnit from, WeightUnit to) {
        return value * from.getToGramFactor() / to.getToGramFactor();
    }

    private double convertVolume(double value, VolumeUnit from, VolumeUnit to) {
        return value * from.getToLiterFactor() / to.getToLiterFactor();
    }

    private double convertTemperature(double value, TemperatureUnit from, TemperatureUnit to) {

        // Convert to base (FAHRENHEIT)
        double fahrenheit = switch (from) {
            case FAHRENHEIT -> value;
            case CELSIUS -> (value * 9 / 5) + 32;
        };

        // Convert from base to target
        return switch (to) {
            case FAHRENHEIT -> fahrenheit;
            case CELSIUS -> (fahrenheit - 32) * 5 / 9;
        };
    }

    // ──────────────── BASE CONVERSION ────────────────

    private double toBase(double value, String unit, String type) {
        return switch (type.toUpperCase()) {

            case "LENGTH" -> value * LengthUnit.valueOf(unit).getToInchFactor();

            case "WEIGHT" -> value * WeightUnit.valueOf(unit).getToGramFactor();

            case "VOLUME" -> value * VolumeUnit.valueOf(unit).getToLiterFactor();

            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit).toBase(value);

            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    // ──────────────── FROM BASE CONVERSION ────────────────
    private double fromBase(double baseValue, String unit, String type) {

        return switch (type.toUpperCase()) {

            case "LENGTH" -> baseValue / LengthUnit.valueOf(unit).getToInchFactor();

            case "WEIGHT" -> baseValue / WeightUnit.valueOf(unit).getToGramFactor();

            case "VOLUME" -> baseValue / VolumeUnit.valueOf(unit).getToLiterFactor();

            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit).fromBase(baseValue);

            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    // ──────────────── BASE UNIT NAME ────────────────
    private String getBaseUnit(String type) {
        return switch (type.toUpperCase()) {
            case "LENGTH" -> "INCHES";
            case "WEIGHT" -> "GRAM";
            case "VOLUME" -> "LITER";
            case "TEMPERATURE" -> "FAHRENHEIT";
            default -> "UNKNOWN";
        };
    }
}