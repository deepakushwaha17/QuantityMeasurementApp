package com.quantity.enums;

public enum VolumeUnit {
    MILLILITER(0.001),
    LITER(1.0),
    GALLON(3.78541);

    private final double toLiterFactor;

    VolumeUnit(double toLiterFactor) {
        this.toLiterFactor = toLiterFactor;
    }

    public double getToLiterFactor() {
        return toLiterFactor;
    }
}