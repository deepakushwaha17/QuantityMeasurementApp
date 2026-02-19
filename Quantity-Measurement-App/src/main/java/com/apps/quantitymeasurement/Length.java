package com.apps.quantitymeasurement;

public class Length {
	
	// Instance variables
	private double value;
	private LengthUnit unit;
	
	// Enum to represent different units and their conversion factors with the base unit being inches. 
	// This means all the conversion factors are defined in the terms of inches.
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0);
		
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
	
	// Convert length value to base unit (inches)
	private double convertToBaseUnit() {
		return value * unit.getConversionFactor(); 
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
	}

}
