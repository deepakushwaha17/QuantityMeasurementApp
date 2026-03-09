package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // Length Equality Tests
    @Test
    public void lengthFeetEqualsInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);

        assertEquals(feet, inches);
    }

    @Test
    public void lengthYardsEqualsFeet() {
    	Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);
    	Quantity<LengthUnit> feet = new Quantity<>(3, LengthUnit.FEET);

        assertEquals(yard, feet);
    }

    // Weight Equality Tests
    @Test
    public void weightKilogramEqualsGrams() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
    	Quantity<WeightUnit> grams = new Quantity<>(1000, WeightUnit.GRAM);

        assertEquals(kg, grams);
    }

    @Test
    public void weightPoundEqualsGrams() {
    	Quantity<WeightUnit> pound = new Quantity<>(1, WeightUnit.POUND);
    	Quantity<WeightUnit> grams = new Quantity<>(453.592, WeightUnit.GRAM);

        assertEquals(pound, grams);
    }

    // Conversion Tests
    @Test
    public void convertLengthFeetToInches() {
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
        double result = feet.convertTo(LengthUnit.INCHES);

        assertEquals(12, result, 0.001);
    }

    @Test
    public void convertLengthYardsToInches() {
    	Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);
        double result = yard.convertTo(LengthUnit.INCHES);

        assertEquals(36, result, 0.001);
    }

    @Test
    public void convertWeightKilogramsToGrams() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
        double result = kg.convertTo(WeightUnit.GRAM);

        assertEquals(1000, result, 0.001);
    }

    // Addition Tests
    @Test
    public void addLengthFeetAndInches() {
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
    	Quantity<LengthUnit> inches = new Quantity<>(6, LengthUnit.INCHES);

    	Quantity<LengthUnit> result = feet.add(inches);

        assertEquals(1.5, result.getValue(), 0.001);
    }

    @Test
    public void addLengthYardsAndFeet() {
    	Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);

    	Quantity<LengthUnit> result = yard.add(feet);

        assertEquals(4, result.convertTo(LengthUnit.FEET), 0.001);
    }

    @Test
    public void addWeightKilogramsAndGrams() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
    	Quantity<WeightUnit> grams = new Quantity<>(500, WeightUnit.GRAM);

    	Quantity<WeightUnit> result = kg.add(grams);

        assertEquals(1.5, result.getValue(), 0.001);
    }

    @Test
    public void addWeightTonnesAndKilograms() {
    	Quantity<WeightUnit> tonne = new Quantity<>(1, WeightUnit.TONNE);
    	Quantity<WeightUnit> kg = new Quantity<>(500, WeightUnit.KILOGRAM);

    	Quantity<WeightUnit> result = tonne.add(kg);

        assertEquals(1500, result.convertTo(WeightUnit.KILOGRAM), 0.001);
    }

    @Test
    public void addWeightKilogramsAndPounds() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
    	Quantity<WeightUnit> pound = new Quantity<>(1, WeightUnit.POUND);

    	Quantity<WeightUnit> result = kg.add(pound);

        assertEquals(1453.592, result.convertTo(WeightUnit.GRAM), 0.01);
    }

    // Generic Type Safety
    @Test
    public void testGenericTypeSafetyWithWeight() {
    	Quantity<WeightUnit> weight = new Quantity<>(1, WeightUnit.KILOGRAM);

        assertNotNull(weight);
    }

    // Cross Type Negative Tests
    @Test
    public void preventCrossTypeComparisonLengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }
    
    @Test
    public void preventCrossTypeAdditionLengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> {
            // this will now throw IllegalArgumentException because types differ
            length.add((Quantity) weight);
        });
    }

    @Test
    public void preventCrossTypeConversionLengthToWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);

        assertThrows(ClassCastException.class, () -> {
            length.convertTo((LengthUnit) (Object) WeightUnit.KILOGRAM);
        });
    }

    // Backward Compatibility Tests
    @Test
    public void backwardCompatibilityLengthFeetEqualsInches() {
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
    	Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    @Test
    public void backwardCompatibilityWeightKilogramEqualsGrams() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
    	Quantity<WeightUnit> grams = new Quantity<>(1000, WeightUnit.GRAM);

        assertTrue(kg.equals(grams));
    }

    @Test
    public void backwardCompatibilityConvertLengthFeetToInches() {
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);

        assertEquals(12, feet.convertTo(LengthUnit.INCHES), 0.001);
    }

    @Test
    public void backwardCompatibilityConvertWeightKilogramsToGrams() {
    	Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);

        assertEquals(1000, kg.convertTo(WeightUnit.GRAM), 0.001);
    }

    @Test
    public void backwardCompatibilityAddLengthInSameUnit() {
    	Quantity<LengthUnit> feet1 = new Quantity<>(1, LengthUnit.FEET);
    	Quantity<LengthUnit> feet2 = new Quantity<>(1, LengthUnit.FEET);

    	Quantity<LengthUnit> result = feet1.add(feet2);

        assertEquals(2, result.getValue(), 0.001);
    }

    @Test
    public void backwardCompatibilityAddWeightInSameUnit() {
    	Quantity<WeightUnit> kg1 = new Quantity<>(1, WeightUnit.KILOGRAM);
    	Quantity<WeightUnit> kg2 = new Quantity<>(1, WeightUnit.KILOGRAM);

    	Quantity<WeightUnit> result = kg1.add(kg2);

        assertEquals(2, result.getValue(), 0.001);
    }

    @Test
    public void backwardCompatibilityLengthYardsEqualsFeet() {
    	Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);
    	Quantity<LengthUnit> feet = new Quantity<>(3, LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    public void backwardCompatibilityWeightPoundEqualsGrams() {
    	Quantity<WeightUnit> pound = new Quantity<>(1, WeightUnit.POUND);
    	Quantity<WeightUnit> grams = new Quantity<>(453.592, WeightUnit.GRAM);

        assertTrue(pound.equals(grams));
    }

    @Test
    public void backwardCompatibilityChainedAdditionsLength() {
    	Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
    	Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);
    	Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);

    	Quantity<LengthUnit> result = feet.add(inches).add(yard);

        assertEquals(60, result.convertTo(LengthUnit.INCHES), 0.001);
    }
}