package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

/* QuantityMeasurementAppUC3 Unified Quantity Measurement System
 *  
* This class addresses the DRY (Don't Repeat Yourself) principle violations
* present in the previous implementation where separate Feet and Inches classes
* contained nearly identical code with:
* -Duplicate equals() method implementations
* - Identical constructor patterns
* -Redundant validation logic
* 
* UC3 introduces a unified approach to handle multiple units of length measurement
* by consolidating common functionality and eliminating code duplication.
* This refactoring promotes code reusability, maintainability, and scalability
* for adding new measurement units without repeating boilerplate code.
**/

public class QuantityMeasurementApp {
	
	// Create a generic method to demonstrate Length Equality check
	public static boolean demonstrateLengthEquality(Length length1 , Length length2) {
		boolean result = length1.equals(length2);
		return result;
	}
	
	// Defining a static method to demonstrate Feet Equality check
	public static void demonstrateFeetEquality() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(1.0, LengthUnit.FEET);

        boolean result = demonstrateLengthEquality(length1, length2);
        System.out.println("Equal (" + result + ")");
	}
	
	// Defining a static method to demonstrate Inches Equality check
	public static void demonstrateInchesEquality() {
		Length length1 = new Length(12.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.FEET);

        boolean result = demonstrateLengthEquality(length1, length2);
        System.out.println("Equal (" + result + ")");
	}
	
	// Defining a static method to demonstrate Inches Equality check
	public static void demonstrateFeetInchesComparison() {
		Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCHES);	
		
        boolean result = demonstrateLengthEquality(length1, length2);
        System.out.println("Equal (" + result + ")");
   }
	
	// Main method to demonstrate Feet and Inches equality checks
	// and comparison checks between Feet and Inches
	public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateInchesEquality();
		demonstrateFeetInchesComparison();
		
	}
}
