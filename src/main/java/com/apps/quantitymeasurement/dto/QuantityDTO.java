
/**
* QuantityDTO Data Transfer Object (P0J0) for holding quantity measurement
* input data value and unit.
* 
* POJO classes like QuantityDTO are used to encapsulate data and provide a structured way to 
* represent the input for quantity measurements. This class holds the numeric value of the 
* quantity and the corresponding unit of measurement.
*
* The purpose of POJO classes is to provide a simple and clear representation of data 
* without any business logic. They are often used in conjunction with service layers and 
* controllers to transfer data between different parts of an application, such as from the 
* user interface to the backend services.
* DTOS are commonly used to transfer data between different layers of an application, such as 
* from the controller to the service layer, or from the service layer to the repository. They 
* help in decoupling the internal data models from the external representations used for input and output.
*
* <p>This class encapsulates quantity input consisting of a numeric value and its 
* corresponding unit of measurement. The supported measurement types include:</p>
* <ul>
*  <li>Length (e.g., feet, inches, yards, centimeters, meters)</li>
*  <li>Weight (e.g., grams, kilograms, pounds, ounces)</li>
*  <li>Volume (e.g., liters, milliliters, gallons)</li>
*  <li>Temperature (e.g., Celsius, Fahrenheit, Kelvin)</li>
* </ul>
**/

package com.apps.quantitymeasurement.dto;

public class QuantityDTO {
	
	/**
	* IMeasurableUnit Interface to represent measurable units for quantity measurements. 
	* This interface defines the contract for measurable units, including methods to get the unit name 
	* and the measurement type. It is * implemented by the various unit enums defined within the QuantityDTO class, 
	* such as LengthUnit, VolumeUnit, WeightUnit, and TemperatureUnit.
	* 
	* The purpose of redefining the IMeasurableUnit interface and the MeasurableUnit enums within this DTO class 
	* is to provide a self-contained representation of * the quantity input. This design allows the QuantityDTO to be easily 
	* instantiated * with specific units and values without requiring external dependencies on the IMeasurableUnit 
	* interface or the unit enums defined elsewhere in the application. * It also simplifies the process of creating 
	* QuantityDTO instances for testing and * demonstration purposes, as all necessary unit definitions are included 
	* within the class itself.
	*/

	interface IMeasurableUnit {
		 String getUnitName();
		 String getMeasurementType();
	}

	// Enums for different measurable units, implementing the IMeasurableUnit interface. 
	public enum LengthUnit implements IMeasurableUnit{
		FEET, INCHES, YARDS, CENTIMETERS;
		
		@Override
		public String getUnitName() {
			return this.name();
		}
		
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}
	
	public enum VolumeUnit implements IMeasurableUnit{
		MILLILITRE, LITRE, GALLON;
		
		@Override
		public String getUnitName() {
			return this.name();
		}
		
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}	
	}
	
	public enum WeightUnit implements IMeasurableUnit{
		MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE;
		
		@Override
		public String getUnitName() {
			return this.name();
		}
		
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}	
	}
	
	public enum TemperatureUnit implements IMeasurableUnit{
		CELSIUS, FAHRENHEIT;
		
		@Override
		public String getUnitName() {
			return this.name();
		}
		
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}	
	}
	
	public double value;
	public String unit;
	public String measurementType;
	
	public QuantityDTO(double value, IMeasurableUnit unit) {
		this.value = value;
		this.unit = unit.getUnitName();
		this.measurementType = unit.getMeasurementType();
	}

	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}
	
	@Override
	public String toString() {
		return "QuantityDTO(" + value + ", " + unit + ", " + measurementType + ")";
	}
	
}