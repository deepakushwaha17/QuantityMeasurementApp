package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.Length.LengthUnit;

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
	    Length a = new Length(1.0, Length.LengthUnit.FEET);
	    assertTrue(a.equals(a));

	    // Symmetric
	    Length b = new Length(12.0, Length.LengthUnit.INCHES);
	    assertTrue(a.equals(b));
	    assertTrue(b.equals(a));

	    // Transitive
	    Length c = new Length(1.0, Length.LengthUnit.FEET);
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
}