package quantitymeasurement;


import java.util.Scanner;

public class QuantityMeasurementApp {

 // feet class
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

  // inches class
    public static class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

   // comparison methods

    public static boolean compareFeet(double value1, double value2) {
        return new Feet(value1).equals(new Feet(value2));
    }

    public static boolean compareInches(double value1, double value2) {
        return new Inches(value1).equals(new Inches(value2));
    }

  // main method

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Select Unit:");
            System.out.println("1. Feet");
            System.out.println("2. Inches");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter first value: ");
            double value1 = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter second value: ");
            double value2 = Double.parseDouble(scanner.nextLine());

            boolean result = false;

            switch (choice) {
                case 1:
                    result = compareFeet(value1, value2);
                    break;

                case 2:
                    result = compareInches(value1, value2);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            System.out.println("Equal: " + result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values only.");
        }

        scanner.close();
    }
}

