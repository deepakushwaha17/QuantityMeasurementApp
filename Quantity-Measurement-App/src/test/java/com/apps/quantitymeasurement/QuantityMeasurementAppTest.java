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
	
}