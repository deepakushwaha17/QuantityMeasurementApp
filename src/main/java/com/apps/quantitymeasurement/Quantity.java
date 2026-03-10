package com.apps.quantitymeasurement;

/**
* In UC13, the Quantity class has been further enhanced to ensure DRY principles by
* introducing the following:
* 1. The private method validateArithmeticOperands to centralize 
*    validation logic for arithmetic operations.
* 2. Additionally, an internal enum ArithmeticOperation has been added to 
*    represent the type of arithmetic operation being performed,
* 3. The performArithmetic method has been added to execute the specified 
*    arithmetic operation on the base unit values of the quantities involved. 
*    
* The performArithmetic method now handles addition, subtraction, and division 
* operations in a unified manner, reducing code duplication and improving readability.
**/

public class Quantity<U extends IMeasurable> {
	private double value;
	private U unit;

	private static final double EPSILON = 1e-9;
	
	public Quantity(double value, U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}
	
	public double toBaseUnit() {
		return this.convertToBaseUnit(this.value);
	}

	// Arithmetic enum
	private enum ArithmeticOperation {

		ADD {
			@Override
			double compute(double a, double b) {
				return a + b;
			}
		},

		SUBTRACT {
			@Override
			double compute(double a, double b) {
				return a - b;
			}
		},

		DIVIDE {
			@Override
			double compute(double a, double b) {
				if (Math.abs(b) < EPSILON)
					throw new ArithmeticException("Division by zero");
				return a / b;
			}
		};

		abstract double compute(double a, double b);
	}
	
	/**
	* Validates the operands for arithmetic operations (addition, subtraction, division).
	* This method validates the following
	*1. NULL values,
	* 2. compatibility of unit types, and
	* 3. finiteness of numeric values.
	* 4. It also validates the target unit if required for addition and subtraction operations
	*
	* @param other the other Quantity involved in the operation
	* @param targetUnit the target unit for the result (if required)
	* @param targetUnitRequired indicates whether the target unit is required for the operation
	* @throws IllegalArgumentException if any validation fails
	**/
	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
		if (other == null) {
			throw new IllegalArgumentException("Other cannot be null.");
		}
		
		if (targetUnitRequired && targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		
		if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
		}

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite");
        }
    }
	
	/**
	* Performs the specified arithmetic operation (addition, subtraction, division)
	*  on this Quantity and another Quantity, returning the result in base units.
	**/
    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

        double base1 = this.toBaseUnit();
        double base2 = other.toBaseUnit();

        return operation.compute(base1, base2);
    }
    
	/**
	 * Converts this Quantity to the specified target unit.
	 * 
	 * <p>
	 * This method first converts the current value to the base unit using the
	 * convertToBaseUnit method of the current unit, then converts that base value
	 * to the target unit using the convertFromBaseUnit method of the target unit.
	 */

	public <U extends IMeasurable> double convertTo(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		double baseValue = this.unit.convertToBaseUnit(this.value);

		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return Math.round(convertedValue * 100.0) / 100.0;
	}

	private double convertToBaseUnit(double value) {

		return this.unit.convertToBaseUnit(value);
	}

	/**
	 * Adds this Quantity to another Quantity of the same unit type.
	 * 
	 * <p>
	 * This method converts both quantities to their base unit, adds the values, and
	 * then converts the sum back to the unit of this Quantity.
	 */

	public Quantity<U> add(Quantity<U> other) {
		unit.validateOperationSupport("addition");
		
		validateArithmeticOperands(other, this.unit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double converted = unit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(converted), unit);
	}

	/**
	 * Adds this Quantity to another Quantity of the same unit type and returns the
	 * result in the specified target unit.
	 * 
	 * <p>
	 * This method converts both quantities to their base unit, adds the values, and
	 * then converts the sum to the specified target unit.
	 */

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		unit.validateOperationSupport("addition");
		validateArithmeticOperands(other, targetUnit, true);
		
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(baseResult);
		
		return new Quantity<U>(round(converted), targetUnit);
	}

	/**
	 * Subtracts this Quantity from another Quantity of the same unit type and
	 * returns the result in the unit of this Quantity.
	 *
	 * @param other the other Quantity to subtract
	 * @return a new Quantity representing the difference
	 **/
	public Quantity<U> subtract(Quantity<U> other) {
		unit.validateOperationSupport("subtraction");
		validateArithmeticOperands(other, this.unit, true);
		
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = unit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round(converted), this.unit);
	}

	/**
	 * Subtracts this Quantity from another Quantity of the same unit type and
	 * returns the result in a specified target unit.
	 *
	 * @param other      the other Quantity to subtract
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the difference in the target unit
	 */
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		unit.validateOperationSupport("subtraction");
		validateArithmeticOperands(other, targetUnit, true);
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round(converted), targetUnit);
	}

	/**
	 * Divides this Quantity by another Quantity of the same unit type and returns
	 * the result as a double.
	 *
	 * @param other the other Quantity to divide by
	 * @return the result of the division as a double
	 */
	public double divide(Quantity<U> other) {
		unit.validateOperationSupport("division");
		validateArithmeticOperands(other, this.unit, true);
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	/**
	 * Compares this Quantity with another object for equality. Two Quantity objects
	 * are considered equal if they represent the same measurement value when
	 * converted to their respective base units.
	 *
	 * Logic to compare two Quantity objects: 1. Check if the other object is an
	 * instance of Quantity. 2. If not, return false. 3. If yes, convert both
	 * Quantity values to their base units using the convertToBaseUnit method of
	 * their respective units. 4. Compare the converted values for equality. 5.
	 * Return true if they are equal, false otherwise.
	 */

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Quantity<?> that)) {
			return false;
		}

		return this.compare(that);
	}

	@Override
	public int hashCode() {
		double baseValue = unit.convertToBaseUnit(value);
		return Double.hashCode(baseValue);
	}

	private boolean compare(Quantity<?> that) {

		if (!this.unit.getClass().equals(that.unit.getClass())) {
	        return false;
	    }

	    double thisBase = this.toBaseUnit();
	    double thatBase = that.toBaseUnit();

	    return Math.abs(thisBase - thatBase) < EPSILON;
	}
	
	private double round(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}

	public static void main(String[] args) {

		// Conversion method
		Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.YARDS);
		System.out.println("10 yards = " + lengthInFeet.convertTo(LengthUnit.INCHES) + " inches");

		Quantity<WeightUnit> weightInKG = new Quantity<>(5.0, WeightUnit.KILOGRAM);
		System.out.println("5 KG = " + weightInKG.convertTo(WeightUnit.GRAM) + " grams");

		// Exception case of conversion method
		try {
			Quantity<WeightUnit> weightInTonne = new Quantity<>(1.0, WeightUnit.TONNE);
			System.out.println(weightInTonne.convertTo(null));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Addition method
		Quantity<LengthUnit> lengthInYards = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> lengthInInch = new Quantity<>(36.0, LengthUnit.INCHES);
		System.out.println("1 yard + 36 inches = " + lengthInYards.add(lengthInInch));

		Quantity<WeightUnit> weightInGram = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInMiligram = new Quantity<>(10000.0, WeightUnit.MILLIGRAM);
		System.out.println("1000 gram + 10000 milligram = " + weightInGram.add(weightInMiligram));

		// Exception case of addition method
		try {
			weightInGram.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Overloaded addition method with specific target unit
		System.out.println(lengthInYards.add(lengthInInch, LengthUnit.FEET));

		System.out.println(weightInGram.add(weightInMiligram, WeightUnit.KILOGRAM));

		// Subtraction method
		System.out.println("36 inches - 1 yard = " + lengthInInch.subtract(lengthInYards));
		System.out.println("10000 milligram - 1000 gram = " + weightInMiligram.subtract(weightInGram));

		// Overloaded subtraction method with specific target unit
		System.out.println(lengthInYards.subtract(lengthInInch, LengthUnit.FEET));

		System.out.println(weightInMiligram.subtract(weightInGram, WeightUnit.KILOGRAM));

		// Exception case of subtraction method
		try {
			weightInGram.subtract(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Division method
		System.out.println("36 inches / 1 yard = " + lengthInInch.divide(lengthInYards));
		System.out.println("10000 milligram / 1000 gram = " + weightInMiligram.divide(weightInGram));

		// Exception case of divide method
		try {
			weightInGram.divide(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Equal Method example
		Quantity<LengthUnit> lengthInCm = new Quantity<>(254.0, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> lengthInInches = new Quantity<>(100.0, LengthUnit.INCHES);
		System.out.println("254.0 centimeters = 100.0 inches -> " + lengthInCm.equals(lengthInInches));

		Quantity<WeightUnit> lengthInPound = new Quantity<>(1000.0, WeightUnit.POUND);
		Quantity<WeightUnit> lengthInKilogram = new Quantity<>(453.592, WeightUnit.KILOGRAM);
		System.out.println("1000.0 pound = 453.592 kilogram -> " + lengthInPound.equals(lengthInKilogram));

		// Volume conversion
		Quantity<VolumeUnit> volumeInLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volumeInML = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> volumeInGallon = new Quantity<>(1.0, VolumeUnit.GALLON);

		System.out.println("1 litre = " + volumeInLitre.convertTo(VolumeUnit.MILLILITRE) + " millilitres");
		System.out.println("1000 millilitres = " + volumeInML.convertTo(VolumeUnit.LITRE) + " litres");
		System.out.println("1 gallon = " + volumeInGallon.convertTo(VolumeUnit.LITRE) + " litres");

		// Exception case for conversion
		try {
			System.out.println(volumeInLitre.convertTo(null));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Volume Addition
		Quantity<VolumeUnit> vol1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> vol2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
		System.out.println("1 litre + 500 millilitres = " + vol1.add(vol2));
		System.out.println("1 litre + 500 millilitres in gallon = " + vol1.add(vol2, VolumeUnit.GALLON));

		// Exception case of addition
		try {
			weightInGram.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Volume Subtraction
		System.out.println("1 litre - 500 millilitres = " + vol1.subtract(vol2));
		System.out.println("1 litre - 500 millilitres in gallon = " + vol1.subtract(vol2, VolumeUnit.GALLON));

		// Exception case of subtraction
		try {
			weightInGram.subtract(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Volume Divide
		System.out.println("1 litre / 500 millilitres = " + vol1.divide(vol2));

		// Exception case of division
		try {
			weightInGram.divide(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		// Arithmetic Exception case of division
		try {
			Quantity<WeightUnit> weight = new Quantity<>(0.0, WeightUnit.GRAM);
			weightInGram.divide(weight);
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}

		// Equal method
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		System.out.println("1 litre = 1000 millilitres -> " + v1.equals(v2));

		Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> v4 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		System.out.println("1 gallon = 3.78541 litre -> " + v3.equals(v4));
	}
}
