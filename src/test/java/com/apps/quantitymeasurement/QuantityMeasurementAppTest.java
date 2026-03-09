package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {
	
	@Test
	public void testFeetEquality() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length feet2 = new Length(1.0, LengthUnit.FEET); 
		
		assertTrue(feet1.equals(feet2));
	}

	@Test
	public void testInchesEquality() {
		Length inches1 = new Length(1.0, LengthUnit.INCHES);
		Length inches2 = new Length(1.0, LengthUnit.INCHES); 
		
		assertTrue(inches1.equals(inches2));
	}
	
	@Test
	public void testFeetInchesComparison() {
		Length feet = new Length(1.0, LengthUnit.FEET);
		Length inches = new Length(12.0, LengthUnit.INCHES);
		
		assertTrue(feet.equals(inches));
	}
	
	@Test
	public void testFeetInequality() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length feet2 = new Length(2.0, LengthUnit.FEET); 
		
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	public void testInchesInequality() {
		Length inches1 = new Length(1.0, LengthUnit.INCHES);
		Length inches2 = new Length(2.0, LengthUnit.INCHES); 
		
		assertFalse(inches1.equals(inches2));
	}
	
	@Test
	public void testCrossUnitInequality() {
		Length feet = new Length(2.0, LengthUnit.FEET);
		Length inches = new Length(12.0, LengthUnit.INCHES);
		
		assertFalse(feet.equals(inches));
	}
	
	@Test
	public void testMultipleFeetComparison() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length feet2 = new Length(1.0, LengthUnit.FEET); 
		Length feet3 = new Length(2.0, LengthUnit.FEET);
		Length feet4 = new Length(2.0, LengthUnit.FEET);
				
		assertTrue(feet1.equals(feet2));
		assertFalse(feet1.equals(feet3));
		assertTrue(feet3.equals(feet4));
	}
	
	@Test
	public void testNullComparison() {
		Length feet = new Length(2.0, LengthUnit.FEET);
		Length inches = new Length(12.0, LengthUnit.INCHES);
				
		assertFalse(feet.equals(null));
		assertFalse(inches.equals(null ));
	}
	
	@Test
	public void yardEquals36Inches() {
		Length yard = new Length(1.0,LengthUnit.YARDS);
		Length inches = new Length(36.0, LengthUnit.INCHES);
		
		assertTrue(yard.equals(inches));
	}
	
	@Test
	public void centimeterEquals39Point370Inches() {
		Length cm = new Length(100.0,LengthUnit.CENTIMETERS);
		Length inches = new Length(39.3701, LengthUnit.INCHES);
		
		assertTrue(cm.equals(inches));
	}
	
	@Test
	public void threeFeetEqualsOneYard() {
		Length feet = new Length(3.0,LengthUnit.FEET);
		Length yard = new Length(1.0, LengthUnit.YARDS);
		
		assertTrue(feet.equals(yard));
	}
	
	@Test
	public void thirtyPoint48CmEqualsOneFeet() {
		Length cm = new Length(30.48,LengthUnit.CENTIMETERS);
		Length feet = new Length(1.0, LengthUnit.FEET);
		
		assertTrue(cm.equals(feet));
	}
	
	@Test
	public void yardNotEqualToInches() {
		Length yard = new Length(1.0,LengthUnit.YARDS);
		Length inches = new Length(1.0, LengthUnit.INCHES);
		
		assertFalse(yard.equals(inches));
	}
	
	@Test
	public void referenceEqualitySameObject() {
		Length cm = new Length(100.0,LengthUnit.CENTIMETERS);
		
		assertTrue(cm.equals(cm));
	}
	
	@Test
	public void equalsReturnFalseForNull() {
		Length cm = new Length(100.0,LengthUnit.CENTIMETERS);
		
		assertFalse(cm.equals(null));
	}
	
	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {

	    // Reflexive
	    Length a = new Length(1.0, LengthUnit.FEET);
	    assertTrue(a.equals(a));

	    // Symmetric
	    Length b = new Length(12.0, LengthUnit.INCHES);
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(a));

	    // Transitive
	    Length c = new Length(1.0, LengthUnit.FEET);
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(c));
	    assertTrue(a.equals(c));
	}
	
	@Test
	public void differentValuesSameUnitNoEqual() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length feet2 = new Length(2.0, LengthUnit.FEET); 
		assertFalse(feet1.equals(feet2));
		
		Length inches1 = new Length(1.0, LengthUnit.INCHES);
		Length inches2 = new Length(2.0, LengthUnit.INCHES); 
		assertFalse(inches1.equals(inches2));
	}
	
	@Test
	public void crossUnitEqualityDemonstrateMethod() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length inches = new Length(12.0, LengthUnit.INCHES);	
		assertTrue(feet1.equals(inches));
		
		Length feet2 = new Length(3.0,LengthUnit.FEET);
		Length yard = new Length(1.0, LengthUnit.YARDS);	
		assertTrue(feet2.equals(yard));
	}
	
	@Test
	public void convertFeetToInches() {
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(3.0, LengthUnit.FEET, LengthUnit.INCHES);
		Length expectedLength = new Length(36.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
	}
	
	@Test
	public void convertYardsToInchesUsingOverloadedMethod() {
		Length lengthInYards = new Length(2.0, LengthUnit.YARDS);
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(lengthInYards, LengthUnit.INCHES);
		Length expectedLength = new Length(72.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
	}
	
	@Test
	public void addFeetAndFeet() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(2.0, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(3.0, LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void addInchesAndInches() {
		Length length1 = new Length(12.0, LengthUnit.INCHES);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(24.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void addFeetAndInches() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(2.0, LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void addFeetAndInchesWithTargetUnitInches() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, LengthUnit.INCHES);
		Length exceptedLength = new Length(24.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testCommutativityAddition() {
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);
		Length l1PlusL2 = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, LengthUnit.INCHES);
		Length l2PlusL1 = QuantityMeasurementApp.demonstrateLengthAddition(l2, l1, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(l1PlusL2, l2PlusL1));
	}

	@Test
	public void testAdditionWithZero() {
		Length length1 = new Length(5.0, LengthUnit.FEET);
		Length length2 = new Length(0.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(5.0, LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionWithNegativeValues() {
		Length length1 = new Length(5.0, LengthUnit.FEET);
		Length length2 = new Length(-2.0, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(3.0, LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionWithNullSecondOperand() {
		Length length = new Length(5.0, LengthUnit.FEET);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthAddition(length, null));
		assertEquals("Length cannot be null", exception.getMessage());
	}
	
	@Test
	public void testAdditionWithLargeValues() {
		Length length1 = new Length(1e6, LengthUnit.FEET);
		Length length2 = new Length(1e6, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(2e6, LengthUnit.FEET);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void testAdditionWithSmallValues() {
		Length length1 = new Length(0.01, LengthUnit.YARDS);
		Length length2 = new Length(0.02, LengthUnit.YARDS);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
		Length exceptedLength = new Length(0.03, LengthUnit.YARDS);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitYards() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, LengthUnit.YARDS);
		Length expectedSum = new Length(0.67, LengthUnit.YARDS);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedSum));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitSameAsFirstOperand() {
		Length length1 = new Length(2.0, LengthUnit.YARDS);
		Length length2 = new Length(3.0, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, LengthUnit.YARDS);
		Length expectedSum = new Length(3.0, LengthUnit.YARDS);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedSum));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitCommutativity() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		Length l1PlusL2 = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, LengthUnit.YARDS);
		Length l2PlusL1 = QuantityMeasurementApp.demonstrateLengthAddition(l2, l1, LengthUnit.YARDS);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(l1PlusL2, l2PlusL1));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitNegativeValues() {
		Length length1 = new Length(5.0, LengthUnit.FEET);
		Length length2 = new Length(-2.0, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, LengthUnit.INCHES);
		Length exceptedLength = new Length(36.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitNullTargetUnit() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, null));
		assertEquals("Target unit cannot be null", exception.getMessage());
	}
	
	@Test
	public void testAdditionExplicitTargetUnitLargeToSmallScale() {
		Length length1 = new Length(1000.0, LengthUnit.FEET);
		Length length2 = new Length(500.0, LengthUnit.FEET);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, LengthUnit.INCHES);
		Length exceptedLength = new Length(18000.0, LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	@Test
	public void testAdditionExplicitTargetUnitSmallToLargeScale() {
		Length length1 = new Length(12.0, LengthUnit.INCHES);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition  (length1, length2, LengthUnit.YARDS);
		Length exceptedLength = new Length(0.67, LengthUnit.YARDS);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
}