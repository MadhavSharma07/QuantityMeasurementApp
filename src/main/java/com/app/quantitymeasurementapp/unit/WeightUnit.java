package com.app.quantitymeasurementapp.unit;

public enum WeightUnit implements IMeasurable {

    MILLIGRAM(0.000001), GRAM(0.001), KILOGRAM(1.0), POUND(0.453592), TONNE(1000.0);

    private final double conversionFactor;

    WeightUnit(double cf) { this.conversionFactor = cf; }

    @Override public double getConversionFactor()          { return conversionFactor; }
    @Override public double convertToBaseUnit(double v)    { return v * conversionFactor; }
    @Override public double convertFromBaseUnit(double b)  { return Math.round(b / conversionFactor * 100.0) / 100.0; }
    @Override public String getUnitName()                  { return name(); }
    @Override public String getMeasurementType()           { return "WEIGHT"; }
}
