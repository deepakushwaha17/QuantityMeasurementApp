 package com.apps.quantitymeasurement;
import java.util.Objects;

/*
* Use Case 8: Refactor QuantityLength to Use Standalone LengthUnit Enum
* <p>Refactor the {@code QuantityLength} class to delegate unit conversion 
* responsibilities to a standalone {@code LengthUnit} enum. This improves 
* separation of concerns and encapsulation by isolating unit-specific logic.
* 
* Following changes are made:
* - Remove all internal conversion logic from QuantityLength.
* - Update all conversion operations to delegate to unit methods.
* - Simplify the class to focus on equality and arithmetic.
*/

public class Length {
	
	// Instance variables
	private double value;
	private LengthUnit unit;
	
	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value.");
		}
		this.value = value;
		this.unit = unit;
	}
	
	// Convert length value to base unit (inches) and round off to two decimal places
	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0; 
	}
	
	// Compare to Length object for equality based on their values in the base unit
	public boolean compare(Length thatLength) {
		if(thatLength == null) {
			return false;
		}
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}
	
	// Equals method is overridden to firstly check if two objects are the same references
	// If not, if other object is null or of different class.
	// Finally, calls the compare method to determine equality based on converted values.
	@Override
	public boolean equals(Object obj) {
		
		// Reference check
		if(this == obj) {
			return true;
		}
		
		// Null check
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		// Type Cast to Length type
		Length other = (Length) obj;
		
		// Compare 
		return compare(other);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}
	
	// Conversion Method
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
			
		// Convert to base (inches)
		double baseValue = convertToBaseUnit();

		// Convert from inches to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Length(convertedValue, targetUnit);
	}

	/*  Adds another {@code Length} to this one.
	* <p><b>Public API Method:</b> This method allows adding two lengths of the same category.
	* The result is returned in the unit of the first operand, with rounding applied for consistency.
	* <p><b>Addition Pipeline:</b>
	* <ol>
	*   <li>Convert both lengths to the base unit (inches)</li>
	*   <li>Sum the base unit values</li>
	*   <li>Convert the sum back to the unit of this instance</li>
	*   <li>Round the result to two decimal places</li>
	*   <li>Return a new {@code Length} instance with the summed value</li>
	* </ol>
	* @param thatLength the {@code Length} to add
	* @return a new {@code Length} representing the sum in this instance's unit
	*/
	public Length add(Length thatLength) {
		
		if (thatLength == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
		
		return addAndConvert(thatLength, this.unit);
	}
	
	public Length add(Length thatLength, LengthUnit targetUnit) {

	    if (thatLength == null) {
	        throw new IllegalArgumentException("Length cannot be null");
	    }

	    if (targetUnit == null) {
	        throw new IllegalArgumentException("Target unit cannot be null");
	    }

	    return addAndConvert(thatLength, targetUnit);
	}
	
	/**
	Private utility method to perform addition conversion on base unit value.
	<p><b>Private Utility Method:</b> This method is used internally by the
	{@link #add(Length)} and {@link #add(Length, LengthUnit)}
	methods to convert this length and the length to add into the base unit, sum them up, and convert the result into the specified target unit.
	@param length the {@code Length} to add
	@param targetUnit the unit to return the sum in
	@return a new {@code Length) representing the sum in the specified target unit
	*/
	
	private Length addAndConvert(Length length, LengthUnit targetUnit) {
		double thisInBase = this.convertToBaseUnit();
		double thatInBase = length.convertToBaseUnit();
		 
		double sumInBase = thisInBase + thatInBase;
		double resultValue = convertFromBaseToTargetUnit(sumInBase, targetUnit);
		    
		return new Length(resultValue,targetUnit);
		
	}
	
	private double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit) {
		Length inches = new Length(lengthInInches, LengthUnit.INCHES);
		Length result = inches.convertTo(targetUnit);
		return result.value;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	// Main method for stand alone testing
	public static void main(String[] args) {

		// 1 Foot = 12 Inches
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("1 Foot equals 12 Inches ? " + length1.equals(length2));

		// 1 Yard = 36 Inches
		Length length3 = new Length(1.0, LengthUnit.YARDS);
		Length length4 = new Length(36.0, LengthUnit.INCHES);
		System.out.println("1 Yard equals 36 Inches ? " + length3.equals(length4));

		// 100 cm ≈ 39.3701 Inches
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);
		System.out.println("100 CM equals 39.3701 Inches ? " + length5.equals(length6));

		// Conversion Test
		Length feet = new Length(3.0, LengthUnit.FEET);
		Length convertedToYards = feet.convertTo(LengthUnit.YARDS);
		System.out.println("3 Feet in Yards = " + convertedToYards);

		Length convertedToCm = feet.convertTo(LengthUnit.CENTIMETERS);
		System.out.println("3 Feet in CM = " + convertedToCm);

		// toString Test
		System.out.println("String Representation: " + feet);
		
		// 3 Feet + 12 Inches = 4 Feet
		Length l1 = new Length (3.0, LengthUnit.FEET);
		Length l2 = new Length (12.0, LengthUnit. INCHES);
		Length result = l1.add(l2);
		System.out.println("3 Feet + 12 Inches = " + result);
		
		System.out.println("3 Feet + 12 Inches in INCHES = " + l1.add(l2, LengthUnit.INCHES));
				
		// 2.54 Centimeters + 1 Inches = ~5.08 Centimeters
		Length cm = new Length(2.54, LengthUnit.CENTIMETERS);
		Length inch = new Length(1.0, LengthUnit.INCHES);
		System.out.println("2.54 Centimeters + 1 Inches = " + cm.add(inch));

		// Exception Test
		try {
			new Length(Double.NaN, LengthUnit.FEET);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}

		try {
			new Length(5.0, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}

		try {
			feet.convertTo(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
		
		try {
			l1.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
		
		try {
			l1.add(l2, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}
}