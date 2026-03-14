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
    void testEquality_InchesSameValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.INCHES);
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
    void testEquality_YardInchesEquivalentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(36.0, LengthUnit.INCHES);
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
    void testAddition_YardFeet() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.YARDS)
                .add(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CentimeterInch() {
        Quantity<LengthUnit> result = new Quantity<>(2.54, LengthUnit.CENTIMETER)
                .add(new Quantity<>(1.0, LengthUnit.INCHES));
        assertEquals(new Quantity<>(5.08, LengthUnit.CENTIMETER), result);
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
    void testAddition_TargetFeet() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_TargetInches() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(new Quantity<>(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_DifferentOperands() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(new Quantity<>(0.6666666, LengthUnit.YARDS), result);
    }

    // ==================== Weight Equality Tests ====================

    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_KilogramToKilogram_DifferentValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(2.0, WeightUnit.KILOGRAM);
        assertFalse(w1.equals(w2));
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
    void testWeightEquality_SameReference() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w1));
    }

    @Test
    void testWeightEquality_ZeroValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(0.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(0.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_NegativeWeight() {
        Quantity<WeightUnit> w1 = new Quantity<>(-1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(-1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_LargeWeightValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1000000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_SmallWeightValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(0.001, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1.0, WeightUnit.GRAM);
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
    void testWeightConversion_SameUnit() {
        Quantity<WeightUnit> result = new Quantity<>(5.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM);
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_ZeroValue() {
        Quantity<WeightUnit> result = new Quantity<>(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion_NegativeValue() {
        Quantity<WeightUnit> result = new Quantity<>(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
        assertEquals(-1000.0, result.getValue(), EPSILON);
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

    @Test
    void testWeightAddition_Commutativity() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> r1 = w1.add(w2);
        Quantity<WeightUnit> r2 = w2.add(w1);
        assertTrue(r1.equals(r2));
    }

    @Test
    void testWeightAddition_WithZero() {
        Quantity<WeightUnit> result = new Quantity<>(5.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(0.0, WeightUnit.GRAM));
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightAddition_NegativeValues() {
        Quantity<WeightUnit> result = new Quantity<>(5.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(-2000.0, WeightUnit.GRAM));
        assertEquals(3.0, result.getValue(), EPSILON);
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

    // ====================  Demonstration Tests ====================

    @Test
    void testQuantityMeasurementApp_demonstrateEquality_Length() {
        assertTrue(QuantityMeasurementApp.demonstrateEquality(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES)));
    }

    @Test
    void testQuantityMeasurementApp_demonstrateEquality_Weight() {
        assertTrue(QuantityMeasurementApp.demonstrateEquality(
                new Quantity<>(1.0, WeightUnit.KILOGRAM),
                new Quantity<>(1000.0, WeightUnit.GRAM)));
    }

    @Test
    void testQuantityMeasurementApp_demonstrateConversion_Length() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateConversion(
                new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateConversion_Weight() {
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateConversion(
                new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateAddition_Length() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateAddition(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES),
                LengthUnit.FEET);
        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateAddition_Weight() {
        Quantity<WeightUnit> result = QuantityMeasurementApp.demonstrateAddition(
                new Quantity<>(1.0, WeightUnit.KILOGRAM),
                new Quantity<>(1000.0, WeightUnit.GRAM),
                WeightUnit.KILOGRAM);
        assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
    }

    // ==================== Volume Equality Tests ====================

    @Test
    void testEquality_LitreToLitre_SameValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_MillilitreToMillilitre_SameValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_GallonToGallon_SameValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.264172, VolumeUnit.GALLON);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2.0, VolumeUnit.LITRE);
        assertFalse(v1.equals(v2));
    }

    @Test
    void testEquality_VolumeNullComparison() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertFalse(v1.equals(null));
    }

    @Test
    void testEquality_VolumeSameReference() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(v1.equals(v1));
    }

    @Test
    void testEquality_VolumeZeroValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(0.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_VolumeNegativeValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(-1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_VolumeLargeValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1000000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.LITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_VolumeSmallValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(0.001, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.MILLILITRE);
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
    void testVolumeConversion_MillilitreToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
        assertEquals(1.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_GallonToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_LitreToGallon() {
        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);
        assertEquals(1.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_MillilitreToGallon() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.GALLON);
        assertEquals(0.264172, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_SameUnit() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_ZeroValue() {
        Quantity<VolumeUnit> result = new Quantity<>(0.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion_NegativeValue() {
        Quantity<VolumeUnit> result = new Quantity<>(-1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(-1000.0, result.getValue(), EPSILON);
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
    void testVolumeAddition_SameUnit_LitrePlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(2.0, VolumeUnit.LITRE));
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void testVolumeAddition_SameUnit_MillilitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(1000.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void testVolumeAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_CrossUnit_MillilitrePlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .add(new Quantity<>(1.0, VolumeUnit.LITRE));
        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_CrossUnit_GallonPlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
                .add(new Quantity<>(3.78541, VolumeUnit.LITRE));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_WithZero() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_NegativeValues() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_NullOperand() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertThrows(IllegalArgumentException.class, () -> v1.add(null));
    }

    @Test
    void testVolumeAddition_ExplicitTargetUnit_Litre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void testVolumeAddition_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
        assertEquals(2000.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    void testVolumeAddition_ExplicitTargetUnit_Gallon() {
        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE)
                .add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.GALLON, result.getUnit());
    }

    @Test
    void testVolumeAddition_Commutativity() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> r1 = v1.add(v2);
        Quantity<VolumeUnit> r2 = v2.add(v1);
        assertTrue(r1.equals(r2));
    }

    // ==================== Volume Demonstration Tests ====================

    @Test
    void testQuantityMeasurementApp_demonstrateEquality_Volume() {
        assertTrue(QuantityMeasurementApp.demonstrateEquality(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testQuantityMeasurementApp_demonstrateConversion_Volume() {
        Quantity<VolumeUnit> result = QuantityMeasurementApp.demonstrateConversion(
                new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateAddition_Volume() {
        Quantity<VolumeUnit> result = QuantityMeasurementApp.demonstrateAddition(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                VolumeUnit.LITRE);
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
    }

}