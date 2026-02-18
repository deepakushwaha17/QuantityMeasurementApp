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
	
	public static void main(String[] args) {
		 
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		
		boolean result = feet1.equals(feet2);
		System.out.println("Equal (" + result + ")");
	}
}
