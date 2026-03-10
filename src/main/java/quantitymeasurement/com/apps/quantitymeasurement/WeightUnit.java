package quantitymeasurement.com.apps.quantitymeasurement;

public enum WeightUnit {
	 MILLIGRAM(.001),      // 1 foot = 12 inches
	    GRAM(1.0),
	    KILOGRAM(1000.0),
	    POUND(453.592),
	    TONNE(1000000.0);

	    private final double conversionFactor;

	    WeightUnit(double conversionFactor) {
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
