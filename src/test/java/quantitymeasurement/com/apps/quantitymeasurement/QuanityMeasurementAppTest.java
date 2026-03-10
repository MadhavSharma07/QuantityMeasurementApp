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
    void testLengthEquality_NullComparison() {
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
    
    private static final double EPSILON = 0.0001;

    // ---------- Equality Tests ----------

    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_KilogramToKilogram_DifferentValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);

        assertFalse(w1.equals(w2));
    }

    @Test
    void testWeightEquality_KilogramToGram_EquivalentValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_GramToKilogram_EquivalentValue() {
        Weight w1 = new Weight(1000.0, WeightUnit.GRAM);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_NullComparison() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertFalse(w1.equals(null));
    }

    @Test
    void testWeightEquality_SameReference() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w1));
    }

    @Test
    void testWeightEquality_ZeroValue() {
        Weight w1 = new Weight(0.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(0.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_NegativeWeight() {
        Weight w1 = new Weight(-1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(-1000.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_LargeWeightValue() {
        Weight w1 = new Weight(1000000.0, WeightUnit.GRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_SmallWeightValue() {
        Weight w1 = new Weight(0.001, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    // ---------- Conversion Tests ----------

    @Test
    void testWeightConversion_KilogramToGram() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_GramToKilogram() {
        Weight w = new Weight(1000.0, WeightUnit.GRAM);

        Weight result = w.convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_SameUnit() {
        Weight w = new Weight(5.0, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.KILOGRAM);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_ZeroValue() {
        Weight w = new Weight(0.0, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.GRAM);

        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_NegativeValue() {
        Weight w = new Weight(-1.0, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.GRAM);

        assertEquals(-1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_RoundTrip() {
        Weight w = new Weight(1.5, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.GRAM)
                         .convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.5, result.getValue(), EPSILON);
    }

    // ---------- Addition Tests ----------

    @Test
    void testWeightAddition_SameUnit_KilogramPlusKilogram() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);

        Weight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testWeightAddition_CrossUnit_KilogramPlusGram() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        Weight result = w1.add(w2);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightAddition_ExplicitTargetUnit() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        Weight result = w1.add(w2, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void testWeightAddition_Commutativity() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        Weight r1 = w1.add(w2);
        Weight r2 = w2.add(w1);

        assertTrue(r1.equals(r2));
    }

    @Test
    void testWeightAddition_WithZero() {
        Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(0.0, WeightUnit.GRAM);

        Weight result = w1.add(w2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightAddition_NegativeValues() {
        Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(-2000.0, WeightUnit.GRAM);

        Weight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

  
}

