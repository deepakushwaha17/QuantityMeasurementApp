package com.apps.quantitymeasurement;

public class Length {
	
	// Instance variables
	private double value;
	private LengthUnit unit;
	
	// Enum to represent different units and their conversion factors with the base unit being inches. 
	// This means all the conversion factors are defined in the terms of inches.
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor = conversionFactor;
		}
		
		public double getConversionFactor() {
			return conversionFactor;
		}
	}
	
	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		this.value = value;
		this.unit = unit;
	}
	
	// Convert length value to base unit (inches) and round off to two decimal places
	private double convertToBaseUnit() {
		String result = String.format("%.2f", value * unit.getConversionFactor());
		return Double.valueOf(result); 
	}
	
	// Compare to Length object for equality based on their values in the base unit
	public boolean compare(Length thatLength) {
		if(thatLength == null) {
			return false;
		}
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}
	
	// Equals method is overridden to firstly check if two objects are the same references
	// If not, if other object is null or of different class.
	// Finally, calls the compare method to determine equality based on converted values.
	@Override
	public boolean equals(Object obj) {
		
		// Reference check
		if(this == obj) {
			return true;
		}
		
		// Null check
		if(obj == null) {
			return false;
		}
		
		// Type check
		if(!(obj instanceof Length)) {
			return false;
		}
		
		// Type Cast to Feet type
		Length other = (Length) obj;
		
		// Compare 
		return compare(other);
	}
	
	@Override
	public int hashCode() {
		return Double.valueOf(convertToBaseUnit()).hashCode();
	}
	
	// Main method for standalone testing
	public static void main(String[] args) {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);	
		System.out.println("Are lengths equal? " + length1.equals(length2));
		
		Length length3 = new Length(1.0, LengthUnit.YARDS);
		Length length4 = new Length(36.0, LengthUnit.INCHES);	
		System.out.println("Are lengths equal? " + length3.equals(length4));
		
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);	
		System.out.println("Are lengths equal? " + length5.equals(length6));
	}
}