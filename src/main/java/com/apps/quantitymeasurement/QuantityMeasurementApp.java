package com.apps.quantitymeasurement;
/**
* QuantityMeasurementApp UC12 extends UC11 to implement subtraction and division operations
* for quantity measurement.
*
* This class provides functionality to perform subtraction and division operations on 
* different units of measurement. It extends the capabilities of UC11 by adding support for 
* these additional arithmetic operations.
*
* Key Features:
* 1. Subtraction of quantities with automatic unit conversion
* 2. Division of quantities with automatic unit conversion.
* 3. Demonstration methods showcasing subtraction operations
* 4. Demonstration methods showcasing division operations
* 
* <p>
* <b>Use Cases Covered:</b>
* <ul>
* 	<li><b>Comparison:</b> Compare two quantities of the same type to determine equality or magnitude</li>
* 	<li><b>Conversion:</b> Convert quantities from one unit to another within the same measurement type</li>
* 	<li><b>Addition:</b> Add two quantities of compatible types and return the result in a specified unit</li>
* </ul>
* </p>
*
* <p>
* <b>Architecture:</b>
* This class acts as a demonstration/driver class that internally utilizes the {@link Quantity} class
* to perform all mathematical and conversion operations on various measurement types such as:
* <ul>
* 	<li>Length (Feet, Inch, Yard, Centimeter, etc.)</li>
* 	<li>Volume (Liter, Milliliter, Gallon, Pint, etc.)</li>
* 	<li>Weight (Kilogram, Gram, Tonne, Pound, etc.)</li>
* 	<li>Temperature (Celsius, Fahrenheit)</li>
* </ul>
* </p>
*/

public class QuantityMeasurementApp {
	
	/**
	 * Demonstrate Equality Comparison between two quantities.
	 * 
	 * @param quantity1 the first quantity to compare
	 * @param quantity2 the second quantity to compare
	 * @return true if quantities are equal, false otherwise
	 */
	
	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.equals(quantity2);
	}
	
	/**
	 * Demonstrate Conversion of a quantity to a target unit.
	 * 
	 * @param quantity the quantity to convert
	 * @param targetUnit the target unit for conversion
	 * @return a new Quantity with the converted value and unit
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		double convertedValue = quantity.convertTo(targetUnit);
		return new Quantity<U>(convertedValue, targetUnit);
	}
	
	/**
	 * Demonstrate Addition of two quantities and return the result in the unit of the first quantity.
	 * 
	 * @param quantity1 the first quantity to add
	 * @param @param quantity1 the first quantity to add
	 * @return a new Quantity representing the sum
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,
			Quantity<U> quantity2) {
		return quantity1.add(quantity2);
	}
	
	/**
	 * Demonstrate Addition of two quantities and return the result in a specified target unit.
	 * 
	 * @param quantity1 the first quantity to add
	 * @param quantity2 the second quantity to add
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the sum in the target unit
	*/
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2,
			U targetUnit) {
		return quantity1.add(quantity2, targetUnit);
	}
	
	/**
	* Demonstrate Subtraction of two quantities and return result in the unit of the first quantity.
	*
	* @param quantityl the first quantity to subtract from
	* @param quantity2 the second quantity to subtract
	* @return a new Quantity representing the difference
	*/
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction (Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.subtract(quantity2);
	}
	
	/**
	* Demonstrate Subtraction of two quantities and return result in a specified target unit
	*
	* @param quantity1 the first quantity to subtract from
	* @param quantity2 the second quantity to subtract
	* @param targetUnit the target unit for the result
	* @return a new Quantity representing the difference in the target unit
	*/
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction (Quantity<U> quantity1, Quantity<U> quantity2, 
			U targetUnit) {
		return quantity1.subtract(quantity2, targetUnit);
	}
	
	/**
	* Demonstrate Division of two quantities and return result in double.
	*
	* @param quantity1 the first quantity to divide
	* @param quantity2 the second quantity to divide by
	* @return result of the division as a double
	* @throws ArithmeticException if division by zero occurs
	*/
	public static <U extends IMeasurable> double demonstrateDivision (Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.divide(quantity2);
	}

	// Main method for demonstration

	public static void main(String[] args) {
		
		// Demonstration equality between the two quantities
		Quantity<LengthUnit> lengthInInches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> lengthInFeet = new Quantity<>(5.0, LengthUnit.FEET);
		boolean areEqual = demonstrateEquality(lengthInInches, lengthInFeet);
		System.out.println("Are lengths equal? " + areEqual);
		
		System.out.println("Hashcode: " + lengthInInches.hashCode() + " " + lengthInFeet.hashCode());

		Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		System.out.println("Are weights equal? " + demonstrateEquality(weightInGrams, weightInKilograms));
		
		// Demonstration conversion between the two quantities
		Quantity<LengthUnit> convertedLength = demonstrateConversion(lengthInInches, LengthUnit.FEET);
		System.out.println("Converted Length: " + convertedLength.getValue() + " " + convertedLength.getUnit());

		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInKilograms, WeightUnit.POUND);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " + convertedWeight.getUnit());
		
		// Demonstration addition of two quantities and return the result in the unit
		// of the first quantity
		Quantity<LengthUnit> lengthInYards = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> sumLength = demonstrateAddition(lengthInFeet, lengthInYards);
		System.out.println("Sum Length: " + sumLength.getValue() + " " + sumLength.getUnit());

		Quantity<WeightUnit> weightInPound = new Quantity<>(1.0, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPound);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " + sumWeight.getUnit());
		
		// Demonstration addition of two quantities and return the result in a specified
		// target unit
		Quantity<LengthUnit> lengthInCm = new Quantity<>(39.3701, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> sumLengthInYards = demonstrateAddition(lengthInInches, lengthInCm, LengthUnit.YARDS);
		System.out.println("Sum Length in Yards: " + sumLengthInYards.getValue() + " " + sumLengthInYards.getUnit());

		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPound, WeightUnit.GRAM);
		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());
		
		// Volume Demonstration
	    Quantity<VolumeUnit> volumeInLitres = new Quantity<>(1.0, VolumeUnit.LITRE);
	    Quantity<VolumeUnit> volumeInML = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
	    Quantity<VolumeUnit> volumeInGallons = new Quantity<>(1.0, VolumeUnit.GALLON);

	    // Equality
	    System.out.println("Are volumes equal? " + demonstrateEquality(volumeInLitres, volumeInML));

	    // Conversion
	    Quantity<VolumeUnit> convertedVolume = demonstrateConversion(volumeInGallons, VolumeUnit.LITRE);
	    System.out.println("Converted Volume: " + convertedVolume.getValue() + " " + convertedVolume.getUnit());

	    // Addition (unit of first quantity)
	    Quantity<VolumeUnit> sumVolume = demonstrateAddition(volumeInLitres, volumeInML);
	    System.out.println("Sum Volume: " + sumVolume.getValue() + " " + sumVolume.getUnit());

	    // Addition (specific target unit)
	    Quantity<VolumeUnit> sumVolumeInGallons = demonstrateAddition(volumeInLitres, volumeInML, VolumeUnit.GALLON);
	    System.out.println("Sum Volume in Gallons: " + sumVolumeInGallons.getValue() + " " + sumVolumeInGallons.getUnit());
	    
	    // Demonstration subtraction of two quantities and return the result in the unit of the first quantity
	    Quantity<LengthUnit> subLength = demonstrateSubtraction(lengthInFeet, lengthInInches);
		System.out.println("Subtract Length: " + subLength.getValue() + " " + subLength.getUnit());
		
		Quantity<WeightUnit> weight1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
	    Quantity<WeightUnit> weight2 = new Quantity<>(500.0, WeightUnit.GRAM);
		Quantity<WeightUnit> subWeight = demonstrateSubtraction(weight1, weight2);
		System.out.println("Subtract Weight: " + subWeight.getValue() + " " + subWeight.getUnit());
		
		Quantity<VolumeUnit> volume1 = new Quantity<>(3.0, VolumeUnit.LITRE);
	    Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
	    Quantity<VolumeUnit> subVolume = demonstrateSubtraction(volume1, volume2);
		System.out.println("Subtract Volume: " + subVolume.getValue() + " " + subVolume.getUnit());
		
		// Demonstration subtraction of two quantities and return the result in specific target unit
	    Quantity<LengthUnit> subLengthInYards = demonstrateSubtraction(lengthInFeet, lengthInInches, LengthUnit.YARDS);
		System.out.println("Subtract Length: " + subLengthInYards.getValue() + " " + subLengthInYards.getUnit());
		
		Quantity<WeightUnit> subWeightInPounds = demonstrateSubtraction(weight1, weight2, WeightUnit.POUND);
		System.out.println("Subtract Weight: " + subWeightInPounds.getValue() + " " + subWeightInPounds.getUnit());
		
		Quantity<VolumeUnit> subVolumeInGallon = demonstrateSubtraction(volume1, volume2, VolumeUnit.GALLON);
		System.out.println("Subtract Volume: " + subVolumeInGallon.getValue() + " " + subVolumeInGallon.getUnit());
		
		// Demonstration division of two quantities and return the result in double
		double divideLength = demonstrateDivision(lengthInFeet, lengthInInches);
		System.out.println("Divide Length: " + divideLength);
		
		double divideWeight = demonstrateDivision(weight1, weight2);
		System.out.println("Divide Weight: " + divideWeight);
		
		double divideVolume = demonstrateDivision(volume1, volume2);
		System.out.println("Divide Volume: " + divideVolume);
		
		try{
			Quantity<VolumeUnit> vol1 = new Quantity<>(3.0, VolumeUnit.LITRE);
			Quantity<VolumeUnit> vol2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
			double divideVol = demonstrateDivision(vol1, vol2);
			System.out.println("Divide Volume: " + divideVol);
		}catch(ArithmeticException e) {
			System.out.println("Exception occur : " + e.getMessage());
		}	
		
		System.out.println("===Temperature Demonstration ===");
		
		// Equality Demonstration
		Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        System.out.println("0°C equals 32°F: " + temp1.equals(temp2));
		
		// Conversion Demonstration
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        double fahrenheit = celsius.convertTo(TemperatureUnit.FAHRENHEIT);

        System.out.println("100°C = " + fahrenheit + "°F");
		
		// Unsupported Operation Demonstration
        try {
            celsius.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add absolute temperatures: " + e.getMessage());
        }
	}
}