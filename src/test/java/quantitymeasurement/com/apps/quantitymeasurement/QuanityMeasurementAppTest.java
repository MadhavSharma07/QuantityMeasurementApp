package quantitymeasurement.com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 0.0001;

    // ==================== Length Equality Tests ====================

    @Test
    void testEquality_FeetSameValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_FeetInchesEquivalenceValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(24.0, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_FeetDifferentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquality_SameReference() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(q1.equals(q1));
    }

    @Test
    void testEquality_NullComparison() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(q1.equals(null));
    }

    @Test
    void testEquality_YardFeetEquivalentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_CentimeterInchesEquivalentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> q2 = new Quantity<>(0.393701, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    // ==================== Length Conversion Tests ====================

    @Test
    void testConversion_FeetToInches() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_YardToFeet() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.YARDS).convertTo(LengthUnit.FEET);
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_CentimeterToInches() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.CENTIMETER).convertTo(LengthUnit.INCHES);
        assertEquals(0.393701, result.getValue(), EPSILON);
    }

    // ==================== Length Addition Tests ====================

    @Test
    void testAddition_SameUnit_Feet() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_InchFeet() {
        Quantity<LengthUnit> result = new Quantity<>(12.0, LengthUnit.INCHES)
                .add(new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(new Quantity<>(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_WithZero() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .add(new Quantity<>(0.0, LengthUnit.INCHES));
        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.add(null));
    }

    @Test
    void testAddition_TargetInches() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(new Quantity<>(24.0, LengthUnit.INCHES), result);
    }

    // ==================== Weight Equality Tests ====================

    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_KilogramToGram_EquivalentValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_NullComparison() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(w1.equals(null));
    }

    @Test
    void testWeightEquality_ZeroValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(0.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(0.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    // ==================== Weight Conversion Tests ====================

    @Test
    void testWeightConversion_KilogramToGram() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_GramToKilogram() {
        Quantity<WeightUnit> result = new Quantity<>(1000.0, WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_RoundTrip() {
        Quantity<WeightUnit> result = new Quantity<>(1.5, WeightUnit.KILOGRAM)
                .convertTo(WeightUnit.GRAM)
                .convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.5, result.getValue(), EPSILON);
    }

    // ==================== Weight Addition Tests ====================

    @Test
    void testWeightAddition_SameUnit_KilogramPlusKilogram() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(2.0, WeightUnit.KILOGRAM));
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testWeightAddition_CrossUnit_KilogramPlusGram() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(1000.0, WeightUnit.GRAM));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightAddition_ExplicitTargetUnit() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);
        assertEquals(2000.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    // ==================== Constructor Validation Tests ====================

    @Test
    void testConstructorValidation_NullUnit() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructorValidation_NaNValue() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testConstructorValidation_InfiniteValue() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
    }

    // ==================== Demonstration Tests ====================

    @Test
    void testQuantityMeasurementApp_demonstrateEquality_Length() {
        assertTrue(QuantityMeasurementApp.demonstrateEquality(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES)));
    }

    @Test
    void testQuantityMeasurementApp_demonstrateConversion_Length() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateConversion(
                new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateAddition_Length() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateAddition(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES),
                LengthUnit.FEET);
        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    // ==================== Volume Equality Tests ====================

    @Test
    void testEquality_LitreToLitre_SameValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(v1.equals(l1));
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(v1.equals(w1));
    }

    // ==================== Volume Conversion Tests ====================

    @Test
    void testVolumeConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_GallonToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_RoundTrip() {
        Quantity<VolumeUnit> result = new Quantity<>(1.5, VolumeUnit.LITRE)
                .convertTo(VolumeUnit.MILLILITRE)
                .convertTo(VolumeUnit.LITRE);
        assertEquals(1.5, result.getValue(), EPSILON);
    }

    // ==================== Volume Addition Tests ====================

    @Test
    void testVolumeAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
        assertEquals(2000.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void testVolumeAddition_NullOperand() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertThrows(IllegalArgumentException.class, () -> v1.add(null));
    }

    // ==================== UC12: Subtraction Tests ====================

    @Test
    void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(5.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Inches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(114.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(-5.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_ResultingInZero() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(120.0, LengthUnit.INCHES));
        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
    }

    @Test
    void testSubtraction_NullTargetUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
    }

    @Test
    void testSubtraction_CrossCategory() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> q2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2));
    }

    @Test
    void testSubtraction_NonCommutative() {
        Quantity<LengthUnit> r1 = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        Quantity<LengthUnit> r2 = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(5.0, r1.getValue(), EPSILON);
        assertEquals(-5.0, r2.getValue(), EPSILON);
        assertFalse(r1.equals(r2));
    }

    @Test
    void testSubtraction_Immutability() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other = new Quantity<>(3.0, LengthUnit.FEET);
        original.subtract(other);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(3.0, other.getValue(), EPSILON);
    }

    @Test
    void testSubtractionAddition_Inverse() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = a.add(b).subtract(b);
        assertEquals(10.0, result.getValue(), EPSILON);
    }

    // ==================== UC12: Division Tests ====================

    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testDivision_CrossUnit_FeetDividedByInches() {
        double result = new Quantity<>(24.0, LengthUnit.INCHES)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testDivision_RatioGreaterThanOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertTrue(result > 1.0);
    }

    @Test
    void testDivision_RatioLessThanOne() {
        double result = new Quantity<>(5.0, LengthUnit.FEET)
                .divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(0.5, result, EPSILON);
        assertTrue(result < 1.0);
    }

    @Test
    void testDivision_RatioEqualToOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testDivision_NonCommutative() {
        double r1 = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
        double r2 = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(2.0, r1, EPSILON);
        assertEquals(0.5, r2, EPSILON);
        assertNotEquals(r1, r2, EPSILON);
    }

    @Test
    void testDivision_ByZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

    @Test
    void testDivision_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.divide(null));
    }

    @Test
    void testDivision_CrossCategory() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> q2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> q1.divide(q2));
    }

    @Test
    void testDivision_Immutability() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisor = new Quantity<>(2.0, LengthUnit.FEET);
        original.divide(divisor);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(2.0, divisor.getValue(), EPSILON);
    }

    @Test
    void testSubtractionAndDivision_Integration() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(4.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(2.0, LengthUnit.FEET);
        double result = a.subtract(b).divide(c);
        assertEquals(3.0, result, EPSILON);
    }

    // ==================== UC13: Validation Consistency Tests ====================

    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> q.add(null));
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> q.divide(null));
        assertEquals(ex1.getMessage(), ex2.getMessage());
        assertEquals(ex2.getMessage(), ex3.getMessage());
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> q2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2));
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> q1.divide(q2));
        assertEquals(ex1.getMessage(), ex2.getMessage());
    }

    @Test
    void testValidation_NullTargetUnit_AddSubtractReject() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
    }

    // ==================== UC13: Enum Computation Tests ====================

    @Test
    void testArithmetic_Add_CorrectResult() {
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .add(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(10.0, result.getValue(), EPSILON);
    }

    @Test
    void testArithmetic_Subtract_CorrectResult() {
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .subtract(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(4.0, result.getValue(), EPSILON);
    }

    @Test
    void testArithmetic_Divide_CorrectResult() {
        double result = new Quantity<>(7.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(3.5, result, EPSILON);
    }

    @Test
    void testArithmetic_DivideByZero_ThrowsArithmeticException() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

    // ==================== UC13: Immutability via Centralized Helper ====================

    @Test
    void testImmutability_AfterAdd_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);
        original.add(other);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(5.0, other.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterSubtract_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other = new Quantity<>(3.0, LengthUnit.FEET);
        original.subtract(other);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(3.0, other.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterDivide_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisor = new Quantity<>(2.0, LengthUnit.FEET);
        original.divide(divisor);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(2.0, divisor.getValue(), EPSILON);
    }

    // ==================== UC13: Chain Operations ====================

    @Test
    void testArithmetic_Chain_Operations() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q3 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q4 = new Quantity<>(3.0, LengthUnit.FEET);
        double result = q1.add(q2).subtract(q3).divide(q4);
        assertEquals(11.0 / 3.0, result, EPSILON);
    }

    @Test
    void testArithmetic_ImplicitTargetUnit_UsesFirstOperandsUnit() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.INCHES)
                .subtract(new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(LengthUnit.INCHES, result.getUnit());
        assertEquals(-2.0, result.getValue(), EPSILON);
    }

    @Test
    void testArithmetic_ExplicitTargetUnit_OverridesFirstOperandsUnit() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(LengthUnit.YARDS, result.getUnit());
        assertEquals(9.5 / 3.0, result.getValue(), EPSILON);
    }

    // ==================== UC13: Demonstration Tests ====================

    @Test
    void testQuantityMeasurementApp_demonstrateSubtraction_Length() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateSubtraction(
                new Quantity<>(10.0, LengthUnit.FEET),
                new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testQuantityMeasurementApp_demonstrateSubtraction_WithTargetUnit() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateSubtraction(
                new Quantity<>(10.0, LengthUnit.FEET),
                new Quantity<>(6.0, LengthUnit.INCHES),
                LengthUnit.INCHES);
        assertEquals(114.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testQuantityMeasurementApp_demonstrateDivision_Length() {
        double result = QuantityMeasurementApp.demonstrateDivision(
                new Quantity<>(10.0, LengthUnit.FEET),
                new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, EPSILON);
    }
}