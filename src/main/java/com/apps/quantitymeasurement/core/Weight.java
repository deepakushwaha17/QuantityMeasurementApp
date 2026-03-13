package com.apps.quantitymeasurement.core;

import java.util.Objects;

/* Represents a weight quantity with support for different units of measurement.
* This class mirrors the design of Weight class and provides functionality
* for weight comparison and conversion between different weight units.
* 
* <p>The class encapsulates a weight value and its associated unit, allowing for:
* <ul>
*  <li>Comparison of weights in different units</li>
*  <li>Conversion between various weight units</li>
*  <li>Standard weight arithmetic operations</li>
* </ul>
* 
* <p>
* Supported weight units are defined in the nested {@link WeightUnit} enum,
* which includes common units such as grams, kilograms, pounds, ounces, etc.
* <p>
* 
* Example usage:
* <pre>
* Weight weight1= new Weight(1000, WeightUnit.GRAM);
* Weight weight2 new Weight(1, WeightUnit.KILOGRAM);
* boolean isEqual weight1.equals(weight2); // true
* </pre>
*/

public class Weight {
	
	// Instance variable to hold weight value and unit
	private double value;
	private WeightUnit unit;
	
	// Constructor to initialize weight value and unit
	public Weight(double value, WeightUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value.");
		}
		
		this.value = value;
		this.unit = unit;
	}

	// Getter for value
	public double getValue() {
		return value;
	}

	// Getter for unit
	public WeightUnit getUnit() {
		return unit;
	}

	/**
	* Compare this weight with another weight for equality.
	* <p><b>Overridden Method:</b> Implements the {@link Object#equals(Object)} contract.
	* Performs reference equality check first, then type validation, and finally delegates
	* to the {@link #compare(Weight)} method for value-based comparison.
	* <p><b>Algorithm:</b>
	* <ol>
	* <li>Check if both references point to the same object (early optimization)</li>
	* <li>Validate that the other object is not null and is of type {@code Weight}</li>
	* <li>Cast to {@code Weight) and invoke {@link #compare(Weight)}</li>
	* </ol>
	*
	* @param o the object to compare with this {@code Weight}
	* @return {@code true if both represent the same weight in the base unit (grams), {@code false} otherwise
	*/
	public boolean equals (Object o) {
		// Reference check
		if(this == o) {
			return true;
		}
				
		// Null check
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
				
		// Type Cast to Weight type
		Weight other = (Weight) o;
				
		// Compare 
		return compare(other);
		
	}
	
	/**
	* Convert this weight to the specified target unit.
	*
	* <p><b>Public API Method:</b> Provides the primary interface for unit conversion.
	* This method implements the conversion pipeline: base unit conversion, target unit conversion, * and rounding to maintain precision consistency.
	* <p><b>Conversion Pipeline:</b>
	* <ol>
	*  <li>Validate that {@code targetUnit) is not null (throws {@link IllegalArgumentException} if null)</li>
	*  <li>Convert this instance to the base unit (grams) using {@link #convertToBaseUnit()}</li>
	*  <li>Convert from grams to the target unit by dividing by the target unit's conversion factor</li>
	*  <li>Round the result to two decimal places</li>
	*  <li>Return a new {@code Weight} instance with the converted value</li>
	* </ol>
	* <p><b>Immutability Guarantee:</b> This method never modifies the receiver; it always returns
	*a new {@code Weight) instance, ensuring that the original object remains unchanged.
	*
	* @param targetUnit the unit to convert this weight into; must not be null
	* @return a new {@code Weight} representing the same physical weight in {@code targetUnit}, with the numeric value rounded to two decimal places
	* @throws IllegalArgumentException if {@code targetUnit} is null
	* @see WeightUnit
	*/
	public Weight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
			
		// Convert to base (grams)
		double baseValue = convertToBaseUnit();

		// Convert from grams to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Weight(convertedValue, targetUnit);	
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}

	/**
	* Adds another {@code Weight) to this one.
	*
	* <p><b>Public API Method:</b> This method allows adding two weights of the same category.
	* The result is returned in the unit of the first operand, with rounding applied for consistency.
	* <p><b>Addition Pipeline:</b>
	* <ol>
	<li>Convert both weights to the base unit (grams)</li>
	<li>Sum the base unit values</li>
	<li>Convert the sum back to the unit of this instance</li>
	<li>Round the result to two decimal places</li>
	<li>Return a new {@code Weight} instance with the summed value</li>
	* </ol>
	* @param thatWeight the {@code Weight} to add
	* @return a new {@code Weight) representing the sum in this instance's unit
	*/
	public Weight add (Weight thatWeight) {
		if (thatWeight == null) {
			throw new IllegalArgumentException("Weight cannot be null");
		}
		
		return addAndConvert(thatWeight, this.unit);
	}
	
	/**
	* Adding weight to this weight with target unit specification.
	*
	* <p><b>Public API Method:</b> This method allows adding two weights specified
	* by their numeric values and units. The result is returned as per the unit of
	* specified target unit.
	*
	* @param weight the {@code Weight} to add
	* @param targetUnit the unit to return the sum in
	* @return a new {@code Weight} representing the sum in the specified target unit
	*/
	public Weight add (Weight weight, WeightUnit targetUnit) {
		if (weight == null) {
	        throw new IllegalArgumentException("Weight cannot be null");
	    }

	    if (targetUnit == null) {
	        throw new IllegalArgumentException("Target unit cannot be null");
	    }

	    return addAndConvert(weight, targetUnit);
		
	}
	
	/**
	* Converts this weight value to the base unit (grams) by delegating to the unit enum.
	*
	* <p><b>Private Utility Method:</b> This method is used internally for all conversions * and comparisons. It calls the {@link WeightUnit#convertToBaseUnit (double)) method * of the {@code WeightUnit} enum to perform the conversion and rounding.
	*
	* @return the weight value in grams, rounded to two decimal places
	*/
	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0; 
	}
	
	/**
	* Compares two {@code Weight) objects for equality based on their base
	* unit values.
	* <p><b>Private Helper Method:</b> Encapsulates the core comparison logic by
	* converting both weights to the base unit and performing a numerical comparison.
	*
	@param thatWeight the {@code Weight} object to compare with
	* @return {@code true if both weights represent the same physical weight in grams,
	* {@code false} otherwise
	*/
	private boolean compare (Weight thatWeight) {
		if(thatWeight == null) {
			return false;
		}
		return Double.compare(this.convertToBaseUnit(), thatWeight.convertToBaseUnit()) == 0;
	}
	
	/**
	* Private utility method to perform addition conversion on base unit value.
	* <p><b>Private Utility Method:</b> This method is used internally by the
	* {@link #add(Weight)} and {@link #add(Weight, WeightUnit)}
	* methods to convert this weight and the weight to add into the base unit,
	* sum them up, and convert the result into the specified target unit.
	* @param weight the {@code Weight to add
	* @param targetUnit the unit to return the sum in
	* @return a new {@code Weight) representing the sum in the specified target unit
	*/
	private Weight addAndConvert (Weight weight, WeightUnit targetUnit) {
		double thisInBase = this.convertToBaseUnit();
		double thatInBase = weight.convertToBaseUnit();
		 
		double sumInBase = thisInBase + thatInBase;
		double resultValue = convertFromBaseToTargetUnit(sumInBase, targetUnit);
		    
		return new Weight(resultValue,targetUnit);
	}
	
	/**
	* Converts a weight value from the base unit (grams) to the specified target unit.
	*
	* <p><b>Private Utility Method:</b> This method is used internally by the
	* {@link #convert To (WeightUnit)}, {@link #add (Weight)}, and
	* {@link #add(Weight, WeightUnit)} methods to perform
	* the final step of conversion from grams to the desired target unit.
	* <p>This method mainly came into existence to avoid code duplication in the
	* conversion process as both the convertTo and add methods require this functionality.
	* This method internally calls the WeightUnit's conversion factor to perform the conversion.
	*
	* @param weightInGrams the weight value in grams to convert
	@param targetUnit the unit to convert the weight into
	* @return the converted weight value in the target unit, rounded to two decimal places
	*/
	private double convertFromBaseToTargetUnit(double weightInGrams, WeightUnit targetUnit) {
		Weight weight = new Weight(weightInGrams, WeightUnit.GRAM);
		Weight result = weight.convertTo(targetUnit);
		return result.value;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}
	
	public static void main(String[] args) {
		
		Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1000.0, WeightUnit.GRAM);
	    Weight w3 = new Weight(2.20462, WeightUnit.POUND);
	    Weight w4 = new Weight(500.0, WeightUnit.GRAM);

	    // Equality comparisons
	    System.out.println(w1 + " equals " + w2 + " ? " + w1.equals(w2)); 
	    System.out.println(w1 + " equals " + w3 + " ? " + w1.equals(w3));
	    System.out.println(w4 + " equals " + new Weight(0.5, WeightUnit.KILOGRAM) + " ? " 
	                        + w4.equals(new Weight(0.5, WeightUnit.KILOGRAM))); 

	    // Unit conversions
	    System.out.println(w1 + " in grams: " + w1.convertTo(WeightUnit.GRAM));
	    System.out.println(w3 + " in kilograms: " + w3.convertTo(WeightUnit.KILOGRAM));
	    System.out.println(w4 + " in pounds: " + w4.convertTo(WeightUnit.POUND));

	    // Addition (implicit target unit)
	    System.out.println(w1 + " + " + w2 + " = " + w1.add(w2));
	    System.out.println(w1 + " + " + w4 + " = " + w1.add(w4));

	    // Addition (explicit target unit)
	    System.out.println(w1 + " + " + w4 + " in grams = " + w1.add(w4, WeightUnit.GRAM));
	    System.out.println(w3 + " + " + w2 + " in pounds = " + w3.add(w2, WeightUnit.POUND));
	    
	    // Exception Test
	 	try {
	 		new Weight(Double.NaN, WeightUnit.GRAM);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}

	 	try {
	 		new Weight(5.0, null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}

	 	try {
	 		w1.convertTo(null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
		}
	 		
	 	try {
	 		w1.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
	 	}
	 		
	 	try {
	 		w1.add(w2, null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}
	}
}
