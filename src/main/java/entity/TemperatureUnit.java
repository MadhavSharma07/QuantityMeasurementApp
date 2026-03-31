package entity;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(  (c) -> c,                        (c) -> c),
    FAHRENHEIT((f) -> (f - 32.0) * 5.0 / 9.0, (c) -> c * 9.0 / 5.0 + 32.0);

    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;
    private final SupportsArithmetic arithmeticSupport = () -> false;

    TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
        this.toCelsius   = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    @Override public double getConversionFactor()            { return 1.0; }
    @Override public double convertToBaseUnit(double v)      { return toCelsius.apply(v); }
    @Override public double convertFromBaseUnit(double b)    { return fromCelsius.apply(b); }
    @Override public String getUnitName()                    { return this.name(); }
    @Override public String getMeasurementType()             { return "TEMPERATURE"; }
    @Override public boolean supportsArithmetic()            { return arithmeticSupport.isSupported(); }

    @Override
    public IMeasurable fromUnitName(String unitName) {
        for (TemperatureUnit u : values())
            if (u.name().equalsIgnoreCase(unitName)) return u;
        return null;
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
            "Temperature does not support " + operation.toLowerCase() +
            " operations. Only equality and conversion are supported.");
    }
}