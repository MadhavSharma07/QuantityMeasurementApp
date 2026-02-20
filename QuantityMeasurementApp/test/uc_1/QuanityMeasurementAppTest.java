package uc_1;


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
}

