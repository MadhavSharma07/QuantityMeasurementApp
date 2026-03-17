package quantitymeasurement.com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // ==================== Generic demonstration methods ====================

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

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
        return q1.subtract(q2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        return q1.subtract(q2, targetUnit);
    }

    public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
        return q1.divide(q2);
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

            System.out.println("////////////////CROSS LENGTH UNIT ADDTION//////////////////");
            Quantity<LengthUnit> result11 = demonstrateAddition(
                    new Quantity<>(1.0, LengthUnit.FEET),
                    new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
            System.out.println("1 ft + 12 in = " + result11.getValue() + " " + result11.getUnit());

            System.out.println("////////SUBTRACTION OF LENGTHS//////////");
            Quantity<LengthUnit> subResult1 = demonstrateSubtraction(
                    new Quantity<>(10.0, LengthUnit.FEET),
                    new Quantity<>(6.0, LengthUnit.INCHES));
            System.out.println("10 ft - 6 in = " + subResult1.getValue() + " " + subResult1.getUnit());
            Quantity<LengthUnit> subResult2 = demonstrateSubtraction(
                    new Quantity<>(10.0, LengthUnit.FEET),
                    new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
            System.out.println("10 ft - 6 in (in INCHES) = " + subResult2.getValue() + " " + subResult2.getUnit());

            System.out.println("////////DIVISION OF LENGTHS//////////");
            double divResult1 = demonstrateDivision(
                    new Quantity<>(10.0, LengthUnit.FEET),
                    new Quantity<>(2.0, LengthUnit.FEET));
            System.out.println("10 ft / 2 ft = " + divResult1);

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

            // ==================== VOLUME ====================

            System.out.println("//////COMPARISON OF VOLUMES///////////");
            boolean vResult1 = demonstrateComparison(1.0, VolumeUnit.LITRE, 1000.0, VolumeUnit.MILLILITRE);
            boolean vResult2 = demonstrateComparison(1.0, VolumeUnit.GALLON, 3.78541, VolumeUnit.LITRE);
            System.out.println("Equal -> " + vResult1);
            System.out.println("Equal -> " + vResult2);

            System.out.println("///////CONVERSION OF VOLUMES///////////");
            Quantity<VolumeUnit> v1 = demonstrateConversion(1.0, VolumeUnit.LITRE, VolumeUnit.MILLILITRE);
            System.out.println("Output: " + v1.getValue());

            // ==================== TEMPERATURE ====================

            System.out.println("//////COMPARISON OF TEMPERATURES///////////");
            boolean tResult1 = demonstrateComparison(0.0, TemperatureUnit.CELSIUS, 32.0, TemperatureUnit.FAHRENHEIT);
            boolean tResult2 = demonstrateComparison(100.0, TemperatureUnit.CELSIUS, 212.0, TemperatureUnit.FAHRENHEIT);
            boolean tResult3 = demonstrateComparison(-40.0, TemperatureUnit.CELSIUS, -40.0, TemperatureUnit.FAHRENHEIT);
            boolean tResult4 = demonstrateComparison(50.0, TemperatureUnit.CELSIUS, 122.0, TemperatureUnit.FAHRENHEIT);
            boolean tResult5 = demonstrateComparison(25.0, TemperatureUnit.CELSIUS, 25.0, TemperatureUnit.CELSIUS);

            System.out.println("0°C == 32°F: "   + tResult1);
            System.out.println("100°C == 212°F: " + tResult2);
            System.out.println("-40°C == -40°F: " + tResult3);
            System.out.println("50°C == 122°F: "  + tResult4);
            System.out.println("25°C == 25°C: "   + tResult5);

            System.out.println("///////CONVERSION OF TEMPERATURES///////////");
            Quantity<TemperatureUnit> t1 = demonstrateConversion(100.0, TemperatureUnit.CELSIUS, TemperatureUnit.FAHRENHEIT);
            System.out.println("100°C -> °F: " + t1.getValue());

            Quantity<TemperatureUnit> t2 = demonstrateConversion(32.0, TemperatureUnit.FAHRENHEIT, TemperatureUnit.CELSIUS);
            System.out.println("32°F -> °C: " + t2.getValue());

            Quantity<TemperatureUnit> t3 = demonstrateConversion(0.0, TemperatureUnit.CELSIUS, TemperatureUnit.FAHRENHEIT);
            System.out.println("0°C -> °F: " + t3.getValue());

            Quantity<TemperatureUnit> t4 = demonstrateConversion(-40.0, TemperatureUnit.CELSIUS, TemperatureUnit.FAHRENHEIT);
            System.out.println("-40°C -> °F: " + t4.getValue());

            Quantity<TemperatureUnit> t5 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
            Quantity<TemperatureUnit> t6 = demonstrateConversion(t5, TemperatureUnit.FAHRENHEIT);
            System.out.println("50°C -> °F (via object): " + t6.getValue());


            System.out.println("////////CROSS-CATEGORY TEMPERATURE PREVENTION//////////");
            Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<LengthUnit>      len  = new Quantity<>(100.0, LengthUnit.FEET);
            Quantity<WeightUnit>      wgt  = new Quantity<>(100.0, WeightUnit.KILOGRAM);
            Quantity<VolumeUnit>      vol  = new Quantity<>(100.0, VolumeUnit.LITRE);
            System.out.println("100°C == 100 ft: "  + temp.equals(len));
            System.out.println("100°C == 100 kg: "  + temp.equals(wgt));
            System.out.println("100°C == 100 L: "   + temp.equals(vol));

        } catch ( ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}