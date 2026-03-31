package entity;

public class QuantityDTO {

    private double value;
    private String unitName;        // e.g. "FEET", "KILOGRAM"
    private String measurementType; // e.g. "LENGTH", "WEIGHT"

    public QuantityDTO() {}

    public QuantityDTO(double value, String unitName, String measurementType) {
        this.value= value;
        this.unitName= unitName;
        this.measurementType = measurementType;
    }

    public double getValue()            { return value; }
    public void   setValue(double v)    { this.value = v; }

    public String getUnitName()                   { return unitName; }
    public void   setUnitName(String u)           { this.unitName = u; }

    public String getMeasurementType()            { return measurementType; }
    public void   setMeasurementType(String t)    { this.measurementType = t; }

    @Override
    public String toString() {
        return value + " " + unitName + " (" + measurementType + ")";
    }
}