package quantitymeasurement.com.apps.quantitymeasurement;


import org.junit.jupiter.api.Test;

import quantitymeasurement.com.apps.quantitymeasurement.Length;
import quantitymeasurement.com.apps.quantitymeasurement.LengthUnit;
import quantitymeasurement.com.apps.quantitymeasurement.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testEquality_FeetSameValue() {
        Length l1 = new Length(1.0,LengthUnit.FEET);
        Length l2 = new Length(1.0,LengthUnit.FEET);
        
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_InchesSameValue() {
        Length l1 = new Length(2.0,LengthUnit.INCHES);
        Length l2 = new Length(2.0,LengthUnit.INCHES);
        
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_FeetInchesEquivalenceValue() {
    	 Length l1 = new Length(2.0,LengthUnit.FEET);
         Length l2 = new Length(24.0,LengthUnit.INCHES);
         
         assertTrue(l1.equals(l2));
    }
    @Test
    void testEquality_FeetDifferentValue() {
    	Length l1 = new Length(1.0,LengthUnit.FEET);
        Length l2 = new Length(2.0,LengthUnit.FEET);
        
        assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_InchesDifferentValue() {
        Length l1 = new Length(2.0,LengthUnit.INCHES);
        Length l2 = new Length(1.0,LengthUnit.INCHES);
        
        assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_SameReference() {
        Length l1 = new Length(1.0,LengthUnit.FEET);
        
        assertTrue(l1.equals(l1));
    }

    @Test
    void testEquality_NullComparison() {
        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }
    
    @Test
    void testEquality_YardsSameValue() {
    	Length l1  = new Length(1.0,LengthUnit.YARDS);
    	Length l2 = new Length(1.0,LengthUnit.YARDS);
    	
    	assertTrue(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardsDifferentValue() {
    	Length l1  = new Length(1.0,LengthUnit.YARDS);
    	Length l2 = new Length(3.0,LengthUnit.YARDS);
    	
    	assertFalse(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardFeetEquivalentValue() {
        Length l1 = new Length(1.0, LengthUnit.YARDS);
        Length l2 = new Length(3.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }
    
    @Test
    void testEquality_YardInchesEquivalentValue() {
        Length l1 = new Length(1.0, LengthUnit.YARDS);
        Length l2 = new Length(36.0,LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
     }
    
    @Test
    void testEquality_CentimeterInchesEquivalentValue() {
        Length l1 = new Length(1.0, LengthUnit.CENTIMETER);
        Length l2 = new Length(0.393701, LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }
    

    @Test
    void testConversion_FeetToInches() {
        Length result = new Length(1.0, LengthUnit.FEET)
                .convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), 0.0001);
    }

    @Test
    void testConversion_YardToFeet() {
        Length result = new Length(1.0,LengthUnit.YARDS)
                .convertTo(LengthUnit.FEET);

        assertEquals(3.0, result.getValue(), 0.0001);
    }

    @Test
    void testConversion_CentimeterToInches() {
        Length result = new Length(1.0, LengthUnit.CENTIMETER)
                .convertTo(LengthUnit.INCHES);

        assertEquals(0.393701, result.getValue(), 0.0001);
    }
    
    @Test
    void testAddition_SameUnit_Feet() {
        Length result = new Length(1.0, LengthUnit.FEET)
                .add(new Length(2.0,LengthUnit.FEET));

        assertEquals(
                new Length(3.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testAddition_CrossUnit_InchFeet() {
        Length result = new Length(12.0, LengthUnit.INCHES).add(new Length(1.0, LengthUnit.FEET));
        assertEquals(new Length(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_YardFeet() {
        Length result = new Length(1.0, LengthUnit.YARDS).add(new Length(3.0, LengthUnit.FEET));
        assertEquals(2.0,result.getValue());
    }

    @Test
    void testAddition_CentimeterInch() {
        Length result = new Length(2.54, LengthUnit.CENTIMETER).add(new Length(1.0, LengthUnit.INCHES));
        assertEquals(new Length(5.08, LengthUnit.CENTIMETER),result);
    }


    @Test
    void testAddition_WithZero() {
        Length result = new Length(5.0, LengthUnit.FEET).add(new Length(0.0, LengthUnit.INCHES));
        assertEquals(new Length(5.0, LengthUnit.FEET), result);
    }


    @Test
    void testAddition_NullOperand() {
        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,() -> l1.add(null));
    }


    @Test
    void testAddition_TargetFeet() {
        Length result = new Length(1.0, LengthUnit.FEET) .add(new Length(12.0,LengthUnit.INCHES), LengthUnit.FEET);

        assertEquals(new Length(2.0, LengthUnit.FEET),result);
    }

    @Test
    void testAddition_TargetInches() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES),LengthUnit.INCHES);

        assertEquals(new Length(24.0, LengthUnit.INCHES), result);
    }
    
    @Test
     void testAddition_DifferentOperands() {
    	Length result = new Length(1.0,LengthUnit.FEET).add(new Length(12.0,LengthUnit.INCHES),LengthUnit.YARDS);
    	assertEquals(new Length(0.6666666,LengthUnit.YARDS),result);
    }

}

