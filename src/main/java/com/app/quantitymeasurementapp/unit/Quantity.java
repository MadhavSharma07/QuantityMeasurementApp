package com.app.quantitymeasurementapp.unit;

import java.util.Objects;

public class Quantity<U extends Enum<U> & IMeasurable> {

    private static final double EPSILON = 0.0001d;

    private final double value;
    private final U      unit;

    // ==================== Constructor ====================

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit  = unit;
    }

    // ==================== Accessors ====================

    public double getValue() { return value; }
    public U      getUnit()  { return unit;  }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // ==================== equals / hashCode / toString ====================

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity)) return false;
        @SuppressWarnings("unchecked")
        Quantity<U> other = (Quantity<U>) obj;
        if (!this.unit.getMeasurementType().equals(other.unit.getMeasurementType())) return false;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    @Override
    public String toString() {
        return String.format("Quantity{value=%.4f, unit=%s}", value, unit.name());
    }

    // ==================== Conversion ====================

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double base      = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(converted, targetUnit);
    }

    public static <U extends Enum<U> & IMeasurable> double convert(double value, U from, U to) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        if (from == null || to == null) throw new IllegalArgumentException("Invalid unit");
        return to.convertFromBaseUnit(from.convertToBaseUnit(value));
    }

    // ==================== Validation helpers ====================

    private void validateArithmeticOperands(Quantity<?> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");
        if (!this.unit.getMeasurementType().equals(other.getUnit().getMeasurementType()))
            throw new IllegalArgumentException("Cannot perform arithmetic on quantities of different categories");
        if (!Double.isFinite(this.value))
            throw new IllegalArgumentException("This quantity's value must be finite");
        if (!Double.isFinite(other.getValue()))
            throw new IllegalArgumentException("Other quantity's value must be finite");
        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }

    private double performBaseArithmetic(Quantity<?> other, ArithmeticOperation operation) {
        this.unit.validateOperationSupport(operation.name());
        double thisBase  = this.toBaseUnit();
        double otherBase = other.getUnit().convertToBaseUnit(other.getValue());
        return operation.compute(thisBase, otherBase);
    }

    // ==================== Addition ====================

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(unit.convertFromBaseUnit(baseResult), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(targetUnit.convertFromBaseUnit(baseResult), targetUnit);
    }

    // ==================== Subtraction ====================

    public Quantity<U> subtract(Quantity<?> other) {
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(unit.convertFromBaseUnit(baseResult), unit);
    }

    public Quantity<U> subtract(Quantity<?> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(targetUnit.convertFromBaseUnit(baseResult), targetUnit);
    }

    // ==================== Division ====================

    public double divide(Quantity<?> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }
}
