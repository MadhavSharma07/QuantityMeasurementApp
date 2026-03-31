package entity;

public interface IMeasurable {

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    String getMeasurementType();
    IMeasurable fromUnitName(String unitName);

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // default: all operations allowed
    }
}