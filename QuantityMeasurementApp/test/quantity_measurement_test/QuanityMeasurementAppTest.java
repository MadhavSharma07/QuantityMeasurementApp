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

  
}

