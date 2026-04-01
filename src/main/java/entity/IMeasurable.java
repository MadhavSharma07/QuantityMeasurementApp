<<<<<<<< HEAD:src/main/java/com/app/quantitymeasurementapp/unit/IMeasurable.java
package com.app.quantitymeasurementapp.unit;

public interface IMeasurable {

========
package entity;

public interface IMeasurable {

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    String getMeasurementType();
    IMeasurable fromUnitName(String unitName);

>>>>>>>> main:src/main/java/entity/IMeasurable.java
    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

<<<<<<<< HEAD:src/main/java/com/app/quantitymeasurementapp/unit/IMeasurable.java
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    String getMeasurementType();

========
>>>>>>>> main:src/main/java/entity/IMeasurable.java
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

<<<<<<<< HEAD:src/main/java/com/app/quantitymeasurementapp/unit/IMeasurable.java
    default boolean supportsAddition() {
        return supportsArithmetic();
========
    default void validateOperationSupport(String operation) {
        // default: all operations allowed
>>>>>>>> main:src/main/java/entity/IMeasurable.java
    }

    default boolean supportsDivision() {
        return supportsArithmetic();
    }

    default void validateOperationSupport(String operation) {
        // default: all operations allowed
    }
}
