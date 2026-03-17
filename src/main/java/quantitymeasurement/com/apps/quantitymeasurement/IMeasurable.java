package quantitymeasurement.com.apps.quantitymeasurement;

/**
 * Interface defining the contract for all measurement units.
 * Includes optional operation support via default methods,
 * allowing categories like Temperature to opt out of arithmetic.
 */
public interface IMeasurable {

    // ==================== Mandatory Conversion Methods ====================

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();


    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    // Default Methods

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Default: all operations supported — subclasses override to restrict
    }
}