package com.app.quantitymeasurementapp.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    @NotNull(message = "Value must not be null")
    private Double value;

    @NotEmpty(message = "Unit must not be empty")
    private String unit;

    @NotEmpty(message = "Measurement type must not be empty")
    private String measurementType;

    // Valid units per measurement type
    private static final Map<String, java.util.List<String>> VALID_UNITS = Map.of(
        "LengthUnit",      Arrays.asList("FEET", "INCHES", "YARDS", "CENTIMETERS"),
        "WeightUnit",      Arrays.asList("MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE"),
        "VolumeUnit",      Arrays.asList("LITRE", "MILLILITRE", "GALLON"),
        "TemperatureUnit", Arrays.asList("CELSIUS", "FAHRENHEIT", "KELVIN")
    );

    public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	@AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        if (unit == null || measurementType == null) return true;
        java.util.List<String> validUnits = VALID_UNITS.get(measurementType);
        return validUnits != null && validUnits.contains(unit.toUpperCase());
    }
}
