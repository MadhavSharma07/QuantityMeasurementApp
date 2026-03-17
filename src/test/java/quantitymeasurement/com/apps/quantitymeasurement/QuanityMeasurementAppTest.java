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

    // ==================== Weight Addition Tests ====================

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

    // ==================== Volume Tests ====================

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testVolumeConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    // ==================== UC12 Subtraction Tests ====================

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(-5.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
    }

    @Test
    void testSubtraction_CrossCategory() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> q2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2));
    }

    @Test
    void testSubtraction_Immutability() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other = new Quantity<>(3.0, LengthUnit.FEET);
        original.subtract(other);
        assertEquals(10.0, original.getValue(), EPSILON);
    }

    // ==================== UC12 Division Tests ====================

    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testDivision_ByZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

    @Test
    void testDivision_CrossCategory() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> q2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> q1.divide(q2));
    }

    // ==================== UC13 Validation Consistency ====================

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
    void testArithmetic_Chain_Operations() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q3 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q4 = new Quantity<>(3.0, LengthUnit.FEET);
        double result = q1.add(q2).subtract(q3).divide(q4);
        assertEquals(11.0 / 3.0, result, EPSILON);
    }

    // ==================== UC14: TemperatureUnit Enum Tests ====================

    @Test
    void testTemperatureUnit_ImplementsIMeasurable() {
        assertTrue(TemperatureUnit.CELSIUS instanceof IMeasurable);
        assertTrue(TemperatureUnit.FAHRENHEIT instanceof IMeasurable);
    }

    @Test
    void testTemperatureUnit_GetUnitName() {
        assertEquals("CELSIUS",    TemperatureUnit.CELSIUS.getUnitName());
        assertEquals("FAHRENHEIT", TemperatureUnit.FAHRENHEIT.getUnitName());
    }

    @Test
    void testTemperatureUnit_SupportsArithmetic_ReturnsFalse() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testTemperatureUnit_ValidateOperationSupport_ThrowsException() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("ADD"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("SUBTRACT"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("DIVIDE"));
    }

    // ==================== UC14: Temperature Equality Tests ====================

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_DifferentValues() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_NullComparison() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(t1.equals(null));
    }

    @Test
    void testTemperatureEquality_SameReference() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertTrue(t1.equals(t1));
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));
    }

    // ==================== UC14: Temperature Conversion Tests ====================

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_0C() {
        Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_100C() {
        Quantity<TemperatureUnit> result = new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_32F() {
        Quantity<TemperatureUnit> result = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_212F() {
        Quantity<TemperatureUnit> result = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_Negative40_EqualPoint() {
        Quantity<TemperatureUnit> result = new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(-40.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> result = new Quantity<>(50.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(50.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_NegativeValue() {
        Quantity<TemperatureUnit> result = new Quantity<>(-20.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(-4.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip() {
        Quantity<TemperatureUnit> result = new Quantity<>(37.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(37.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_LargeValue() {
        Quantity<TemperatureUnit> result = new Quantity<>(1000.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(1832.0, result.getValue(), EPSILON);
    }

    // ==================== UC14: Unsupported Operations Tests ====================

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.subtract(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.divide(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_AddWithTargetUnit() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class,
                () -> t1.add(t2, TemperatureUnit.CELSIUS));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,
                () -> t1.add(t2));
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().toLowerCase().contains("temperature"));
    }

    // ==================== UC14: Cross-Category Type Safety ====================

    @Test
    void testTemperatureVsLengthIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit>      len  = new Quantity<>(100.0, LengthUnit.FEET);
        assertFalse(temp.equals(len));
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<WeightUnit>      wgt  = new Quantity<>(50.0, WeightUnit.KILOGRAM);
        assertFalse(temp.equals(wgt));
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        Quantity<VolumeUnit>      vol  = new Quantity<>(25.0, VolumeUnit.LITRE);
        assertFalse(temp.equals(vol));
    }

    // ==================== UC14: Operation Support Methods ====================

    @Test
    void testOperationSupportMethods_LengthUnitSupportsArithmetic() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(LengthUnit.INCHES.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitSupportsArithmetic() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
        assertTrue(WeightUnit.GRAM.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_VolumeUnitSupportsArithmetic() {
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
        assertTrue(VolumeUnit.MILLILITRE.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_TemperatureDoesNotSupportArithmetic() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    // ==================== UC14: IMeasurable Backward Compatibility ====================

    @Test
    void testIMeasurableInterface_BackwardCompatible_LengthStillWorks() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testIMeasurableInterface_BackwardCompatible_WeightStillWorks() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(500.0, WeightUnit.GRAM));
        assertEquals(0.5, result.getValue(), EPSILON);
    }

    @Test
    void testIMeasurableInterface_BackwardCompatible_VolumeStillWorks() {
        double ratio = new Quantity<>(1.0, VolumeUnit.LITRE)
                .divide(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, ratio, EPSILON);
    }

    // ==================== UC14: Demonstration Method Tests ====================

    @Test
    void testQuantityMeasurementApp_demonstrateEquality_Temperature() {
        assertTrue(QuantityMeasurementApp.demonstrateEquality(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    void testQuantityMeasurementApp_demonstrateConversion_CelsiusToFahrenheit() {
        Quantity<TemperatureUnit> result = QuantityMeasurementApp.demonstrateConversion(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, result.getValue(), EPSILON);
    }

    @Test
    void testQuantityMeasurementApp_demonstrateComparison_Temperature() {
        assertTrue(QuantityMeasurementApp.demonstrateComparison(
                100.0, TemperatureUnit.CELSIUS, 212.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, (TemperatureUnit) null));
    }
}