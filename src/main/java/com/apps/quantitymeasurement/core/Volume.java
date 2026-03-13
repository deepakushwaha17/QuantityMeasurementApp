package com.apps.quantitymeasurement.core;

import java.util.Objects;

/* Represents a volume quantity with support for different units of measurement.
* This class mirrors the design of Volume class and provides functionality
* for volume comparison and conversion between different volume units.
* 
* <p>The class encapsulates a volume value and its associated unit, allowing for:
* <ul>
*  <li>Comparison of volumes in different units</li>
*  <li>Conversion between various volume units</li>
*  <li>Standard volume arithmetic operations</li>
* </ul>
* 
* <p>
* Supported volume units are defined in the nested {@link VolumeUnit} enum,
* which includes common units such as litre, millilitre, gallon.
* <p>
* 
* Example usage:
* <pre>
* Volume volume1= new Volume(1000, VolumeUnit.MILLILITRE);
* Volume volume2 new Volume(1, VolumeUnit.LITRE);
* boolean isEqual volume1.equals(volume2); // true
* </pre>
*/

public class Volume {
	// Instance variable to hold volume value and unit
	private double value;
	private VolumeUnit unit;
		
	// Constructor to initialize volume value and unit
	public Volume(double value, VolumeUnit unit) {
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
	public VolumeUnit getUnit() {
		return unit;
	}
	
	/**
	* Compare this volume with another volume for equality.
	* <p><b>Overridden Method:</b> Implements the {@link Object#equals(Object)} contract.
	* Performs reference equality check first, then type validation, and finally delegates
	* to the {@link #compare(Weight)} method for value-based comparison.
	* <p><b>Algorithm:</b>
	* <ol>
	* <li>Check if both references point to the same object (early optimization)</li>
	* <li>Validate that the other object is not null and is of type {@code Volume}</li>
	* <li>Cast to {@code Volume) and invoke {@link #compare(Volume)}</li>
	* </ol>
	*
	* @param o the object to compare with this {@code Volume}
	* @return {@code true if both represent the same volume in the base unit (litre), {@code false} otherwise
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
				
		// Type Cast to Volume type
		Volume other = (Volume) o;
				
		// Compare 
		return compare(other);		
	}
	
	/**
	* Convert this volume to the specified target unit.
	*
	* <p><b>Public API Method:</b> Provides the primary interface for unit conversion.
	* This method implements the conversion pipeline: base unit conversion, target unit conversion, 
	* and rounding to maintain precision consistency.
	* <p><b>Conversion Pipeline:</b>
	* <ol>
	*  <li>Validate that {@code targetUnit) is not null (throws {@link IllegalArgumentException} if null)</li>
	*  <li>Convert this instance to the base unit (grams) using {@link #convertToBaseUnit()}</li>
	*  <li>Convert from grams to the target unit by dividing by the target unit's conversion factor</li>
	*  <li>Round the result to two decimal places</li>
	*  <li>Return a new {@code Volume} instance with the converted value</li>
	* </ol>
	* <p><b>Immutability Guarantee:</b> This method never modifies the receiver; it always returns
	*a new {@code Volume) instance, ensuring that the original object remains unchanged.
	*
	* @param targetUnit the unit to convert this volume into; must not be null
	* @return a new {@code Volume} representing the same physical volume in {@code targetUnit}, with the numeric value rounded to two decimal places
	* @throws IllegalArgumentException if {@code targetUnit} is null
	* @see VolumeUnit
	*/
	
	public Volume convertTo(VolumeUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
			
		// Convert to base (grams)
		double baseValue = convertToBaseUnit();

		// Convert from grams to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Volume(convertedValue, targetUnit);	
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}
	
	/**
	* Adds another {@code Volume) to this one.
	*
	* <p><b>Public API Method:</b> This method allows adding two volume of the same category.
	* The result is returned in the unit of the first operand, with rounding applied for consistency.
	* <p><b>Addition Pipeline:</b>
	* <ol>
	<li>Convert both volumes to the base unit (litre)</li>
	<li>Sum the base unit values</li>
	<li>Convert the sum back to the unit of this instance</li>
	<li>Round the result to two decimal places</li>
	<li>Return a new {@code Volume} instance with the summed value</li>
	* </ol>
	* @param thatVolume the {@code Volume} to add
	* @return a new {@code Volume) representing the sum in this instance's unit
	*/
	public Volume add (Volume thatVolume) {
		if (thatVolume == null) {
			throw new IllegalArgumentException("Volume cannot be null");
		}
		
		return addAndConvert(thatVolume, this.unit);
	}
	
	/**
	* Adding weight to this volume with target unit specification.
	*
	* <p><b>Public API Method:</b> This method allows adding two volumes specified
	* by their numeric values and units. The result is returned as per the unit of
	* specified target unit.
	*
	* @param volume the {@code Volume} to add
	* @param targetUnit the unit to return the sum in
	* @return a new {@code Volume} representing the sum in the specified target unit
	*/
	public Volume add (Volume volume, VolumeUnit targetUnit) {
		if (volume == null) {
	        throw new IllegalArgumentException("Volume cannot be null");
	    }

	    if (targetUnit == null) {
	        throw new IllegalArgumentException("Target unit cannot be null");
	    }

	    return addAndConvert(volume, targetUnit);
	}
	
	/**
	* Converts this volume value to the base unit (litre) by delegating to the unit enum.
	*
	* <p><b>Private Utility Method:</b> This method is used internally for all conversions 
	* and comparisons. It calls the {@link VolumeUnit#convertToBaseUnit (double)) method 
	* of the {@code VolumeUnit} enum to perform the conversion and rounding.
	*
	* @return the volume value in litre, rounded to two decimal places
	*/
	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0; 
	}
	
	/**
	* Compares two {@code Volume) objects for equality based on their base
	* unit values.
	* <p><b>Private Helper Method:</b> Encapsulates the core comparison logic by
	* converting both volumes to the base unit and performing a numerical comparison.
	*
	@param thatVolume the {@code Volume} object to compare with
	* @return {@code true if both weights represent the same physical volume in litre,
	* {@code false} otherwise
	*/
	private boolean compare (Volume thatVolume) {
		if(thatVolume == null) {
			return false;
		}
		return Double.compare(this.convertToBaseUnit(), thatVolume.convertToBaseUnit()) == 0;
	}
	
	/**
	* Private utility method to perform addition conversion on base unit value.
	* <p><b>Private Utility Method:</b> This method is used internally by the
	* {@link #add(Volume)} and {@link #add(Volume, VolumeUnit)}
	* methods to convert this volume and the volume to add into the base unit,
	* sum them up, and convert the result into the specified target unit.
	* @param volume the {@code Volume to add
	* @param targetUnit the unit to return the sum in
	* @return a new {@code Volume) representing the sum in the specified target unit
	*/
	private Volume addAndConvert (Volume volume, VolumeUnit targetUnit) {
		double thisInBase = this.convertToBaseUnit();
		double thatInBase = volume.convertToBaseUnit();
		 
		double sumInBase = thisInBase + thatInBase;
		double resultValue = convertFromBaseToTargetUnit(sumInBase, targetUnit);
		    
		return new Volume(resultValue,targetUnit);
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
	private double convertFromBaseToTargetUnit(double volumeInLitre, VolumeUnit targetUnit) {
		Volume volume = new Volume(volumeInLitre, VolumeUnit.LITRE);
		Volume result = volume.convertTo(targetUnit);
		return result.value;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}
	
	public static void main(String[] args) {
		
		Volume v1 = new Volume(1.0, VolumeUnit.LITRE);
		Volume v2 = new Volume(1000.0, VolumeUnit.MILLILITRE);
		Volume v3 = new Volume(3.78541, VolumeUnit.GALLON);

	    // Equality comparisons
	    System.out.println(v1 + " equals " + v2 + " ? " + v1.equals(v2)); 
	    System.out.println(v1 + " equals " + v3 + " ? " + v1.equals(v3));

	    // Unit conversions
	    System.out.println(v1 + " in millilitre: " + v1.convertTo(VolumeUnit.MILLILITRE));
	    System.out.println(v3 + " in litre: " + v3.convertTo(VolumeUnit.LITRE)); 

	    // Addition (implicit target unit)
	    System.out.println(v1 + " + " + v2 + " = " + v1.add(v2));
	    System.out.println(v1 + " + " + v3 + " = " + v1.add(v3));

	    // Addition (explicit target unit)
	    System.out.println(v1 + " + " + v2 + " in litre = " + v1.add(v2, VolumeUnit.LITRE));
	    System.out.println(v2 + " + " + v3 + " in gallon = " + v2.add(v3, VolumeUnit.GALLON));
	    
	    // Exception Test
	 	try {
	 		new Volume(Double.NaN, VolumeUnit.LITRE);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}

	 	try {
	 		new Volume(5.0, null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}

	 	try {
	 		v1.convertTo(null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
		}
	 		
	 	try {
	 		v2.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
	 	}
	 		
	 	try {
	 		v1.add(v2, null);
	 	} catch (IllegalArgumentException e) {
	 		System.out.println("Exception occurred: " + e.getMessage());
	 	}
	}

}
