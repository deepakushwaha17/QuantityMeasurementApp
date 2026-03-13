package com.apps.quantitymeasurement.dto;

import com.apps.quantitymeasurement.core.IMeasurable;

/**
* QuantityModel A generic POJO model class for representing a quantity with its
* associated unit of measurement of type {@link IMeasurable}.
*
* <p>This class serves as a container for a numeric value and its corresponding unit,
* where the unit must implement the {@link IMeasurable} interface. It provides a clean
* separation between the value and its measurement unit.</p>
*
* The purpose of this DTO class is to be used within the service layer for performing
* operations on quantities, such as conversion, comparison, addition, subtraction, and division. 
* Also used as a DTO for storing the quantities in the repository. It allows for a flexible 
* representation of quantities with different types of measurable units. The generic type 
* parameter U allows for any unit that implements the IMeasurable interface, making it adaptable
* to various measurement types such as length, weight, volume, and temperature.
* 
* provide a simple and flexible way to represent quantities
* in the application, especially when working with different types of measurable units.
*
* @param <U> the type of unit, which must implement {@link IMeasurable}
*/

public class QuantityModel<U extends IMeasurable> {

	public double value;
	public U unit;
	
	public QuantityModel(double value, U unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}
	
	@Override
	public String toString() {
		return "QuantityModel [value=" + value + ", unit=" + unit + "]";
	}
}