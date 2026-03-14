package quantitymeasurement.com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        return q1.equals(q2);
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

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("//////COMPARISON OF LENGTHS///////////");
            boolean result1 = new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCHES));
            boolean result2 = new Quantity<>(1.0, LengthUnit.YARDS).equals(new Quantity<>(36.0, LengthUnit.INCHES));
            boolean result3 = new Quantity<>(100.0, LengthUnit.CENTIMETER).equals(new Quantity<>(39.3701, LengthUnit.INCHES));
            boolean result4 = new Quantity<>(3.0, LengthUnit.FEET).equals(new Quantity<>(1.0, LengthUnit.YARDS));
            boolean result5 = new Quantity<>(30.48, LengthUnit.CENTIMETER).equals(new Quantity<>(1.0, LengthUnit.FEET));

            System.out.println("Equal -> " + result1);
            System.out.println("Equal -> " + result2);
            System.out.println("Equal -> " + result3);
            System.out.println("Equal -> " + result4);
            System.out.println("Equal -> " + result5);

            System.out.println("///////CONVERSION OF LENGTHS///////////");
            Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
            System.out.println("Output: " + l1.getValue());

            Quantity<LengthUnit> l2 = new Quantity<>(3.0, LengthUnit.YARDS);
            Quantity<LengthUnit> l3 = l2.convertTo(LengthUnit.FEET);
            System.out.println("Output: " + l3.getValue());

            Quantity<LengthUnit> l4 = new Quantity<>(36.0, LengthUnit.INCHES).convertTo(LengthUnit.YARDS);
            System.out.println("Output: " + l4.getValue());

            Quantity<LengthUnit> l5 = new Quantity<>(1.0, LengthUnit.CENTIMETER).convertTo(LengthUnit.INCHES);
            System.out.println("Output: " + l5.getValue());

            Quantity<LengthUnit> l6 = new Quantity<>(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
            System.out.println("Output: " + l6.getValue());

            System.out.println("////////FIRST OPERAND LENGTH UNIT ADDITION//////////");
            Quantity<LengthUnit> a1 = new Quantity<>(1.0, LengthUnit.FEET);
            Quantity<LengthUnit> a2 = new Quantity<>(12.0, LengthUnit.INCHES);

            Quantity<LengthUnit> result6 = a1.add(a2);
            System.out.println("1 ft + 12 in = " + result6.getValue() + " " + result6.getUnit());

            Quantity<LengthUnit> result7 = new Quantity<>(1.0, LengthUnit.YARDS).add(new Quantity<>(3.0, LengthUnit.FEET));
            System.out.println("1 yd + 3 ft = " + result7.getValue() + " " + result7.getUnit());

            Quantity<LengthUnit> result8 = new Quantity<>(12.0, LengthUnit.INCHES).add(new Quantity<>(1.0, LengthUnit.FEET));
            System.out.println("12 in + 1 ft = " + result8.getValue() + " " + result8.getUnit());

            Quantity<LengthUnit> result9 = new Quantity<>(2.54, LengthUnit.CENTIMETER).add(new Quantity<>(1.0, LengthUnit.INCHES));
            System.out.println("2.54 cm + 1 in = " + result9.getValue() + " " + result9.getUnit());

            Quantity<LengthUnit> result10 = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(-2.0, LengthUnit.FEET));
            System.out.println("5 ft + (-2 ft) = " + result10.getValue() + " " + result10.getUnit());

            System.out.println("////////////////CROSS LENGTH UNIT ADDTION////////////////// ");

            Quantity<LengthUnit> result11 = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
            System.out.println("1 ft + 12 in = " + result11.getValue() + " " + result10.getUnit());

            Quantity<LengthUnit> result12 = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
            System.out.println("1 ft + 12 in = " + result12.getValue() + " " + result12.getUnit());

            Quantity<LengthUnit> result13 = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(-2.0, LengthUnit.FEET), LengthUnit.INCHES);
            System.out.println("5 ft + (-2 ft)  = " + result13.getValue() + " " + result13.getUnit());

            System.out.println("//////COMPARISON OF WEIGHTS///////////");

            boolean wResult1 = new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM));
            boolean wResult2 = new Quantity<>(2.0, WeightUnit.KILOGRAM).equals(new Quantity<>(2000.0, WeightUnit.GRAM));
            boolean wResult3 = new Quantity<>(1.0, WeightUnit.TONNE).equals(new Quantity<>(1000.0, WeightUnit.KILOGRAM));

            System.out.println("Equal -> " + wResult1);
            System.out.println("Equal -> " + wResult2);
            System.out.println("Equal -> " + wResult3);

            System.out.println("///////CONVERSION OF WEIGHTS/////////");
            Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
            System.out.println("Output: " + w1.getValue());

            Quantity<WeightUnit> w2 = new Quantity<>(2000.0, WeightUnit.GRAM);
            Quantity<WeightUnit> w3 = w2.convertTo(WeightUnit.KILOGRAM);
            System.out.println("Output: " + w3.getValue());

            Quantity<WeightUnit> w4 = new Quantity<>(1.0, WeightUnit.TONNE).convertTo(WeightUnit.KILOGRAM);
            System.out.println("Output: " + w4.getValue());

            Quantity<WeightUnit> w5 = new Quantity<>(500.0, WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);
            System.out.println("Output: " + w5.getValue());

            System.out.println("////////FIRST OPERAND WEIGHT UNIT ADDITION//////////");

            Quantity<WeightUnit> wa1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
            Quantity<WeightUnit> wa2 = new Quantity<>(500.0, WeightUnit.GRAM);

            Quantity<WeightUnit> wResult4 = wa1.add(wa2);
            System.out.println("1 kg + 500 g = " + wResult4.getValue() + " " + wResult4.getUnit());

            Quantity<WeightUnit> wResult5 = new Quantity<>(1.0, WeightUnit.TONNE).add(new Quantity<>(500.0, WeightUnit.KILOGRAM));
            System.out.println("1 tonne + 500 kg = " + wResult5.getValue() + " " + wResult5.getUnit());

            Quantity<WeightUnit> wResult6 = new Quantity<>(1000.0, WeightUnit.GRAM).add(new Quantity<>(1.0, WeightUnit.KILOGRAM));
            System.out.println("1000 g + 1 kg = " + wResult6.getValue() + " " + wResult6.getUnit());

            Quantity<WeightUnit> wResult7 = new Quantity<>(5.0, WeightUnit.KILOGRAM).add(new Quantity<>(-2.0, WeightUnit.KILOGRAM));
            System.out.println("5 kg + (-2 kg) = " + wResult7.getValue() + " " + wResult7.getUnit());

            System.out.println("////////////////CROSS WEIGHT UNIT ADDITION//////////////////");

            Quantity<WeightUnit> wResult8 = new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(500.0, WeightUnit.GRAM), WeightUnit.GRAM);
            System.out.println("1 kg + 500 g = " + wResult8.getValue() + " " + wResult8.getUnit());

            Quantity<WeightUnit> wResult9 = new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(500.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
            System.out.println("1 kg + 500 g = " + wResult9.getValue() + " " + wResult9.getUnit());

            Quantity<WeightUnit> wResult10 = new Quantity<>(5.0, WeightUnit.KILOGRAM).add(new Quantity<>(-2.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
            System.out.println("5 kg + (-2 kg) = " + wResult10.getValue() + " " + wResult10.getUnit());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }
    }
}