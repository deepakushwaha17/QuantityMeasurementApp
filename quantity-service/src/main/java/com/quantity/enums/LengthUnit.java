package com.quantity.enums;

public enum LengthUnit {
    CENTIMETER(0.393701),
    INCHES(1.0),
    FEET(12.0),
    YARD(36.0);

    private final double toInchFactor;

    LengthUnit(double toInchFactor) {
        this.toInchFactor = toInchFactor;
    }

    public double getToInchFactor() {
        return toInchFactor;
    }
}