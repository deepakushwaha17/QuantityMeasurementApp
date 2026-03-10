package com.apps.quantitymeasurement;

/**
 * IMeasurable interface defines the contract for measurable units.
 * 
 * This interface serves as a common abstraction for different types of measurements
 * such as weight and length units. Classes implementing this interface should provide
 * functionality to handle unit conversions and comparisons between different measurement types.
 * 
 * @see WeightUnit
 * @see LengthUnit
 */


public interface IMeasurable {
	
	/**Get the conversion factor to the base unit (grams).
	 * @return the conversion factor to grams.
	 **/
	public double getConversionFactor();
	
	/** Convert value from this unit to base unit (inches). New responsibility added.
	 * <p> This method is used internally for all conversions. It ensures consistent
	 * rounding to two decimal places across all operations.
	 * 
	 * @param value the value in this unit
	 * @return the value converted to base unit (inch) and then rounded to two decimal places
	 **/	
	public double convertToBaseUnit (double value);
	
	/**Convert value from base unit (inches) to this unit. New responsibility added.
	 * <p> This method is used internally for all conversions. It ensures consistent
	 * rounding to two decimal places across all operations.
	 * @param baseValue the value in base unit (inches)
	 * @return the value converted to this unit and then rounded to two decimal places
	 * */
	public double convertFromBaseUnit (double baseValue);
	
	// main method to test IMeasurable interface	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface");
	}
}