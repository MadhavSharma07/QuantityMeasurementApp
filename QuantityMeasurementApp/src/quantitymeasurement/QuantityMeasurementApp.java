package quantitymeasurement;


import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter first value:");
            double value1 = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter first unit (FEET / INCHES):");
            Length.LengthUnit unit1 =
                    Length.LengthUnit.valueOf(scanner.nextLine().toUpperCase());

            System.out.println("Enter second value:");
            double value2 = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter second unit (FEET / INCHES):");
            Length.LengthUnit unit2 =
                    Length.LengthUnit.valueOf(scanner.nextLine().toUpperCase());

            Length length1 = new Length(value1, unit1);
            Length length2 = new Length(value2, unit2);

            boolean result = length1.equals(length2);

            System.out.println("Equal: " + result);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }

        scanner.close();
    }
}


