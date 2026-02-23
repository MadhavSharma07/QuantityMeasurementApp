package quantitymeasurement;


import java.util.Scanner;

import quantitymeasurement.Length.*;

public class QuantityMeasurementApp {
	
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
			return l1.equals(l2);
	}
	
	public static boolean demonstrateLengthComparison(double value1 , Length.LengthUnit unit1,double value2 , Length.LengthUnit unit2) {
		Length l1 = new Length(value1, unit1);
		Length l2 = new Length(value2,unit2);
		
		return l1.equals(l2);
	}
	
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

        		boolean result1 = demonstrateLengthComparison(1.0,Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);
        		boolean result2 = demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);
        		boolean result3 = demonstrateLengthComparison(100.0, Length.LengthUnit.CENTIMETER, 39.3701, Length.LengthUnit.INCHES);
        		boolean result4 = demonstrateLengthComparison(3.0, Length.LengthUnit.FEET, 1.0, Length.LengthUnit.YARDS);
        		boolean result5 = demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETER, 1.0, Length.LengthUnit.FEET);
        		
        		System.out.println("Equal -> "+result1);
        		System.out.println("Equal -> "+result2);
        		System.out.println("Equal -> "+result3);
        		System.out.println("Equal -> "+result4);
        		System.out.println("Equal -> "+result5);
        		
        	
        	
        	

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }

        
    }
}


