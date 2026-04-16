package com.quantity.enums;

public enum WeightUnit {
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    TON(1000000.0),
    POUND(453.592),
    OUNCE(28.3495);

    private final double toGramFactor;

    WeightUnit(double toGramFactor) {
        this.toGramFactor = toGramFactor;
    }

    public double getToGramFactor() {
        return toGramFactor;
    }
}