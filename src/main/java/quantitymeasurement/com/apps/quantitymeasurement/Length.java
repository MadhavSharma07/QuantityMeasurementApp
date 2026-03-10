package quantitymeasurement.com.apps.quantitymeasurement;


import java.util.Objects;

public class Length {

	private final double value;
    private final LengthUnit unit;
    private final static double Epsilon=0.0001d;

   
    
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
        return unit.convertToBaseUnit(value);
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
    
    public static double convert(double value ,LengthUnit sourceUnit,LengthUnit targetUnit) {
    	if(!Double.isFinite(value)) { throw new IllegalArgumentException("Invalid value");}
    	if(sourceUnit ==null || targetUnit==null) {
    		throw new IllegalArgumentException("Invalid unit");
    	}
    	
    	double base = sourceUnit.convertToBaseUnit(value);
    	double convertedValue = targetUnit.convertFromBaseUnit(base);
    	
    	return convertedValue;
    } 
    
    public Length convertTo(LengthUnit targetUnit) {
    	if(!Double.isFinite(value)) { throw new IllegalArgumentException("Invalid value");}
    	if(this.unit ==null || targetUnit==null) {
    		throw new IllegalArgumentException("Invalid unit");
    	}
    	
    	double base = this.toBaseUnit();
    	double convertedValue = targetUnit.convertFromBaseUnit(base);
    	Length convertedLength = new Length(convertedValue,targetUnit);
    	
    	return convertedLength;
    }
    
    
    public Length add(Length other) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        double baseSum = this.toBaseUnit() + other.toBaseUnit();

        double resultValue =this.unit.convertFromBaseUnit(baseSum);

        return new Length(resultValue, this.unit);
    }
   
    public Length add(Length other, LengthUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseSum = this.toBaseUnit() + other.toBaseUnit();

        double resultValue =targetUnit.convertFromBaseUnit(baseSum);

        return new Length(resultValue, targetUnit);
    }
}

