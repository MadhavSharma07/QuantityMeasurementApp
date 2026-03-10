package quantitymeasurement.com.apps.quantitymeasurement;


import java.util.Scanner;

import quantitymeasurement.com.apps.quantitymeasurement.Length.*;

public class QuantityMeasurementApp {
	//Length methods
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
	
	 // Weight methods
	 public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
			return w1.equals(w2);
	}
	 
	 public static boolean demonstrateWeightComparison(double value1 , WeightUnit unit1,double value2 , WeightUnit unit2) {
			Weight w1 = new Weight(value1, unit1);
			Weight w2 = new Weight(value2,unit2);
			
			return w1.equals(w2);
		}
	 
	 public static Weight demonstrateWeightConversion(double value , WeightUnit sourceUnit,WeightUnit targetUnit) {
			double resultantValue= Weight.convert( value ,sourceUnit,targetUnit);
			
			Weight resultantLength = new Weight(resultantValue,targetUnit);
			
			return resultantLength;
			 
		}
	 public static Weight demonstrateWeightConversion(Weight weight, WeightUnit toUnit) {
			return weight.convertTo(toUnit);
		}
		
		 public static Weight demonstrateWeightAddition(Weight w1, Weight w2) {
		        return w1.add(w2);  
		    }

		 
		 public static Weight demonstrateWeightAddition( Weight w1, Weight w2,WeightUnit targetUnit) {

		        return w1.add(w2, targetUnit);
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
        		
        	System.out.println("///////CONVERSION OF LENGTHS///////////");
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
        	
        	System.out.println("////////FIRST OPERAND LENGTH UNIT ADDITION//////////");
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
             
             System.out.println("////////////////CROSS LENGTH UNIT ADDTION////////////////// ");
        	//Addition in Cross Length units
             
             Length result11 = demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET),new Length(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
             System.out.println("1 ft + 12 in = " + result11.getValue()+" "+result10.getUnit());

             Length result12 = demonstrateLengthAddition( new Length(1.0,LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
             System.out.println("1 ft + 12 in = " + result12.getValue()+" "+result12.getUnit());

             Length result13 = demonstrateLengthAddition(new Length(5.0, LengthUnit.FEET),new Length(-2.0, LengthUnit.FEET),LengthUnit.INCHES);
             System.out.println("5 ft + (-2 ft)  = " + result13.getValue()+" "+result13.getUnit());


             System.out.println("//////COMPARISON OF WEIGHTS///////////");

             boolean wResult1 = demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
             boolean wResult2 = demonstrateWeightComparison(2.0, WeightUnit.KILOGRAM, 2000.0, WeightUnit.GRAM);
             boolean wResult3 = demonstrateWeightComparison(1.0, WeightUnit.TONNE, 1000.0, WeightUnit.KILOGRAM);

             System.out.println("Equal -> " + wResult1);
             System.out.println("Equal -> " + wResult2);
             System.out.println("Equal -> " + wResult3);

             System.out.println("///////CONVERSION OF WEIGHTS/////////");
             Weight w1 = demonstrateWeightConversion(1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
             System.out.println("Output: " + w1.getValue());

             Weight w2 = new Weight(2000.0, WeightUnit.GRAM);
             Weight w3 = demonstrateWeightConversion(w2, WeightUnit.KILOGRAM);
             System.out.println("Output: " + w3.getValue());

             Weight w4 = demonstrateWeightConversion(1.0, WeightUnit.TONNE, WeightUnit.KILOGRAM);
             System.out.println("Output: " + w4.getValue());

             Weight w5 = demonstrateWeightConversion(500.0, WeightUnit.GRAM, WeightUnit.KILOGRAM);
             System.out.println("Output: " + w5.getValue());


             System.out.println("////////FIRST OPERAND WEIGHT UNIT ADDITION//////////");

             // Addition

             Weight wa1 = new Weight(1.0, WeightUnit.KILOGRAM);
             Weight wa2 = new Weight(500.0, WeightUnit.GRAM);

             Weight wResult4 = demonstrateWeightAddition(wa1, wa2);
             System.out.println("1 kg + 500 g = " + wResult4.getValue() + " " + wResult4.getUnit());

             Weight wResult5 = demonstrateWeightAddition(
                     new Weight(1.0, WeightUnit.TONNE),
                     new Weight(500.0, WeightUnit.KILOGRAM));

             System.out.println("1 tonne + 500 kg = " + wResult5.getValue() + " " + wResult5.getUnit());

             Weight wResult6 = demonstrateWeightAddition(
                     new Weight(1000.0, WeightUnit.GRAM),
                     new Weight(1.0, WeightUnit.KILOGRAM));

             System.out.println("1000 g + 1 kg = " + wResult6.getValue() + " " + wResult6.getUnit());

             Weight wResult7 = demonstrateWeightAddition(
                     new Weight(5.0, WeightUnit.KILOGRAM),
                     new Weight(-2.0, WeightUnit.KILOGRAM));

             System.out.println("5 kg + (-2 kg) = " + wResult7.getValue() + " " + wResult7.getUnit());


             System.out.println("////////////////CROSS WEIGHT UNIT ADDITION//////////////////");

             // Cross Unit Addition

             Weight wResult8 = demonstrateWeightAddition(
                     new Weight(1.0, WeightUnit.KILOGRAM),
                     new Weight(500.0, WeightUnit.GRAM),
                     WeightUnit.GRAM);

             System.out.println("1 kg + 500 g = " + wResult8.getValue() + " " + wResult8.getUnit());

             Weight wResult9 = demonstrateWeightAddition(
                     new Weight(1.0, WeightUnit.KILOGRAM),
                     new Weight(500.0, WeightUnit.GRAM),
                     WeightUnit.KILOGRAM);

             System.out.println("1 kg + 500 g = " + wResult9.getValue() + " " + wResult9.getUnit());

             Weight wResult10 = demonstrateWeightAddition(
                     new Weight(5.0, WeightUnit.KILOGRAM),
                     new Weight(-2.0, WeightUnit.KILOGRAM),
                     WeightUnit.GRAM);

             System.out.println("5 kg + (-2 kg) = " + wResult10.getValue() + " " + wResult10.getUnit());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input or unsupported unit.");
        }

        
    }
}


