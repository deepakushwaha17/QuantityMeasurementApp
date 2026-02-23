package com.apps.quantitymeasurement;
import java.util.Objects;

public class Length {
	
	// Instance variables
	private double value;
	private LengthUnit unit;
	
	/**
	*Nested enumeration representing different length units and their conversion factors.
	*The base unit for conversion is inches. Thus, each unit's conversion factor is defined
	* relative to inches.
	<p>Each unit stores a conversion factor relative to inches (the base unit).
	This design simplifies unit conversions by always converting through a common base unit.
	* <p>Example: 1 FOOT 12.0 inches, 1 YARD 36.0 inches, 1 CENTIMETER = 0.393701 inches
	*/
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
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value.");
		}
		this.value = value;
		this.unit = unit;
	}
	
	// Convert length value to base unit (inches) and round off to two decimal places
	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0; 
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
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		// Type Cast to Length type
		Length other = (Length) obj;
		
		// Compare 
		return compare(other);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}
	
	//UC5 Conversion Method
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
			
		// Convert to base (inches)
		double baseValue = convertToBaseUnit();

		// Convert from inches to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Length(convertedValue, targetUnit);
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	// Main method for stand alone testing
	public static void main(String[] args) {

		// 1 Foot = 12 Inches
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("1 Foot equals 12 Inches ? " + length1.equals(length2));

		// 1 Yard = 36 Inches
		Length length3 = new Length(1.0, LengthUnit.YARDS);
		Length length4 = new Length(36.0, LengthUnit.INCHES);
		System.out.println("1 Yard equals 36 Inches ? " + length3.equals(length4));

		// 100 cm ≈ 39.3701 Inches
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);
		System.out.println("100 CM equals 39.3701 Inches ? " + length5.equals(length6));

		// Conversion Test
		Length feet = new Length(3.0, LengthUnit.FEET);
		Length convertedToYards = feet.convertTo(LengthUnit.YARDS);
		System.out.println("3 Feet in Yards = " + convertedToYards);

		Length convertedToCm = feet.convertTo(LengthUnit.CENTIMETERS);
		System.out.println("3 Feet in CM = " + convertedToCm);

		// toString Test
		System.out.println("String Representation: " + feet);

		// Exception Test
		try {
			new Length(Double.NaN, LengthUnit.FEET);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}

		try {
			new Length(5.0, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}

		try {
			feet.convertTo(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}
}