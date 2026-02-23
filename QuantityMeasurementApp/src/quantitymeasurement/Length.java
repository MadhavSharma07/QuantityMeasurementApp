package quantitymeasurement;


import java.util.Objects;

public class Length {

	private final double value;
    private final LengthUnit unit;
    private final static double Epsilon=0.0001d;

    public enum LengthUnit {

        FEET(12.0),      // 1 foot = 12 inches
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETER(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }

		
    }
    
    public double getValue() {
    	return value;
    }
    
    public LengthUnit getUnit() {
    	return unit;
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

//        return Double.compare(Math.floor(this.toBaseUnit()), Math.floor(other.toBaseUnit())) == 0;
        return Math.abs(this.toBaseUnit()- other.toBaseUnit())<Epsilon;
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
    
    public static double convert(double value , Length.LengthUnit sourceUnit,Length.LengthUnit targetUnit) {
    	if(!Double.isFinite(value)) { throw new IllegalArgumentException("Invalid value");}
    	if(sourceUnit ==null || targetUnit==null) {
    		throw new IllegalArgumentException("Invalid unit");
    	}
    	
    	double base = value*sourceUnit.getConversionFactor();
    	double convertedValue = base/targetUnit.getConversionFactor();
    	
    	return convertedValue;
    } 
    
    public Length convertTo(Length.LengthUnit targetUnit) {
    	if(!Double.isFinite(value)) { throw new IllegalArgumentException("Invalid value");}
    	if(this.unit ==null || targetUnit==null) {
    		throw new IllegalArgumentException("Invalid unit");
    	}
    	
    	double base = value*this.unit.getConversionFactor();
    	double convertedValue = base/targetUnit.getConversionFactor();
    	Length convertedLength = new Length(convertedValue,targetUnit);
    	
    	return convertedLength;
    }
   
}

