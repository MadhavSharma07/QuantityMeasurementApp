package com.app.quantitymeasurementapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quantity_measurement",
       indexes = {
           @Index(name = "idx_operation",     columnList = "operation"),
           @Index(name = "idx_meas_type",     columnList = "thisMeasurementType"),
           @Index(name = "idx_created_at",    columnList = "createdAt")
       })
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String resultString;
    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String errorMessage;
    private boolean error;

    public void setId(Long id) {
		this.id = id;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setThisValue(Double thisValue) {
		this.thisValue = thisValue;
	}

	public void setThisUnit(String thisUnit) {
		this.thisUnit = thisUnit;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public void setThatValue(Double thatValue) {
		this.thatValue = thatValue;
	}

	public void setThatUnit(String thatUnit) {
		this.thatUnit = thatUnit;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getOperation() {
		return operation;
	}

	public Double getThisValue() {
		return thisValue;
	}

	public String getThisUnit() {
		return thisUnit;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public Double getThatValue() {
		return thatValue;
	}

	public String getThatUnit() {
		return thatUnit;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public String getResultString() {
		return resultString;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isError() {
		return error;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
