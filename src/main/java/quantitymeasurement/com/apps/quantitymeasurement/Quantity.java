package quantitymeasurement.com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;
    private static final double EPSILON = 0.0001d;

    // ArithmeticOperation Enum

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (Math.abs(b) < 0.0001d)
                throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    //Constructor

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

    // Instance conversion

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = this.toBaseUnit();
        double convertedValue = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(convertedValue, targetUnit);
    }

    //Private Helpers (UC13 DRY refactor)

    private void validateArithmeticOperands(Quantity<?> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");
        if (this.unit.getClass() != other.getUnit().getClass())
            throw new IllegalArgumentException("Cannot perform arithmetic on quantities of different categories");
        if (!Double.isFinite(this.value))
            throw new IllegalArgumentException("This quantity's value must be finite");
        if (!Double.isFinite(other.getValue()))
            throw new IllegalArgumentException("Other quantity's value must be finite");
        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }
    private double performBaseArithmetic(Quantity<?> other, ArithmeticOperation operation) {
        double thisBase  = this.toBaseUnit();
        double otherBase = other.getUnit().convertToBaseUnit(other.getValue());
        return operation.compute(thisBase, otherBase);
    }

    // Addition

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(this.unit.convertFromBaseUnit(baseResult), this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(targetUnit.convertFromBaseUnit(baseResult), targetUnit);
    }

    //Subtraction

    public Quantity<U> subtract(Quantity<?> other) {
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(this.unit.convertFromBaseUnit(baseResult), this.unit);
    }

    public Quantity<U> subtract(Quantity<?> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(targetUnit.convertFromBaseUnit(baseResult), targetUnit);
    }

    // Division 
    public double divide(Quantity<?> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }
}