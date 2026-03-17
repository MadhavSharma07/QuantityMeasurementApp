package quantitymeasurement.com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            // toCelsius: identity function — already in Celsius
            (celsius) -> celsius,
            // fromCelsius: identity function
            (celsius) -> celsius
    ),

    FAHRENHEIT(
            // toCelsius: °C = (°F - 32) × 5/9
            (fahrenheit) -> (fahrenheit - 32.0) * 5.0 / 9.0,
            // fromCelsius: °F = °C × 9/5 + 32
            (celsius) -> celsius * 9.0 / 5.0 + 32.0
    );

    // ==================== Lambda Conversion Functions ====================

    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;

    // Lambda: TemperatureUnit does NOT support arithmetic
    private final SupportsArithmetic arithmeticSupport = () -> false;

    // ==================== Constructor ====================

    TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
        this.toCelsius   = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    // ==================== IMeasurable Mandatory Methods ====================

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

 
    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }


    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromCelsius.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    // ==================== IMeasurable Optional Override Methods ====================

 
    @Override
    public boolean supportsArithmetic() {
        return arithmeticSupport.isSupported();
    }

 
    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation.toLowerCase() +" operations.");
    }
}
