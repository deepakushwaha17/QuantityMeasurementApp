package com.apps.quantitymeasurement.entity;

import java.util.Objects;

import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.dto.QuantityModel;


public class QuantityMeasurementEntity  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;
	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;
	
	public String operation;
	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;
	
	// For comparing results like "Equal" or "Not Equal"
	public String resultString;
	
	public boolean isError;
	public String errorMessage;
	
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation) {
		this.thisValue = thisQuantity.value;
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType = thisQuantity.getUnit().getMeasurementType();
		this.operation = operation;
		
		if(thatQuantity != null) {
			this.thatValue = thatQuantity.value;
			this.thatUnit = thatQuantity.getUnit().getUnitName();
			this.thatMeasurementType = thatQuantity.getUnit().getMeasurementType();
		}
		
	}
	
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, String result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}
	
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, QuantityModel<IMeasurable> result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.value;
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType = result.getUnit().getMeasurementType();
	}
	
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, String errorMessage, boolean isError) {
		this(thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (!(obj instanceof QuantityMeasurementEntity)) return false;
        
        QuantityMeasurementEntity other = (QuantityMeasurementEntity) obj;
  
        return Double.compare(thisValue, other.thisValue) == 0
                && Double.compare(thatValue, other.thatValue) == 0
                && Objects.equals(thisUnit, other.thisUnit)
                && Objects.equals(thatUnit, other.thatUnit)
                && Objects.equals(operation, other.operation);
    }

    @Override
    public String toString() {
        if (isError)
            return "[ERROR] " + operation + " | " + errorMessage;
        
        if (resultString != null) {
            return operation + " | " + thisValue + " " + thisUnit
                    + " vs " + thatValue + " " + thatUnit
                    + " -> " + resultString;
        }
        return operation + " | " + thisValue + " " + thisUnit
                + " & " + thatValue + " " + thatUnit
                + " = " + resultValue + " " + resultUnit;
    }
}
