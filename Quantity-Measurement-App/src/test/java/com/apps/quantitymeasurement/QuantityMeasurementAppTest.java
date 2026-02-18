package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;

public class QuantityMeasurementAppTest {
	
	@Test
	public void testEquality_SameValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);

		assertTrue(feet1.equals(feet2));
	}
	
	@Test
	public void testEquality_DifferentValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(2.0);

		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	public void testEquality_NullComparison() {
		Feet feet1 = new Feet(1.0);
		
		assertFalse(feet1.equals(null));
	}
	
	@Test
	public void testEquality_DifferentClass() {
		Feet feet1 = new Feet(1.0);
		
		assertFalse(feet1.equals("hello"));
	}
	
	@Test
	public void testEquality_SameReference() {
		Feet feet1 = new Feet(1.0);
		
		assertTrue(feet1.equals(feet1));
	}
	
	
}
