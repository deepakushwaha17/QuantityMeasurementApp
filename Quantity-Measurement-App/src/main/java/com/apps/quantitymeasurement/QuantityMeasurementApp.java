package com.apps.quantitymeasurement;


/*QuantityMeasurementAppUC4 Use Case 4: Extended Unit Support
 * 
 * This class extends the Quantity Measurement Application (UC3) by introducing support 
 * for additional length units: yards and centimeters.
 * 
 * <p>UC4 enhances the previous implementation by:
 * <ul>
 *	<li>Adding yard (yd) as a supported unit of length measurement</li>
 *	<li>Adding centimeter (cm) as a supported unit of length measurement</li>
 *	<li>Enabling conversion between all supported length units including feet, inches, yards, and centimeters</li>
 *	<li>Maintaining backward compatibility with existing unit conversions from UC3</li>
 *</ul>
 *<p><b>Supported Units:</b>
 *<ul>
 *  <li>Feet (ft)</li>
 *  <li>Inches (in)</li>
 *	<li>Yards (yd)</li>
 *  <li>Centimeters (cm)</li>
 *</ul>
**/

public class QuantityMeasurementApp {
	
	// Create a generic method to demonstrate Length Equality check
	public static boolean demonstrateLengthEquality(Length length1 , Length length2) {
		boolean result = length1.equals(length2);
		return result;
	}
	
	// Create a static method to take method parameters and demonstrate equality check
	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1 , 
			double value2, Length.LengthUnit unit2) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		
		return demonstrateLengthEquality(length1,length2);
		
	}
	
	
	// Main method to demonstrate extended unit support
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
	}
}
