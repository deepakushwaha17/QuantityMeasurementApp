package com.apps.quantitymeasurement.application;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

/**
 * QuantityMeasurementApp UC16 - Database Connection
 */
public class QuantityMeasurementApp {

	public static void main(String[] args) {

		// Repository
		IQuantityMeasurementRepository repository = QuantityMeasurementDatabaseRepository.getInstance();

		// Service
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);

		System.out.println("===== Quantity Measurement Application =====");

		// Compare
		QuantityDTO length1 = new QuantityDTO(24.0, "INCHES", "LengthUnit");

		QuantityDTO length2 = new QuantityDTO(2.0, "FEET", "LengthUnit");

		boolean equal = service.compare(length1, length2);

		System.out.println("Are lengths equal? " + equal);

		// Convert
		QuantityDTO weight1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");

		QuantityDTO target = new QuantityDTO(0.0, "POUND", "WeightUnit");

		QuantityDTO converted = service.convert(weight1, target);

		System.out.println("Converted weight = " + converted.getValue() + " " + converted.getUnit());

		// Addition
		QuantityDTO litre = new QuantityDTO(1.0, "LITRE", "VolumeUnit");

		QuantityDTO milliLitre = new QuantityDTO(1000.0, "MILLILITRE", "VolumeUnit");

		QuantityDTO sum = service.add(litre, milliLitre);

		System.out.println("Sum volume = " + sum.getValue() + " " + sum.getUnit());

		// Subtraction
		QuantityDTO feet = new QuantityDTO(10.0, "FEET", "LengthUnit");

		QuantityDTO inches = new QuantityDTO(6.0, "INCHES", "LengthUnit");

		QuantityDTO subtraction = service.subtract(feet, inches);

		System.out.println("Subtraction result = " + subtraction.getValue() + " " + subtraction.getUnit());

		// Division
		QuantityDTO kg = new QuantityDTO(10.0, "KILOGRAM", "WeightUnit");

		QuantityDTO kg2 = new QuantityDTO(5.0, "KILOGRAM", "WeightUnit");

		double division = service.divide(kg, kg2);

		System.out.println("Division result = " + division);

		System.out.println("Operations completed successfully");

	}
}