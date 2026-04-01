package com.app.quantitymeasurementapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {

    private Double  thisValue;
    private String  thisUnit;
    private String  thisMeasurementType;

    private Double  thatValue;
    private String  thatUnit;
    private String  thatMeasurementType;

    private String  operation;

    private String  resultString;
    private Double  resultValue;
    private String  resultUnit;
    private String  resultMeasurementType;

    private String  errorMessage;
    private boolean error;

    // ==================== Factory methods ====================

    public Double getThisValue() {
		return thisValue;
	}

	public void setThisValue(Double thisValue) {
		this.thisValue = thisValue;
	}

	public String getThisUnit() {
		return thisUnit;
	}

	public void setThisUnit(String thisUnit) {
		this.thisUnit = thisUnit;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public Double getThatValue() {
		return thatValue;
	}

	public void setThatValue(Double thatValue) {
		this.thatValue = thatValue;
	}

	public String getThatUnit() {
		return thatUnit;
	}

	public void setThatUnit(String thatUnit) {
		this.thatUnit = thatUnit;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity e) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setThisValue(e.getThisValue());
        dto.setThisUnit(e.getThisUnit());
        dto.setThisMeasurementType(e.getThisMeasurementType());
        dto.setThatValue(e.getThatValue());
        dto.setThatUnit(e.getThatUnit());
        dto.setThatMeasurementType(e.getThatMeasurementType());
        dto.setOperation(e.getOperation());
        dto.setResultString(e.getResultString());
        dto.setResultValue(e.getResultValue());
        dto.setResultUnit(e.getResultUnit());
        dto.setResultMeasurementType(e.getResultMeasurementType());
        dto.setErrorMessage(e.getErrorMessage());
        dto.setError(e.isError());
        return dto;
    }

    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity e = new QuantityMeasurementEntity();
        e.setThisValue(this.thisValue);
        e.setThisUnit(this.thisUnit);
        e.setThisMeasurementType(this.thisMeasurementType);
        e.setThatValue(this.thatValue);
        e.setThatUnit(this.thatUnit);
        e.setThatMeasurementType(this.thatMeasurementType);
        e.setOperation(this.operation);
        e.setResultString(this.resultString);
        e.setResultValue(this.resultValue);
        e.setResultUnit(this.resultUnit);
        e.setResultMeasurementType(this.resultMeasurementType);
        e.setErrorMessage(this.errorMessage);
        e.setError(this.error);
        return e;
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
    }
}
