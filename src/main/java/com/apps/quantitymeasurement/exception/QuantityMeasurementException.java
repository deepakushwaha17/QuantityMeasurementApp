package com.apps.quantitymeasurement.exception;

public class QuantityMeasurementException extends RuntimeException{

	public QuantityMeasurementException(String message) {
		super(message);
	}

	public QuantityMeasurementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public static void main(String[] args) {
		try {
			throw new QuantityMeasurementException("This is a test exception for quantity");
		}catch(QuantityMeasurementException e) {
			System.out.println("Caught QuantityMeasurementException : " + e.getMessage());
		}
	}
}