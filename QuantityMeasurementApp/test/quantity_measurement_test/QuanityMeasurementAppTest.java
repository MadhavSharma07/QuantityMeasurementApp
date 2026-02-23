package quantity_measurement_test;


import org.junit.jupiter.api.Test;

import quantitymeasurement.Length;
import quantitymeasurement.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testEquality_FeetSameValue() {
        Length l1 = new Length(1.0,Length.LengthUnit.FEET);
        Length l2 = new Length(1.0,Length.LengthUnit.FEET);
        
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_InchesSameValue() {
        Length l1 = new Length(2.0,Length.LengthUnit.INCHES);
        Length l2 = new Length(2.0,Length.LengthUnit.INCHES);
        
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_FeetInchesEquivalenceValue() {
    	 Length l1 = new Length(2.0,Length.LengthUnit.FEET);
         Length l2 = new Length(24.0,Length.LengthUnit.INCHES);
         
         assertTrue(l1.equals(l2));
    }
    @Test
    void testEquality_FeetDifferentValue() {
    	Length l1 = new Length(1.0,Length.LengthUnit.FEET);
        Length l2 = new Length(2.0,Length.LengthUnit.FEET);
        
        assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_InchesDifferentValue() {
        Length l1 = new Length(2.0,Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0,Length.LengthUnit.INCHES);
        
        assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_SameReference() {
        Length l1 = new Length(1.0,Length.LengthUnit.FEET);
        
        assertTrue(l1.equals(l1));
    }

    @Test
    void testEquality_NullComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }
    
    @Test
    void testEquality_YardsSameValue() {
    	Length l1  = new Length(1.0,Length.LengthUnit.YARDS);
    	Length l2 = new Length(1.0,Length.LengthUnit.YARDS);
    	
    	assertTrue(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardsDifferentValue() {
    	Length l1  = new Length(1.0,Length.LengthUnit.YARDS);
    	Length l2 = new Length(3.0,Length.LengthUnit.YARDS);
    	
    	assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardFeetEquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardInchesEquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(36.0, Length.LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
     }
    
    @Test
    void testEquality_CentimeterInchesEquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.CENTIMETER);
        Length l2 = new Length(0.393701, Length.LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }
    

    @Test
    void testConversion_FeetToInches() {
        Length result = new Length(1.0, Length.LengthUnit.FEET)
                .convertTo(Length.LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), 0.0001);
    }

    @Test
    void testConversion_YardToFeet() {
        Length result = new Length(1.0, Length.LengthUnit.YARDS)
                .convertTo(Length.LengthUnit.FEET);

        assertEquals(3.0, result.getValue(), 0.0001);
    }

    @Test
    void testConversion_CentimeterToInches() {
        Length result = new Length(1.0, Length.LengthUnit.CENTIMETER)
                .convertTo(Length.LengthUnit.INCHES);

        assertEquals(0.393701, result.getValue(), 0.0001);
    }
    
    @Test
    void testAddition_SameUnit_Feet() {
        Length result = new Length(1.0, Length.LengthUnit.FEET)
                .add(new Length(2.0, Length.LengthUnit.FEET));

        assertEquals(
                new Length(3.0, Length.LengthUnit.FEET),
                result);
    }

    @Test
    void testAddition_CrossUnit_InchFeet() {
        Length result = new Length(12.0, Length.LengthUnit.INCHES).add(new Length(1.0, Length.LengthUnit.FEET));
        assertEquals(new Length(24.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_YardFeet() {
        Length result = new Length(1.0, Length.LengthUnit.YARDS).add(new Length(3.0, Length.LengthUnit.FEET));
        assertEquals(new Length(2.0, Length.LengthUnit.YARDS),result);
    }

    @Test
    void testAddition_CentimeterInch() {
        Length result = new Length(2.54, Length.LengthUnit.CENTIMETER).add(new Length(1.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(5.08, Length.LengthUnit.CENTIMETER),result);
    }


    @Test
    void testAddition_WithZero() {
        Length result = new Length(5.0, Length.LengthUnit.FEET).add(new Length(0.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(5.0, Length.LengthUnit.FEET), result);
    }


    @Test
    void testAddition_NullOperand() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,() -> l1.add(null));
    }

   

}

