package quantitymeasurement.com.apps.quantitymeasurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;
    private static final double EPSILON = 0.0001d;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public boolean compare(Quantity<U> other) {
        if (other == null)
            return false;
        if (this.unit.getClass() != other.unit.getClass())
            return false;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        // Prevent cross-category comparison
        if (this.unit.getClass() != other.unit.getClass())
            return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    // Static conversion utility
    public static <U extends IMeasurable> double convert(double value, U sourceUnit, U targetUnit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");
        if (sourceUnit == null || targetUnit == null)
            throw new IllegalArgumentException("Invalid unit");

        double base = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = this.toBaseUnit();
        double convertedValue = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(convertedValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        double baseSum = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = this.unit.convertFromBaseUnit(baseSum);
        return new Quantity<>(resultValue, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseSum = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(baseSum);
        return new Quantity<>(resultValue, targetUnit);
    }
}