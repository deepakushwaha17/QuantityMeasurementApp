package com.apps.quantitymeasurement;

/**
* QuantityMeasurementApp - UC9 - Weight Unit Enum with Base Unit Conversion 
* <p>UC9 introduces the WeightUnit enum to handle weight measurements, 
* enabling conversion to a base unit (grams) for consistent comparison and arithmetic operations.
* This is in addition to the existing LengthUnit enum for length measurements.
* <ul>
*  <li>MILLIGRAM</li>
*  <li>GRAM</li>
*  <li>KILOGRAM</li>
*  <li>POUND</li>
*  <li>TONNE</li>
* </ul>
* 
* <p>Each weight unit includes methods to convert to and from the base unit,
* facilitating accurate comparisons and calculations across different weight units.
* Also included are methods for equality checks, conversions, and addition of weights.
* In addition to the length functionality provided in UC8, UC9 extends similar capabilities
* to weight measurements.
* <ul>
*  <li>WeightUnit enum defines conversion factors relative to grams</li>
*  <li>Conversion methods ensure consistent rounding to two decimal places</li>
*  <li>Weight class updated to utilize WeightUnit for weight measurements</li>
*  <li>Quantity MeasurementAppUC9 class added for demonstration and testing</li>
*  <li>All references to WeightUnit enum are updated accordingly</li>
* </ul>
* <p>Similar to LengthUnit in UC8, WeightUnit enum provides a structured way to 
* manage weight units and their conversions, enhancing the overall functionality of the
* QuantityMeasurement application.
* 
* <ul>
*  <li>Weight and QuantityMeasurementAppUC9 classes import WeightUnit</li>
*  <li>All references to WeightUnit enum are updated accordingly</li>
* </ul>
**/

public class QuantityMeasurementApp {
	
	// New Methods for Weight Functionality
	
	/**
	* Demonstrate weight equality between two Weight instances.
	* @param weight1 the first Weight instance
	* @param weight2 the second Weight instance
	* @return true if the two weights are equal, false otherwise
	*/
	public static boolean demonstrateWeightEquality(Weight weight1, Weight weight2){
		return weight1.equals(weight2);
	}
	
	/**
	* Demonstrate weight comparison between two weights specified by value and unit.
	* @param valuel the first weight value
	* @param unitl the unit of the first weight
	* @param value2 the second weight value
	* @param unit2 the unit of the second weight
	* @return true if the two weights are equal, false otherwise
	*/
	public static boolean demonstrateWeightComparison(double value1, WeightUnit unit1, double value2 , WeightUnit unit2) {
		Weight weight1 = new Weight(value1, unit1);
		Weight weight2 = new Weight(value2, unit2);
		
		return demonstrateWeightEquality(weight1 , weight2);
		
	}
	
	/**
	* Demonstrate weight conversion from one unit to another.
	* @param value the weight value to convert
	* @param fromUnit the unit of the weight value
	* @param toUnit the target unit to convert to
	* @return a new Weight instance representing the converted weight
	*/
	public static Weight demonstrateWeightConversion(double value, WeightUnit fromUnit, WeightUnit targetUnit) {
		Weight weight = new Weight(value, fromUnit);
		return weight.convertTo(targetUnit);
	}
	
	/**
	Demonstrate weight conversion from one Weight instance to another unit.
	* Method overload is used. Method Overloading means having multiple methods
	* with the same name but different parameter lists within the same class.
	* @param weight the Weight instance to convert
	* @param toUnit the target unit to convert to
	* @return a new Weight instance representing the converted weight
	*/
	public static Weight demonstrateWeightConversion (Weight weight, WeightUnit toUnit) {	
		return weight.convertTo(toUnit);
	}
	
	/**
	* Demonstrate addition of second Weight to first Weight.
	* @param weight1 the first Weight instance
	* @param weight2 the second Weight instance
	* @return a new Weight instance representing the sum of the two weights
	*/
	public static Weight demonstrateWeightAddition(Weight weight1 , Weight weight2) {
		return weight1.add(weight2);
		
	}
	/** Demonstrate addition of second Weight to first Weight
	* with target unit.
	*
	* @param weight1 the first Weight instance
	* @param weight2 the second Weight instance
	* @param targetUnit the target unit for the result
	* @return a new Weight instance representing the sum of the two weights
	* in the target unit
	**/
	public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2, WeightUnit targetUnit) {
		return weight1.add(weight2, targetUnit);
	}
	
	// Methods for Length functionality
	
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
		
		// Weight operations
		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.GRAM, 1.0, WeightUnit.KILOGRAM));

		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.MILLIGRAM, 1.0, WeightUnit.GRAM));

		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.KILOGRAM, 1.0, WeightUnit.TONNE));

		System.out.println(demonstrateWeightConversion(1.0, WeightUnit.TONNE, WeightUnit.KILOGRAM));

		System.out.println(demonstrateWeightConversion(2.0, WeightUnit.POUND, WeightUnit.GRAM));

		System.out.println(demonstrateWeightConversion(5000.0, WeightUnit.MILLIGRAM, WeightUnit.GRAM));
		
		System.out.println(demonstrateWeightConversion(0.0, WeightUnit.GRAM, WeightUnit.KILOGRAM));

		Weight weight1 = new Weight(500.0, WeightUnit.GRAM);
		Weight weight2 = new Weight(1.0, WeightUnit.KILOGRAM);
		System.out.println(demonstrateWeightAddition(weight1, weight2));

		System.out.println(demonstrateWeightAddition(weight1, weight2, WeightUnit.POUND));

		Weight weight3 = new Weight(1.0, WeightUnit.TONNE);
		Weight weight4 = new Weight(500.0, WeightUnit.KILOGRAM);
		System.out.println(demonstrateWeightAddition(weight3, weight4, WeightUnit.TONNE));
	}
	
}