package com.apps.quantitymeasurement.application;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.Quantity;
import com.apps.quantitymeasurement.core.TemperatureUnit;
import com.apps.quantitymeasurement.core.VolumeUnit;
import com.apps.quantitymeasurement.core.WeightUnit;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

/**
 * QuantityMeasurementApp UC16 - Database Connection
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
	 * @param quantity   the quantity to convert
	 * @param targetUnit the target unit for conversion
	 * @return a new Quantity with the converted value and unit
	 */

	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		double convertedValue = quantity.convertTo(targetUnit);
		return new Quantity<U>(convertedValue, targetUnit);
	}

	/**
	 * Demonstrate Addition of two quantities and return the result in the unit of
	 * the first quantity.
	 * 
	 * @param quantity1 the first quantity to add
	 * @param @param    quantity1 the first quantity to add
	 * @return a new Quantity representing the sum
	 */

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,
			Quantity<U> quantity2) {
		return quantity1.add(quantity2);
	}

	/**
	 * Demonstrate Addition of two quantities and return the result in a specified
	 * target unit.
	 * 
	 * @param quantity1  the first quantity to add
	 * @param quantity2  the second quantity to add
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the sum in the target unit
	 */

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2,
			U targetUnit) {
		return quantity1.add(quantity2, targetUnit);
	}

	/**
	 * Demonstrate Subtraction of two quantities and return result in the unit of
	 * the first quantity.
	 *
	 * @param quantityl the first quantity to subtract from
	 * @param quantity2 the second quantity to subtract
	 * @return a new Quantity representing the difference
	 */
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1,
			Quantity<U> quantity2) {
		return quantity1.subtract(quantity2);
	}

	/**
	 * Demonstrate Subtraction of two quantities and return result in a specified
	 * target unit
	 *
	 * @param quantity1  the first quantity to subtract from
	 * @param quantity2  the second quantity to subtract
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the difference in the target unit
	 */
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1,
			Quantity<U> quantity2, U targetUnit) {
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
	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2) {
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
		System.out.println(
				"Sum Volume in Gallons: " + sumVolumeInGallons.getValue() + " " + sumVolumeInGallons.getUnit());

		// Demonstration subtraction of two quantities and return the result in the unit
		// of the first quantity
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

		// Demonstration subtraction of two quantities and return the result in specific
		// target unit
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

		try {
			Quantity<VolumeUnit> vol1 = new Quantity<>(3.0, VolumeUnit.LITRE);
			Quantity<VolumeUnit> vol2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
			double divideVol = demonstrateDivision(vol1, vol2);
			System.out.println("Divide Volume: " + divideVol);
		} catch (ArithmeticException e) {
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

		// Wire up N-Tier layers (Factory Pattern)
		ApplicationConfig config = ApplicationConfig.getInstance();
        IQuantityMeasurementRepository repository;
        if (config.isDatabaseRepository()) {
            repository = QuantityMeasurementDatabaseRepository.getInstance();
        } else {
            repository = QuantityMeasurementCacheRepository.getInstance();
        }

		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);

		QuantityMeasurementController controller = new QuantityMeasurementController(service);
		System.out.println("\n=== N-Tier Quantity Measurement App ===\n");

		// Length Equality & Conversion
		System.out.println("--- LENGTH COMPARISON ---");
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS),
				new QuantityDTO(3.0, QuantityDTO.LengthUnit.FEET));

		System.out.println("\n--- LENGTH CONVERSION ---");
		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

		// Length Addition
		System.out.println("\n--- LENGTH ADDITION ---");
		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.YARDS)); // target = YARDS

		// Weight
		System.out.println("\n--- WEIGHT OPERATIONS ---");
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));
		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));
		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));

		// Volume
		System.out.println("\n--- VOLUME OPERATIONS ---");
		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
				new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE));
		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
				new QuantityDTO(2.0, QuantityDTO.VolumeUnit.LITRE));
		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.VolumeUnit.GALLON),
				new QuantityDTO(0.0, QuantityDTO.VolumeUnit.LITRE));

		// Subtraction & Division
		System.out.println("\n--- SUBTRACTION ---");
		controller.performSubtraction(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES));
		controller.performSubtraction(new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(5000.0, QuantityDTO.WeightUnit.GRAM),
				new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));

		System.out.println("\n--- DIVISION ---");
		controller.performDivision(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));
		controller.performDivision(new QuantityDTO(2000.0, QuantityDTO.WeightUnit.GRAM),
				new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM));

		// Temperature
		System.out.println("\n TEMPERATURE");
		controller.performComparison(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
				new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
		controller.performConversion(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
				new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));

		try {
			controller.performAddition(new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
					new QuantityDTO(20.0, QuantityDTO.TemperatureUnit.CELSIUS));
		} catch (QuantityMeasurementException e) {
			System.out.println("Expected error → " + e.getMessage());
		}

		// Operation History
		System.out.println("\n--- OPERATION HISTORY ---");
		repository.getAllMeasurements().forEach(System.out::println);
	}
}