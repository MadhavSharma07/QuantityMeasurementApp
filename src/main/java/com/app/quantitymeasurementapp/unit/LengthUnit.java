package com.app.quantitymeasurementapp.unit;

public enum LengthUnit implements IMeasurable {

    FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

    private final double conversionFactor;

    LengthUnit(double cf) { this.conversionFactor = cf; }

    @Override public double getConversionFactor()          { return conversionFactor; }
    @Override public double convertToBaseUnit(double v)    { return v * conversionFactor; }
    @Override public double convertFromBaseUnit(double b)  { return b / conversionFactor; }
    @Override public String getUnitName()                  { return name(); }
    @Override public String getMeasurementType()           { return "LENGTH"; }
}
