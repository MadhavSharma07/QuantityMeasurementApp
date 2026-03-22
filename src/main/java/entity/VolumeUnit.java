package entity;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

    private final double conversionFactor;

    VolumeUnit(double cf) { this.conversionFactor = cf; }

    @Override public double getConversionFactor()            { return conversionFactor; }
    @Override public double convertToBaseUnit(double v)      { return v * conversionFactor; }
    @Override public double convertFromBaseUnit(double b)    { return b / conversionFactor; }
    @Override public String getUnitName()                    { return this.name(); }
    @Override public String getMeasurementType()             { return "VOLUME"; }

    @Override
    public IMeasurable fromUnitName(String unitName) {
        for (VolumeUnit u : values())
            if (u.name().equalsIgnoreCase(unitName)) return u;
        return null;
    }
}