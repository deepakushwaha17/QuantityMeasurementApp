package com.apps.quantitymeasurement;

/*QuantityMeasurementApp UC1: Feet measurement equality
* This class is responsible for checking the equality of two numerical values 
* measured in feet in the Quantity Measurement Application.*/

public class QuantityMeasurementApp {
	
	// Inner class to represent feet measurement
	public static class Feet{
		private final double value;
		
		public Feet(double value){
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			
			// Reference check
			if(this == obj) {
				return true;
			}
			
			// Null check
			if(obj == null) {
				return false;
			}
			
			// Type check
			if(getClass() != obj.getClass()) {
				return false;
			}
			
			// Type Cast to Feet type
			Feet other = (Feet) obj;
			
			// Compare using Double.compare()
			return Double.compare(this.value, other.value) == 0;
		}
	}
	
	// Inner class to represent inches measurement
	public static class Inches{
		private final double value;
		
		public Inches(double value){
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			
			// Reference check
			if(this == obj) {
				return true;
			}
			
			// Null check
			if(obj == null) {
				return false;
			}
			
			// Type check
			if(getClass() != obj.getClass()) {
				return false;
			}
			
			// Type Cast to Inches type
			Inches other = (Inches) obj;
			
			// Compare using Double.compare()
			return Double.compare(this.value, other.value) == 0;
		}
	}
	
	// Defining a static method to demonstrate Feet Equality check
	public static void demonstrateFeetEquality() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		
		boolean result = feet1.equals(feet2);
		System.out.println("Equal (" + result + ")");
	}
	
	// Defining a static method to demonstrate Inches Equality check
	public static void demonstrateiInchesEquality() {
		Inches inches1 = new Inches(1.0);
		Inches inches2 = new Inches(1.0);
		
		boolean result = inches1.equals(inches2);
		System.out.println("Equal (" + result + ")");
	}
	
	public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateiInchesEquality();
		
	}
}
