package quantitymeasurement.com.apps.quantitymeasurement;

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
    
    public double convertToBaseUnit(double value) {
    	return value*conversionFactor;
    }
	
    public double convertFromBaseUnit(double baseValue) {
    		return baseValue/conversionFactor;
    }
}