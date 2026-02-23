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
	
	public static Length demonstrateLengthConversion(double value , Length.LengthUnit sourceUnit,Length.LengthUnit targetUnit) {
		double resultantValue= Length.convert( value ,sourceUnit,targetUnit);
		
		Length resultantLength = new Length(resultantValue,targetUnit);
		
		return resultantLength;
		 
	}
	
	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}
	
	 public static Length demonstrateLengthAddition(Length l1, Length l2) {
	        return l1.add(l2);  
	    }

	    public static Length demonstrateLengthAddition(Length l1, Length l2, Length.LengthUnit targetUnit) {

	        return Length.add(l1, l2, targetUnit);
	    }
	
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

//        		boolean result1 = demonstrateLengthComparison(1.0,Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);
//        		boolean result2 = demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);
//        		boolean result3 = demonstrateLengthComparison(100.0, Length.LengthUnit.CENTIMETER, 39.3701, Length.LengthUnit.INCHES);
//        		boolean result4 = demonstrateLengthComparison(3.0, Length.LengthUnit.FEET, 1.0, Length.LengthUnit.YARDS);
//        		boolean result5 = demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETER, 1.0, Length.LengthUnit.FEET);
//        		
//        		System.out.println("Equal -> "+result1);
//        		System.out.println("Equal -> "+result2);
//        		System.out.println("Equal -> "+result3);
//        		System.out.println("Equal -> "+result4);
//        		System.out.println("Equal -> "+result5);
        		
        	Length l1 = demonstrateLengthConversion(1.0, Length.LengthUnit.FEET,Length.LengthUnit.INCHES);
        	System.out.println("Output: "+l1.getValue());
        	
        	Length l2 = new Length(3.0,Length.LengthUnit.YARDS);
        	Length l3 = demonstrateLengthConversion(l2, Length.LengthUnit.FEET);
        	System.out.println("Output: "+l3.getValue());
        	
        	Length l4 = demonstrateLengthConversion(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS);
        	System.out.println("Ouput: "+l4.getValue());
        	
        	Length l5 = demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETER,Length.LengthUnit.INCHES);
        	System.out.println("Output: "+l5.getValue());
        	
        	Length l6 = demonstrateLengthConversion(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
        	System.out.println("Output: "+l6.getValue());
        	
        	// Addition
        	 Length a1 = new Length(1.0, Length.LengthUnit.FEET);
             Length a2 = new Length(12.0, Length.LengthUnit.INCHES);

             Length result1 = demonstrateLengthAddition(a1, a2);
             System.out.println("1 ft + 12 in = " + result1.getValue()+" "+result1.getUnit());

             Length result2 = demonstrateLengthAddition(
                     new Length(1.0, Length.LengthUnit.YARDS),
                     new Length(3.0, Length.LengthUnit.FEET));

             System.out.println("1 yd + 3 ft = " + result2.getValue()+" "+result2.getUnit());

             Length result3 = demonstrateLengthAddition(
                     new Length(12.0, Length.LengthUnit.INCHES),
                     new Length(1.0, Length.LengthUnit.FEET));

             System.out.println("12 in + 1 ft = " + result3.getValue()+" "+result3.getUnit());

             Length result4 = demonstrateLengthAddition(
                     new Length(2.54, Length.LengthUnit.CENTIMETER),
                     new Length(1.0, Length.LengthUnit.INCHES));

             System.out.println("2.54 cm + 1 in = " + result4.getValue()+" "+result4.getUnit());

             Length result5 = demonstrateLengthAddition(
                     new Length(5.0, Length.LengthUnit.FEET),
                     new Length(-2.0, Length.LengthUnit.FEET));

             System.out.println("5 ft + (-2 ft) = " + result5.getValue()+" "+result5.getUnit());
        	

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }

        
    }
}


