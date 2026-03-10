package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
	
	private static final double EPSILON = 1e-9;

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

        assertEquals(3.99, result.convertTo(LengthUnit.FEET), 0.001);
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

        assertEquals(1450.0, result.convertTo(WeightUnit.GRAM), 0.01);
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
    
    // Volume test cases
    

    // Equality Test
    
    @Test
    public void testEquality_LitreToLitre_SameValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE),
                     new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    @Test
    public void testEquality_LitreToLitre_DifferentValue() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE),
                        new Quantity<>(2.0, VolumeUnit.LITRE));
    }

    @Test
    public void testEquality_LitreToMillilitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE),
                     new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
    }

    @Test
    public void testEquality_MillilitreToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                     new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    @Test
    public void testEquality_LitreToGallon_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE),
                     new Quantity<>(0.264172, VolumeUnit.GALLON));
    }

    @Test
    public void testEquality_GallonToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.GALLON),
                     new Quantity<>(3.78541, VolumeUnit.LITRE));
    }

    @Test
    public void testEquality_VolumeVsLength_Incompatible() {
    	Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
        
    }

    @Test
    public void testEquality_VolumeVsWeight_Incompatible() {
    	Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(volume.equals(weight));
    }

    @Test
    public void testEquality_NullComparison() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
    }

    @Test
    public void testEquality_SameReference() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(volume.equals(volume));
    }

    @Test
    public void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
    }

    @Test
    public void testEquality_TransitiveProperty() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    public void testEquality_ZeroValue() {
        assertEquals(new Quantity<>(0.0, VolumeUnit.LITRE),
                     new Quantity<>(0.0, VolumeUnit.MILLILITRE));
    }

    @Test
    public void testEquality_NegativeVolume() {
        assertEquals(new Quantity<>(-1.0, VolumeUnit.LITRE),
                     new Quantity<>(-1000.0, VolumeUnit.MILLILITRE));
    }

    @Test
    public void testEquality_LargeVolumeValue() {
        assertEquals(new Quantity<>(1_000_000.0, VolumeUnit.MILLILITRE),
                     new Quantity<>(1000.0, VolumeUnit.LITRE));
    }

    @Test
    public void testEquality_SmallVolumeValue() {
        assertEquals(new Quantity<>(0.001, VolumeUnit.LITRE),
                     new Quantity<>(1.0, VolumeUnit.MILLILITRE));
    }

    // Conversion Tests 

    @Test
    public void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(1000.0, litre.convertTo(VolumeUnit.MILLILITRE), EPSILON);
    }

    @Test
    public void testConversion_MillilitreToLitre() {
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(1.0, ml.convertTo(VolumeUnit.LITRE), EPSILON);
    }

    @Test
    public void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        assertEquals(3.79, gallon.convertTo(VolumeUnit.LITRE), EPSILON);
    }

    @Test
    public void testConversion_LitreToGallon() {
        Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
        assertEquals(1.0, litre.convertTo(VolumeUnit.GALLON), EPSILON);
    }

    @Test
    public void testConversion_MillilitreToGallon() {
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(0.26, ml.convertTo(VolumeUnit.GALLON), EPSILON);
    }

    @Test
    public void testConversion_SameUnit() {
        Quantity<VolumeUnit> litre = new Quantity<>(5.0, VolumeUnit.LITRE);
        assertEquals(5.0, litre.convertTo(VolumeUnit.LITRE), EPSILON);
    }

    @Test
    public void testConversion_ZeroValue() {
        Quantity<VolumeUnit> litre = new Quantity<>(0.0, VolumeUnit.LITRE);
        assertEquals(0.0, litre.convertTo(VolumeUnit.MILLILITRE), EPSILON);
    }

    @Test
    public void testConversion_NegativeValue() {
        Quantity<VolumeUnit> litre = new Quantity<>(-1.0, VolumeUnit.LITRE);
        assertEquals(-1000.0, litre.convertTo(VolumeUnit.MILLILITRE), EPSILON);
    }

    @Test
    public void testConversion_RoundTrip() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.5, VolumeUnit.LITRE);
        double converted = new Quantity<>(litre.convertTo(VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE)
                                .convertTo(VolumeUnit.LITRE);
        assertEquals(1.5, converted, EPSILON);
    }

    // Addition Tests

    @Test
    public void testAddition_SameUnit_LitrePlusLitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(2.0, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_SameUnit_MillilitrePlusMillilitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_CrossUnit_MillilitrePlusLitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_CrossUnit_GallonPlusLitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> volume2 = new Quantity<>(3.78, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), volume1.add(volume2));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Litre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), volume1.add(volume2, VolumeUnit.LITRE));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE), volume1.add(volume2, VolumeUnit.MILLILITRE));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Gallon() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), volume1.add(volume2, VolumeUnit.GALLON));
    }

    @Test
    public void testAddition_Commutativity() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> sum1 = volume1.add(volume2);
        Quantity<VolumeUnit> sum2 = volume2.add(volume1);

        assertEquals(sum1.convertTo(VolumeUnit.LITRE), sum2.convertTo(VolumeUnit.LITRE), EPSILON);
    }

    @Test
    public void testAddition_WithZero() {
        Quantity<VolumeUnit> volume = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> zero = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
        assertEquals(volume, volume.add(zero));
    }

    @Test
    public void testAddition_NegativeValues() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(-2000.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_LargeValues() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(1e6, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1e6, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(2e6, VolumeUnit.LITRE), volume1.add(volume2));
    }

    @Test
    public void testAddition_SmallValues() {
        Quantity<VolumeUnit> volume1 = new Quantity<>(0.01, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(0.02, VolumeUnit.LITRE);
        assertEquals(0.03, volume1.add(volume2).getValue(), EPSILON);
    }

    // VolumeUnit Enum Tests

    @Test
    public void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
    }

    @Test
    public void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
    }

    @Test
    public void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
    }

    @Test
    public void testConvertToBaseUnit_LitreToLitre() {
        assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0), EPSILON);
    }

    @Test
    public void testConvertToBaseUnit_MillilitreToLitre() {
        assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), EPSILON);
    }

    @Test
    public void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.79, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPSILON);
    }

    @Test
    public void testConvertFromBaseUnit_LitreToLitre() {
        assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0), EPSILON);
    }

    @Test
    public void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPSILON);
    }

    @Test
    public void testConvertFromBaseUnit_LitreToGallon() {
        assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.78541), EPSILON);
    }
    
    // Subtraction test cases

    @Test
    public void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> result =new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity<VolumeUnit> result =new Quantity<>(10.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));

        assertEquals(new Quantity<>(7.0, VolumeUnit.LITRE), result);
    }

    @Test
    public void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCHES)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(60.0, LengthUnit.INCHES), result);
    }

    @Test
    public void testSubtraction_ResultNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(10.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_ResultZero() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(120.0, LengthUnit.INCHES));

        assertEquals(new Quantity<>(0.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_WithZeroOperand() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(0.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_WithNegativeValue() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(-2.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_NonCommutative() {

        Quantity<LengthUnit> firstResult = new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET));

        Quantity<LengthUnit> secondResult = new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(10.0, LengthUnit.FEET));

        assertNotEquals(firstResult, secondResult);
    }

    @Test
    public void testSubtraction_LargeValues() {
        Quantity<WeightUnit> result = new Quantity<>(1000000.0, WeightUnit.KILOGRAM)
                        .subtract(new Quantity<>(500000.0, WeightUnit.KILOGRAM));

        assertEquals(new Quantity<>(500000.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testSubtraction_SmallValues() {
        Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
                        .subtract(new Quantity<>(0.0005, LengthUnit.FEET));

        assertEquals(new Quantity<>(0.00, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_ChainedOperations() {

        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(2.0, LengthUnit.FEET))
                        .subtract(new Quantity<>(1.0, LengthUnit.FEET));

        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_NullOperand() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
        });
    }

    @Test
    public void testSubtraction_Immutability() {

        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> subtractValue = new Quantity<>(5.0, LengthUnit.FEET);

        original.subtract(subtractValue);

        assertEquals(new Quantity<>(10.0, LengthUnit.FEET), original);
    }

    // Division Tests

    @Test
    public void testDivision_SameUnit_FeetByFeet() {

        double result = new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(5.0, result, EPSILON);
    }

    @Test
    public void testDivision_SameUnit_LitreByLitre() {

        double result = new Quantity<>(10.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(5.0, VolumeUnit.LITRE));

        assertEquals(2.0, result, EPSILON);
    }

    @Test
    public void testDivision_CrossUnit_FeetByInches() {

        double result = new Quantity<>(24.0, LengthUnit.INCHES)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    public void testDivision_CrossUnit_KgByGram() {

        double result = new Quantity<>(2.0, WeightUnit.KILOGRAM)
                        .divide(new Quantity<>(2000.0, WeightUnit.GRAM));

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    public void testDivision_RatioGreaterThanOne() {

        double result = new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(5.0, result, EPSILON);
    }

    @Test
    public void testDivision_RatioLessThanOne() {

        double result = new Quantity<>(5.0, LengthUnit.FEET)
                        .divide(new Quantity<>(10.0, LengthUnit.FEET));

        assertEquals(0.5, result, EPSILON);
    }

    @Test
    public void testDivision_RatioEqualOne() {

        double result = new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(10.0, LengthUnit.FEET));

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    public void testDivision_NonCommutative() {

        double first = new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(5.0, LengthUnit.FEET));

        double second = new Quantity<>(5.0, LengthUnit.FEET)
                        .divide(new Quantity<>(10.0, LengthUnit.FEET));

        assertNotEquals(first, second);
    }

    @Test
    public void testDivision_ByZero() {

        assertThrows(ArithmeticException.class, () -> {
            new Quantity<>(10.0, LengthUnit.FEET)
                    .divide(new Quantity<>(0.0, LengthUnit.FEET));
        });
    }

    @Test
    public void testDivision_LargeRatio() {

        double result = new Quantity<>(1000000.0, WeightUnit.KILOGRAM)
                        .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));

        assertEquals(1000000.0, result, EPSILON);
    }

    @Test
    public void testDivision_SmallRatio() {

        double result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .divide(new Quantity<>(1000000.0, WeightUnit.KILOGRAM));

        assertEquals(0.000001, result, EPSILON);
    }

    @Test
    public void testDivision_NullOperand() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(10.0, LengthUnit.FEET).divide(null);
        });
    }

    @Test
    public void testDivision_Immutability() {

        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> divisor = new Quantity<>(5.0, LengthUnit.FEET);

        original.divide(divisor);

        assertEquals(new Quantity<>(10.0, LengthUnit.FEET), original);
    }
    
    // Delegation Tests
    @Test
    void testRefactoring_Add_DelegatesViaHelper() {
        Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInInches = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> resultQuantity = lengthInFeet.add(lengthInInches);

        assertEquals(11.0, resultQuantity.getValue(), 0.01);
        assertEquals(LengthUnit.FEET, resultQuantity.getUnit());
    }

    @Test
    void testRefactoring_Subtract_DelegatesViaHelper() {
        Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInFeetToSubtract = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> resultQuantity = lengthInFeet.subtract(lengthInFeetToSubtract);

        assertEquals(5.0, resultQuantity.getValue(), 0.01);
    }

    @Test
    void testRefactoring_Divide_DelegatesViaHelper() {
        Quantity<LengthUnit> totalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisorLength = new Quantity<>(2.0, LengthUnit.FEET);

        double resultRatio = totalLength.divide(divisorLength);

        assertEquals(5.0, resultRatio, 0.001);
    }

    // Validation Consistency Tests
    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {

        Quantity<LengthUnit> lengthQuantity = new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.add(null));

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.subtract(null));

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.divide(null));
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {

        Quantity<LengthUnit> lengthQuantity = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> weightQuantity = new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.add((Quantity) weightQuantity));

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.subtract((Quantity) weightQuantity));

        assertThrows(IllegalArgumentException.class,
                () -> lengthQuantity.divide((Quantity) weightQuantity));
    }

    @Test
    void testValidation_FiniteValue_ConsistentAcrossOperations() {

    	assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NEGATIVE_INFINITY, LengthUnit.FEET));

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    // Enum Operation Tests
 
    @Test
    void testArithmeticOperation_Add_EnumComputation() {

        Quantity<LengthUnit> firstLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> secondLength = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> resultQuantity = firstLength.add(secondLength);

        assertEquals(15.0, resultQuantity.getValue(), 0.001);
    }
    
    @Test
    void testArithmeticOperation_Subtract_EnumComputation() {

        Quantity<LengthUnit> firstLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> secondLength = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> resultQuantity = firstLength.subtract(secondLength);

        assertEquals(5.0, resultQuantity.getValue(), 0.001);
    }
    
    @Test
    void testArithmeticOperation_Divide_EnumComputation() {

        Quantity<LengthUnit> totalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisorLength = new Quantity<>(5.0, LengthUnit.FEET);

        double resultRatio = totalLength.divide(divisorLength);

        assertEquals(2.0, resultRatio, 0.001);
    }
    
    @Test
    void testArithmeticOperation_DivideByZero_EnumThrows() {

        Quantity<LengthUnit> totalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> zeroLength = new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class,
                () -> totalLength.divide(zeroLength));
    }

    // Helper Arithmetic Behavior
    @Test
    void testPerformBaseArithmetic_ConversionAndOperation() {

        Quantity<LengthUnit> lengthInFeet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInInches = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> resultQuantity = lengthInFeet.add(lengthInInches);

        assertEquals(2.0, resultQuantity.getValue(), 0.01);
    }

  
    // Rounding Tests
    @Test
    void testRounding_AddSubtract_TwoDecimalPlaces() {

        Quantity<LengthUnit> firstLength = new Quantity<>(1.234, LengthUnit.FEET);
        Quantity<LengthUnit> secondLength = new Quantity<>(1.234, LengthUnit.FEET);

        Quantity<LengthUnit> result = firstLength.add(secondLength);

        assertEquals(2.47, result.getValue(), 0.01);
    }

    @Test
    void testRounding_Divide_NoRounding() {

        Quantity<LengthUnit> totalLength = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisorLength = new Quantity<>(2.0, LengthUnit.FEET);

        double resultRatio = totalLength.divide(divisorLength);

        assertEquals(2.5, resultRatio);
    }

    // Immutability Tests

    @Test
    void testImmutability_AfterAdd_ViaCentralizedHelper() {

        Quantity<LengthUnit> originalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> addedLength = new Quantity<>(2.0, LengthUnit.FEET);

        originalLength.add(addedLength);

        assertEquals(10.0, originalLength.getValue());
    }

    @Test
    void testImmutability_AfterSubtract_ViaCentralizedHelper() {

        Quantity<LengthUnit> originalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> subtractLength = new Quantity<>(3.0, LengthUnit.FEET);

        originalLength.subtract(subtractLength);

        assertEquals(10.0, originalLength.getValue());
    }

    @Test
    void testImmutability_AfterDivide_ViaCentralizedHelper() {

        Quantity<LengthUnit> originalLength = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisorLength = new Quantity<>(2.0, LengthUnit.FEET);

        originalLength.divide(divisorLength);

        assertEquals(10.0, originalLength.getValue());
    }

    // Multi Category Test
    @Test
    void testAllOperations_AcrossAllCategories() {

        Quantity<LengthUnit> length1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weight2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);

        Quantity<VolumeUnit> volume1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(5.0, VolumeUnit.LITRE);

        assertEquals(15.0, length1.add(length2).getValue());
        assertEquals(5.0, weight1.subtract(weight2).getValue());
        assertEquals(2.0, volume1.divide(volume2));
    }

    // Chain Operation Test
    @Test
    void testArithmetic_Chain_Operations() {

        Quantity<LengthUnit> lengthOne = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthTwo = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthThree = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthFour = new Quantity<>(3.0, LengthUnit.FEET);

        double result = lengthOne.add(lengthTwo).subtract(lengthThree).divide(lengthFour);

        assertEquals(11.0 / 3.0, result, 0.001);
    }

    // Temperature test cases
    
    // Equality Tests
    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertTrue(temp1.equals(temp2));
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(temp1.equals(temp2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(tempC.equals(tempF));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(tempC.equals(tempF));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(tempC.equals(tempF));
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = new Quantity<>(77.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(tempC.equals(tempF));
        assertTrue(tempF.equals(tempC));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> temp = new Quantity<>(15.0, TemperatureUnit.CELSIUS);
        assertTrue(temp.equals(temp));
    }

    // Conversion Tests
    
    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        Quantity<TemperatureUnit> temp50 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertEquals(122.0, temp50.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);

        Quantity<TemperatureUnit> tempMinus20 = new Quantity<>(-20.0, TemperatureUnit.CELSIUS);
        assertEquals(-4.0, tempMinus20.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        Quantity<TemperatureUnit> temp212F = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(100.0, temp212F.convertTo(TemperatureUnit.CELSIUS), EPSILON);

        Quantity<TemperatureUnit> tempMinus4F = new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(-20.0, tempMinus4F.convertTo(TemperatureUnit.CELSIUS), EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> originalTemp = new Quantity<>(75.0, TemperatureUnit.CELSIUS);
        double convertedF = originalTemp.convertTo(TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> roundTrip = new Quantity<>(convertedF, TemperatureUnit.FAHRENHEIT);
        assertEquals(originalTemp.getValue(), roundTrip.convertTo(TemperatureUnit.CELSIUS), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(23.0, TemperatureUnit.CELSIUS);
        assertEquals(23.0, tempC.convertTo(TemperatureUnit.CELSIUS), EPSILON);
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertEquals(32.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
    }

    @Test
    void testTemperatureConversion_NegativeValues() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(-10.0, TemperatureUnit.CELSIUS);
        assertEquals(14.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(1000.0, TemperatureUnit.CELSIUS);
        assertEquals(1832.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
    }

    // Unsupported Operations Tests
 
    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempAdd = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            tempC.add(tempAdd);
        });
        assertTrue(exception.getMessage().contains("CELSIUS does not support addition"));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempSub = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            tempC.subtract(tempSub);
        });
        assertTrue(exception.getMessage().contains("CELSIUS does not support subtraction"));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempDiv = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            tempC.divide(tempDiv);
        });
        assertTrue(exception.getMessage().contains("CELSIUS does not support division"));
    }

    // Incompatibility Tests

    @Test
    void testTemperatureVsLengthIncompatibility() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit> lengthFeet = new Quantity<>(100.0, LengthUnit.FEET);
        assertFalse(tempC.equals(lengthFeet));
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<WeightUnit> weightKG = new Quantity<>(50.0, WeightUnit.KILOGRAM);
        assertFalse(tempC.equals(weightKG));
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        Quantity<VolumeUnit> volumeLitre = new Quantity<>(25.0, VolumeUnit.LITRE);
        assertFalse(tempC.equals(volumeLitre));
    }

    // Operation Support Methods

    @Test
    void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }
    

}