package com.apps.quantitymeasurement;

/*
* QuantityMeasurementApp UC8 Refactoring Unit Enum to Standalone Class
* <p>UC8 refactors the design from UC1-UC7 to overcome the disadvantage of embedding the 
* LengthUnit enum within the QuantityLength class. Since UC8 extracts the LengthUnit enum into a standalone class, 
* hence it is needed to update the QuantityMeasurementAppUC8 and QuantityLength classes to 
* import the LengthUnit from the new standalone class and refactor the code accordingly.
* </p>
* 
* <p>Examples:
* <ul>
*  <li>LengthUnit enum is now a standalone class in Quantity MeasurementApp package</li>
*  <li>QuantityLength and QuantityMeasurementAppUC8 classes import LengthUnit</li>
*  <li>All references to LengthUnit enum are updated accordingly</li>
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
	public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1 , 
			double value2, LengthUnit unit2) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		
		return demonstrateLengthEquality(length1,length2);
		
	}

	 // UC5: Convert one length to target unit
	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit targetUnit) {

		Length length = new Length(value, fromUnit);
		return length.convertTo(targetUnit);
	}

	// UC5: Overloaded Conversion using Length object
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {

		return length.convertTo(toUnit);
	}
	
	// UC6 : Add length to the target length
	public static Length demonstrateLengthAddition(Length targetLength, Length givenLength) {
		return targetLength.add(givenLength);
	}
		
	// UC6 : Overloaded Method & Add two lengths and then converted them into target unit
	public static Length demonstrateLengthAddition(Length length1, Length length2, LengthUnit targetUnit) {
		Length totalLength = length1.add(length2, targetUnit);
		return totalLength;
	}
		
	// UC6 : Overloaded Addition to provide flexibility
	public static Length demonstrateLengthAddition(double value1, LengthUnit unit1, double value2, LengthUnit unit2, LengthUnit targetUnit) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		return demonstrateLengthAddition(length1, length2, targetUnit);
	}

	// Main method
	public static void main(String[] args) {
		//Demonstrate Feet and Inches comparison
		boolean result1 = demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0 , LengthUnit.INCHES);
		System.out.println("Equal (" + result1 + ")");
		
		//Demonstrate Yards and Inches comparison
		boolean result2 = demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0 , LengthUnit.INCHES);
		System.out.println("Equal (" + result2 + ")");
				
		//Demonstrate Centimeters and Inches comparison
		boolean result3 = demonstrateLengthComparison(100.0, LengthUnit.CENTIMETERS, 39.3701 , LengthUnit.INCHES);
		System.out.println("Equal (" + result3 + ")");
				
		//Demonstrate Feet and Yards comparison
		boolean result4 = demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0 , LengthUnit.YARDS);
		System.out.println("Equal (" + result4 + ")");
				
		//Demonstrate Centimeters and Feet comparison
		boolean result5 = demonstrateLengthComparison(30.48, LengthUnit.CENTIMETERS, 1.0 , LengthUnit.FEET);
		System.out.println("Equal (" + result5 + ")");

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES)
				.convertTo(LengthUnit.INCHES).toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET).toString()
				.split(" ")[0]);

		System.out.println(demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES).toString()
				.split(" ")[0]);
		
		Length length1 = new Length(3.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		
		System.out.println(length1.add(length2));
		
		System.out.println(demonstrateLengthAddition(length1, length2, LengthUnit.YARDS));
		
		System.out.println(demonstrateLengthAddition(6.0, LengthUnit.INCHES, 6.0, LengthUnit.INCHES, LengthUnit.INCHES));
	}
	
}