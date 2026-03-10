package quantitymeasurement.com.apps.quantitymeasurement;


import java.util.Scanner;

import quantitymeasurement.com.apps.quantitymeasurement.Length.*;

public class QuantityMeasurementApp {
	
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
			return l1.equals(l2);
	}
	
	public static boolean demonstrateLengthComparison(double value1 , LengthUnit unit1,double value2 , LengthUnit unit2) {
		Length l1 = new Length(value1, unit1);
		Length l2 = new Length(value2,unit2);
		
		return l1.equals(l2);
	}
	
	public static Length demonstrateLengthConversion(double value , LengthUnit sourceUnit,LengthUnit targetUnit) {
		double resultantValue= Length.convert( value ,sourceUnit,targetUnit);
		
		Length resultantLength = new Length(resultantValue,targetUnit);
		
		return resultantLength;
		 
	}
	
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}
	
	 public static Length demonstrateLengthAddition(Length l1, Length l2) {
	        return l1.add(l2);  
	    }

	 
	 public static Length demonstrateLengthAddition( Length l1, Length l2,LengthUnit targetUnit) {

	        return l1.add(l2, targetUnit);
	    }
	
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
        		System.out.println("//////COMPARISON OF LENGTHS///////////");
        		boolean result1 = demonstrateLengthComparison(1.0,LengthUnit.FEET, 12.0, LengthUnit.INCHES);
        		boolean result2 = demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);
        		boolean result3 = demonstrateLengthComparison(100.0,LengthUnit.CENTIMETER, 39.3701, LengthUnit.INCHES);
        		boolean result4 = demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);
        		boolean result5 = demonstrateLengthComparison(30.48, LengthUnit.CENTIMETER, 1.0, LengthUnit.FEET);
        		
        		System.out.println("Equal -> "+result1);
        		System.out.println("Equal -> "+result2);
        		System.out.println("Equal -> "+result3);
        		System.out.println("Equal -> "+result4);
        		System.out.println("Equal -> "+result5);
        		
        	Length l1 = demonstrateLengthConversion(1.0, LengthUnit.FEET,LengthUnit.INCHES);
        	System.out.println("Output: "+l1.getValue());
        	
        	Length l2 = new Length(3.0,LengthUnit.YARDS);
        	Length l3 = demonstrateLengthConversion(l2, LengthUnit.FEET);
        	System.out.println("Output: "+l3.getValue());
        	
        	Length l4 = demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        	System.out.println("Output: "+l4.getValue());
        	
        	Length l5 = demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER,LengthUnit.INCHES);
        	System.out.println("Output: "+l5.getValue());
        	
        	Length l6 = demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);
        	System.out.println("Output: "+l6.getValue());
        	
        	System.out.println("////////FIRST OPERAND LENGTHUNIT ADDITION//////////");
        	// Addition 
        	 Length a1 = new Length(1.0, LengthUnit.FEET);
             Length a2 = new Length(12.0, LengthUnit.INCHES);

             Length result6 = demonstrateLengthAddition(a1, a2);
             System.out.println("1 ft + 12 in = " + result6.getValue()+" "+result6.getUnit());

             Length result7 = demonstrateLengthAddition(
                     new Length(1.0, LengthUnit.YARDS),
                     new Length(3.0, LengthUnit.FEET));

             System.out.println("1 yd + 3 ft = " + result7.getValue()+" "+result7.getUnit());

             Length result8 = demonstrateLengthAddition(
                     new Length(12.0, LengthUnit.INCHES),
                     new Length(1.0, LengthUnit.FEET));

             System.out.println("12 in + 1 ft = " + result8.getValue()+" "+result8.getUnit());

             Length result9 = demonstrateLengthAddition(
                     new Length(2.54, LengthUnit.CENTIMETER),
                     new Length(1.0, LengthUnit.INCHES));

             System.out.println("2.54 cm + 1 in = " + result9.getValue()+" "+result9.getUnit());

             Length result10 = demonstrateLengthAddition(new Length(5.0,LengthUnit.FEET),new Length(-2.0, LengthUnit.FEET));

             System.out.println("5 ft + (-2 ft) = " + result10.getValue()+" "+result10.getUnit());
             
             System.out.println("////////////////CROSS LENGTHUNIT ADDTION////////////////// ");
        	//Addition in Cross Length units
             Length result11 = demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET),new Length(12.0, LengthUnit.INCHES), LengthUnit.INCHES);

             System.out.println("1 ft + 12 in = " + result11.getValue()+" "+result10.getUnit());

             Length result12 = demonstrateLengthAddition( new Length(1.0,LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);

             System.out.println("1 ft + 12 in = " + result12.getValue()+" "+result12.getUnit());

             Length result13 = demonstrateLengthAddition(new Length(5.0, LengthUnit.FEET),new Length(-2.0, LengthUnit.FEET),LengthUnit.INCHES);

             System.out.println("5 ft + (-2 ft)  = " + result13.getValue()+" "+result13.getUnit());


        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }

        
    }
}


