package quantitymeasurement;


import java.util.Objects;

public class Length {

	private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {

        FEET(12.0),      // 1 foot = 12 inches
        INCHES(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }

		
    }

    public Length(double value, LengthUnit unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    private double toBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }

    public boolean compare(Length other) {
        if (other == null)
            return false;

        return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
    }

 
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Length other = (Length) obj;

        return compare(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }
}

