package com.apps.quantitymeasurement;

/** UC5 QuantityMeasurementApp UCS Extended Unit Support with Conversion
* <p>Extends UC4 to provide unit-to-unit conversion for values within the same measurement
* category (for example, length-to-length conversions such as feet-to-inches or
* yards-to-inches). This class builds on the validation and equality checks from UC4
* and adds explicit conversion logic so callers can obtain a numeric result in the
* requested target unit.</p>
<p>Responsibilities:
<ul>
	<li>Validate that source and target units belong to the same measurement category (e.g., both are length units).</li>
	<li>Perform conversion by normalizing the source value to a canonical/base unit and then converting to the target unit.</li>
	<li>Return a numeric result with a defined precision (implementation-defined, typically a fixed decimal/rounded value).</li>
	<li>Throw an IllegalArgumentException for null inputs or incompatible unit pairs.</li>
* </ul>
* </p>
* <p>Examples:
* <pre>
*Convert 3 feet to inches => 36.0
* Convert 2 yards to inches => 72.0
* </pre>
* </p>
*
* <p>Notes:
* <ul>
*   <li>Only conversions within the same category are allowed; cross-category conversion (e.g., length-to-weight or length-to-time) is not supported.</li>
*   <li>Supported units include (but are not limited to) feet, inches, yards and other length units provided by UC4.</li>
* </ul>
* </p>
**/

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
	}
	
}