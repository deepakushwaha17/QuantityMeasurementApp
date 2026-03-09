package com.apps.quantitymeasurement;

/*
* LengthUnit.java
* The LengthUnit enumeration defines various units of length measurement
* along with their conversion factors relative to a base unit (inches).
* This enumeration is used in the QuantityMeasurement application to facilitate.
* conversions and comparisons between different length units.
* <p>The base unit for conversion is inches. Each unit stores a conversion factor
* relative to inches (the base unit). This design simplifies unit conversions by
* always converting through a common base unit. </p>
* <p>Example: 1 FOOT 12.0 inches, 1 YARD 36.0 inches,
* 1 CENTIMETER = 0.393701 inches</p>
*/

public enum LengthUnit {
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
	
	/*
	* Convert value from this unit to base unit (inches). New responsibility added.
	* <p> This method is used internally for all conversions. It ensures consistent
	* rounding to two decimal places across all operations.
	* @param value the value in this unit
	* @return the value converted to base unit (inch) and then rounded to two decimal places
	*/	
	public double convertToBaseUnit(double value) {
		double base = value * this.getConversionFactor();
		return Math.round(base * 100.0) / 100.0; 
	}
	
	/*
	* Convert value from base unit (inches) to this unit. New responsibility added.
	* <p> This method is used internally for all conversions. It ensures consistent rounding to two decimal places across all operations.
	* @param baseValue the value in base unit (inches)
	* @return the value converted to this unit and then rounded to two decimal places
	*/
	public double convertFromBaseUnit(double baseValue) {
		double convertedValue = baseValue / this.getConversionFactor();
		return Math.round(convertedValue * 100.0) / 100.0;
	}
	
}