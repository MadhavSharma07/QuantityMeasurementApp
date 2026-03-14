package quantitymeasurement.com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // -------- Generic demonstration methods --------

    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        return q1.equals(q2);
    }

    public static <U extends IMeasurable> boolean demonstrateComparison(double value1, U unit1, double value2, U unit2) {
        Quantity<U> q1 = new Quantity<>(value1, unit1);
        Quantity<U> q2 = new Quantity<>(value2, unit2);
        return q1.equals(q2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(double value, U sourceUnit, U targetUnit) {
        double resultantValue = Quantity.convert(value, sourceUnit, targetUnit);
        return new Quantity<>(resultantValue, targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {
        return q1.add(q2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        return q1.add(q2, targetUnit);
    }

    public static void main(String[] args) {

        try {
            // ==================== LENGTH ====================

            System.out.println("//////COMPARISON OF LENGTHS///////////");
            boolean result1 = demonstrateComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
            boolean result2 = demonstrateComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);
            boolean result3 = demonstrateComparison(100.0, LengthUnit.CENTIMETER, 39.3701, LengthUnit.INCHES);
            boolean result4 = demonstrateComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);
            boolean result5 = demonstrateComparison(30.48, LengthUnit.CENTIMETER, 1.0, LengthUnit.FEET);

            System.out.println("Equal -> " + result1);
            System.out.println("Equal -> " + result2);
            System.out.println("Equal -> " + result3);
            System.out.println("Equal -> " + result4);
            System.out.println("Equal -> " + result5);

            System.out.println("///////CONVERSION OF LENGTHS///////////");
            Quantity<LengthUnit> l1 = demonstrateConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
            System.out.println("Output: " + l1.getValue());

            Quantity<LengthUnit> l2 = new Quantity<>(3.0, LengthUnit.YARDS);
            Quantity<LengthUnit> l3 = demonstrateConversion(l2, LengthUnit.FEET);
            System.out.println("Output: " + l3.getValue());

            Quantity<LengthUnit> l4 = demonstrateConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
            System.out.println("Output: " + l4.getValue());

            Quantity<LengthUnit> l5 = demonstrateConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCHES);
            System.out.println("Output: " + l5.getValue());

            Quantity<LengthUnit> l6 = demonstrateConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);
            System.out.println("Output: " + l6.getValue());

            System.out.println("////////FIRST OPERAND LENGTH UNIT ADDITION//////////");
            Quantity<LengthUnit> a1 = new Quantity<>(1.0, LengthUnit.FEET);
            Quantity<LengthUnit> a2 = new Quantity<>(12.0, LengthUnit.INCHES);

            Quantity<LengthUnit> result6 = demonstrateAddition(a1, a2);
            System.out.println("1 ft + 12 in = " + result6.getValue() + " " + result6.getUnit());

            Quantity<LengthUnit> result7 = demonstrateAddition(
                    new Quantity<>(1.0, LengthUnit.YARDS),
                    new Quantity<>(3.0, LengthUnit.FEET));
            System.out.println("1 yd + 3 ft = " + result7.getValue() + " " + result7.getUnit());

            Quantity<LengthUnit> result8 = demonstrateAddition(
                    new Quantity<>(12.0, LengthUnit.INCHES),
                    new Quantity<>(1.0, LengthUnit.FEET));
            System.out.println("12 in + 1 ft = " + result8.getValue() + " " + result8.getUnit());

            Quantity<LengthUnit> result9 = demonstrateAddition(
                    new Quantity<>(2.54, LengthUnit.CENTIMETER),
                    new Quantity<>(1.0, LengthUnit.INCHES));
            System.out.println("2.54 cm + 1 in = " + result9.getValue() + " " + result9.getUnit());

            Quantity<LengthUnit> result10 = demonstrateAddition(
                    new Quantity<>(5.0, LengthUnit.FEET),
                    new Quantity<>(-2.0, LengthUnit.FEET));
            System.out.println("5 ft + (-2 ft) = " + result10.getValue() + " " + result10.getUnit());

            System.out.println("////////////////CROSS LENGTH UNIT ADDTION////////////////// ");

            Quantity<LengthUnit> result11 = demonstrateAddition(
                    new Quantity<>(1.0, LengthUnit.FEET),
                    new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
            System.out.println("1 ft + 12 in = " + result11.getValue() + " " + result10.getUnit());

            Quantity<LengthUnit> result12 = demonstrateAddition(
                    new Quantity<>(1.0, LengthUnit.FEET),
                    new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
            System.out.println("1 ft + 12 in = " + result12.getValue() + " " + result12.getUnit());

            Quantity<LengthUnit> result13 = demonstrateAddition(
                    new Quantity<>(5.0, LengthUnit.FEET),
                    new Quantity<>(-2.0, LengthUnit.FEET), LengthUnit.INCHES);
            System.out.println("5 ft + (-2 ft)  = " + result13.getValue() + " " + result13.getUnit());

            // ==================== WEIGHT ====================

            System.out.println("//////COMPARISON OF WEIGHTS///////////");

            boolean wResult1 = demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
            boolean wResult2 = demonstrateComparison(2.0, WeightUnit.KILOGRAM, 2000.0, WeightUnit.GRAM);
            boolean wResult3 = demonstrateComparison(1.0, WeightUnit.TONNE, 1000.0, WeightUnit.KILOGRAM);

            System.out.println("Equal -> " + wResult1);
            System.out.println("Equal -> " + wResult2);
            System.out.println("Equal -> " + wResult3);

            System.out.println("///////CONVERSION OF WEIGHTS/////////");
            Quantity<WeightUnit> w1 = demonstrateConversion(1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
            System.out.println("Output: " + w1.getValue());

            Quantity<WeightUnit> w2 = new Quantity<>(2000.0, WeightUnit.GRAM);
            Quantity<WeightUnit> w3 = demonstrateConversion(w2, WeightUnit.KILOGRAM);
            System.out.println("Output: " + w3.getValue());

            Quantity<WeightUnit> w4 = demonstrateConversion(1.0, WeightUnit.TONNE, WeightUnit.KILOGRAM);
            System.out.println("Output: " + w4.getValue());

            Quantity<WeightUnit> w5 = demonstrateConversion(500.0, WeightUnit.GRAM, WeightUnit.KILOGRAM);
            System.out.println("Output: " + w5.getValue());

            System.out.println("////////FIRST OPERAND WEIGHT UNIT ADDITION//////////");

            Quantity<WeightUnit> wa1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
            Quantity<WeightUnit> wa2 = new Quantity<>(500.0, WeightUnit.GRAM);

            Quantity<WeightUnit> wResult4 = demonstrateAddition(wa1, wa2);
            System.out.println("1 kg + 500 g = " + wResult4.getValue() + " " + wResult4.getUnit());

            Quantity<WeightUnit> wResult5 = demonstrateAddition(
                    new Quantity<>(1.0, WeightUnit.TONNE),
                    new Quantity<>(500.0, WeightUnit.KILOGRAM));
            System.out.println("1 tonne + 500 kg = " + wResult5.getValue() + " " + wResult5.getUnit());

            Quantity<WeightUnit> wResult6 = demonstrateAddition(
                    new Quantity<>(1000.0, WeightUnit.GRAM),
                    new Quantity<>(1.0, WeightUnit.KILOGRAM));
            System.out.println("1000 g + 1 kg = " + wResult6.getValue() + " " + wResult6.getUnit());

            Quantity<WeightUnit> wResult7 = demonstrateAddition(
                    new Quantity<>(5.0, WeightUnit.KILOGRAM),
                    new Quantity<>(-2.0, WeightUnit.KILOGRAM));
            System.out.println("5 kg + (-2 kg) = " + wResult7.getValue() + " " + wResult7.getUnit());

            System.out.println("////////////////CROSS WEIGHT UNIT ADDITION//////////////////");

            Quantity<WeightUnit> wResult8 = demonstrateAddition(
                    new Quantity<>(1.0, WeightUnit.KILOGRAM),
                    new Quantity<>(500.0, WeightUnit.GRAM),
                    WeightUnit.GRAM);
            System.out.println("1 kg + 500 g = " + wResult8.getValue() + " " + wResult8.getUnit());

            Quantity<WeightUnit> wResult9 = demonstrateAddition(
                    new Quantity<>(1.0, WeightUnit.KILOGRAM),
                    new Quantity<>(500.0, WeightUnit.GRAM),
                    WeightUnit.KILOGRAM);
            System.out.println("1 kg + 500 g = " + wResult9.getValue() + " " + wResult9.getUnit());

            Quantity<WeightUnit> wResult10 = demonstrateAddition(
                    new Quantity<>(5.0, WeightUnit.KILOGRAM),
                    new Quantity<>(-2.0, WeightUnit.KILOGRAM),
                    WeightUnit.GRAM);
            System.out.println("5 kg + (-2 kg) = " + wResult10.getValue() + " " + wResult10.getUnit());

            // ==================== VOLUME ====================

            System.out.println("//////COMPARISON OF VOLUMES///////////");

            boolean vResult1 = demonstrateComparison(1.0, VolumeUnit.LITRE, 1000.0, VolumeUnit.MILLILITRE);
            boolean vResult2 = demonstrateComparison(1.0, VolumeUnit.GALLON, 3.78541, VolumeUnit.LITRE);
            boolean vResult3 = demonstrateComparison(500.0, VolumeUnit.MILLILITRE, 0.5, VolumeUnit.LITRE);
            boolean vResult4 = demonstrateComparison(1.0, VolumeUnit.LITRE, 0.264172, VolumeUnit.GALLON);
            boolean vResult5 = demonstrateComparison(1000.0, VolumeUnit.MILLILITRE, 1.0, VolumeUnit.LITRE);

            System.out.println("Equal -> " + vResult1);
            System.out.println("Equal -> " + vResult2);
            System.out.println("Equal -> " + vResult3);
            System.out.println("Equal -> " + vResult4);
            System.out.println("Equal -> " + vResult5);

            System.out.println("///////CONVERSION OF VOLUMES///////////");
            Quantity<VolumeUnit> v1 = demonstrateConversion(1.0, VolumeUnit.LITRE, VolumeUnit.MILLILITRE);
            System.out.println("Output: " + v1.getValue());

            Quantity<VolumeUnit> v2 = new Quantity<>(2.0, VolumeUnit.GALLON);
            Quantity<VolumeUnit> v3 = demonstrateConversion(v2, VolumeUnit.LITRE);
            System.out.println("Output: " + v3.getValue());

            Quantity<VolumeUnit> v4 = demonstrateConversion(500.0, VolumeUnit.MILLILITRE, VolumeUnit.GALLON);
            System.out.println("Output: " + v4.getValue());

            Quantity<VolumeUnit> v5 = demonstrateConversion(3.78541, VolumeUnit.LITRE, VolumeUnit.GALLON);
            System.out.println("Output: " + v5.getValue());

            Quantity<VolumeUnit> v6 = demonstrateConversion(0.0, VolumeUnit.LITRE, VolumeUnit.MILLILITRE);
            System.out.println("Output: " + v6.getValue());

            System.out.println("////////FIRST OPERAND VOLUME UNIT ADDITION//////////");

            Quantity<VolumeUnit> va1 = new Quantity<>(1.0, VolumeUnit.LITRE);
            Quantity<VolumeUnit> va2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

            Quantity<VolumeUnit> vResult6 = demonstrateAddition(va1, va2);
            System.out.println("1 L + 1000 mL = " + vResult6.getValue() + " " + vResult6.getUnit());

            Quantity<VolumeUnit> vResult7 = demonstrateAddition(
                    new Quantity<>(500.0, VolumeUnit.MILLILITRE),
                    new Quantity<>(0.5, VolumeUnit.LITRE));
            System.out.println("500 mL + 0.5 L = " + vResult7.getValue() + " " + vResult7.getUnit());

            Quantity<VolumeUnit> vResult8 = demonstrateAddition(
                    new Quantity<>(2.0, VolumeUnit.GALLON),
                    new Quantity<>(3.78541, VolumeUnit.LITRE));
            System.out.println("2 gal + 3.78541 L = " + vResult8.getValue() + " " + vResult8.getUnit());

            Quantity<VolumeUnit> vResult9 = demonstrateAddition(
                    new Quantity<>(5.0, VolumeUnit.LITRE),
                    new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
            System.out.println("5 L + (-2000 mL) = " + vResult9.getValue() + " " + vResult9.getUnit());

            System.out.println("////////////////CROSS VOLUME UNIT ADDITION//////////////////");

            Quantity<VolumeUnit> vResult10 = demonstrateAddition(
                    new Quantity<>(1.0, VolumeUnit.LITRE),
                    new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                    VolumeUnit.MILLILITRE);
            System.out.println("1 L + 1000 mL = " + vResult10.getValue() + " " + vResult10.getUnit());

            Quantity<VolumeUnit> vResult11 = demonstrateAddition(
                    new Quantity<>(1.0, VolumeUnit.GALLON),
                    new Quantity<>(3.78541, VolumeUnit.LITRE),
                    VolumeUnit.GALLON);
            System.out.println("1 gal + 3.78541 L = " + vResult11.getValue() + " " + vResult11.getUnit());

            Quantity<VolumeUnit> vResult12 = demonstrateAddition(
                    new Quantity<>(500.0, VolumeUnit.MILLILITRE),
                    new Quantity<>(1.0, VolumeUnit.LITRE),
                    VolumeUnit.GALLON);
            System.out.println("500 mL + 1 L = " + vResult12.getValue() + " " + vResult12.getUnit());

            Quantity<VolumeUnit> vResult13 = demonstrateAddition(
                    new Quantity<>(2.0, VolumeUnit.LITRE),
                    new Quantity<>(4.0, VolumeUnit.GALLON),
                    VolumeUnit.LITRE);
            System.out.println("2 L + 4 gal = " + vResult13.getValue() + " " + vResult13.getUnit());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }
    }
}