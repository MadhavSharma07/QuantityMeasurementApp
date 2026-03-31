package entity;

public enum WeightUnit implements IMeasurable {

    MILLIGRAM(0.001), GRAM(1.0), KILOGRAM(1000.0), POUND(453.592), TONNE(1000000.0);

    private final double conversionFactor;

    WeightUnit(double cf) { this.conversionFactor = cf; }

    @Override public double getConversionFactor()            { return conversionFactor; }
    @Override public double convertToBaseUnit(double v)      { return v * conversionFactor; }
    @Override public double convertFromBaseUnit(double b)    { return b / conversionFactor; }
    @Override public String getUnitName()                    { return this.name(); }
    @Override public String getMeasurementType()             { return "WEIGHT"; }

    @Override
    public IMeasurable fromUnitName(String unitName) {
        for (WeightUnit u : values())
            if (u.name().equalsIgnoreCase(unitName)) return u;
        return null;
    }
}