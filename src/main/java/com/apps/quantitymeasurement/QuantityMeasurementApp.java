package com.apps.quantitymeasurement;

/**
 * QuantityMeasurementApp UC6 Addition Operations Between Length Measurements
 * <p>This use case extends UCS by introducing addition operations between length measurements.
 * The Quantity Length API can add two measurements of potentially different units (but same
 * category-length) and return the result as per the unit of the first operand.</p>
 * 
 * <p>Examples:
 * <ul>
 * <li>Adding 1 foot and 12 inches yields 2 feet (based on the unit of the first operand)</li>
 * <li>Measurements must belong to the same category (length) but can have different units</li>
 * <li>Result is returned as per the unit of the first operand</li>
 * </ul>
 * </p>
*/

public class QuantityMeasurementApp {
	
	// UC4 Create a generic method to demonstrate Length Equality check
	public static boolean demonstrateLengthEquality(Length length1 , Length length2) {
		boolean result = length1.equals(length2);
		return result;
	}
	
	// UC4 Create a static method to take method parameters and demonstrate equality check
	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1 , 
			double value2, Length.LengthUnit unit2) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		
		return demonstrateLengthEquality(length1,length2);
		
	}

	 // UC5: Convert one length to target unit
	public static Length demonstrateLengthConversion(double value, Length.LengthUnit fromUnit,Length.LengthUnit targetUnit) {

		Length length = new Length(value, fromUnit);
		return length.convertTo(targetUnit);
	}

	// UC5: Overloaded Conversion using Length object
	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {

		return length.convertTo(toUnit);
	}
	
	// UC6 : Add length to the target length
	public static Length demonstarteLengthAddition(Length targetLength, Length givenLength) {
		return targetLength.add(givenLength);
	}
		
	// UC6 : Overloaded Method & Add two lengths and then converted them into target unit
	public static Length demonstarteLengthAddition(Length length1, Length length2, Length.LengthUnit targetUnit) {
		Length totalLength = length1.add(length2);
		return totalLength.convertTo(targetUnit);
	}
		
	// UC6 : Overloaded Addition to provide flexibility
	public static Length demonstarteLengthAddition(double value1, Length.LengthUnit unit1, double value2, Length.LengthUnit unit2, Length.LengthUnit targetUnit) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		return demonstarteLengthAddition(length1, length2, targetUnit);
	}

	// Main method
	public static void main(String[] args) {
		//Demonstrate Feet and Inches comparison
		boolean result1 = demonstrateLengthComparison(1.0, Length.LengthUnit.FEET, 12.0 , Length.LengthUnit.INCHES);
		System.out.println("Equal (" + result1 + ")");
		
		//Demonstrate Yards and Inches comparison
		boolean result2 = demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS, 36.0 , Length.LengthUnit.INCHES);
		System.out.println("Equal (" + result2 + ")");
				
		//Demonstrate Centimeters and Inches comparison
		boolean result3 = demonstrateLengthComparison(100.0, Length.LengthUnit.CENTIMETERS, 39.3701 , Length.LengthUnit.INCHES);
		System.out.println("Equal (" + result3 + ")");
				
		//Demonstrate Feet and Yards comparison
		boolean result4 = demonstrateLengthComparison(3.0, Length.LengthUnit.FEET, 1.0 , Length.LengthUnit.YARDS);
		System.out.println("Equal (" + result4 + ")");
				
		//Demonstrate Centimeters and Feet comparison
		boolean result5 = demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS, 1.0 , Length.LengthUnit.FEET);
		System.out.println("Equal (" + result5 + ")");

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES)
				.convertTo(Length.LengthUnit.INCHES).toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET).toString()
				.split(" ")[0]);

		System.out.println(demonstrateLengthConversion(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES).toString()
				.split(" ")[0]);
		
		Length length1 = new Length(3.0, Length.LengthUnit.FEET);
		Length length2 = new Length(12.0, Length.LengthUnit.INCHES);
		
		System.out.println(length1.add(length2));
		
		System.out.println(demonstarteLengthAddition(length1, length2, Length.LengthUnit.YARDS));
		
		System.out.println(demonstarteLengthAddition(6.0, Length.LengthUnit.INCHES, 6.0, Length.LengthUnit.INCHES, Length.LengthUnit.INCHES));
	}
	
}