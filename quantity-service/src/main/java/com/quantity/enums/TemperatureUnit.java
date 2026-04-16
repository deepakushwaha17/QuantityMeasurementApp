package com.quantity.enums;

public enum TemperatureUnit {

    FAHRENHEIT {
        @Override
        public double toBase(double value) {
            return value;
        }

        @Override
        public double fromBase(double baseValue) {
            return baseValue;
        }
    },

    CELSIUS {
        @Override
        public double toBase(double value) {
            return (value * 9 / 5) + 32;
        }

        @Override
        public double fromBase(double baseValue) {
            return (baseValue - 32) * 5 / 9;
        }
    };

    public abstract double toBase(double value);
    public abstract double fromBase(double baseValue);
}