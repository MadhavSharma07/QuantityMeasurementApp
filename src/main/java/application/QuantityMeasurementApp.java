package application;

import controller.QuantityMeasurementController;
import entity.QuantityDTO;
import service.IQuantityMeasurementService;
import service.QuantityMeasurementService;

/**
 * Application entry point.
 * Creates service and controller, then delegates all operations to the controller.
 */
public class QuantityMeasurementApp {

    // -------- Factory methods --------

    public static IQuantityMeasurementService createService() {
        return new QuantityMeasurementService();
    }

    public static QuantityMeasurementController createController(IQuantityMeasurementService service) {
        return new QuantityMeasurementController(service);
    }

    // -------- Helper --------

    private static QuantityDTO dto(double value, String unit, String type) {
        return new QuantityDTO(value, unit, type);
    }

    // ==================== Main ====================

    public static void main(String[] args) {

        IQuantityMeasurementService   service    = createService();
        QuantityMeasurementController controller = createController(service);

        // ===== LENGTH =====
        System.out.println("\n=== LENGTH COMPARISONS ===");
        controller.performComparison(dto(1.0, "FEET", "LENGTH"), dto(12.0, "INCHES", "LENGTH"));
        controller.performComparison(dto(1.0, "YARDS", "LENGTH"), dto(36.0, "INCHES", "LENGTH"));
        controller.performComparison(dto(3.0, "FEET", "LENGTH"), dto(1.0,  "YARDS",  "LENGTH"));

        System.out.println("\n=== LENGTH CONVERSIONS ===");
        controller.performConversion(dto(1.0, "FEET",       "LENGTH"), "INCHES");
        controller.performConversion(dto(3.0, "YARDS",      "LENGTH"), "FEET");
        controller.performConversion(dto(1.0, "CENTIMETER", "LENGTH"), "INCHES");

        System.out.println("\n=== LENGTH ADDITION ===");
        controller.performAddition(dto(1.0, "FEET", "LENGTH"), dto(12.0, "INCHES", "LENGTH"));
        controller.performAddition(dto(1.0, "FEET", "LENGTH"), dto(12.0, "INCHES", "LENGTH"), "INCHES");

        System.out.println("\n=== LENGTH SUBTRACTION ===");
        controller.performSubtraction(dto(10.0, "FEET", "LENGTH"), dto(6.0, "INCHES", "LENGTH"));
        controller.performSubtraction(dto(10.0, "FEET", "LENGTH"), dto(6.0, "INCHES", "LENGTH"), "INCHES");

        System.out.println("\n=== LENGTH DIVISION ===");
        controller.performDivision(dto(10.0, "FEET",   "LENGTH"), dto(2.0, "FEET", "LENGTH"));
        controller.performDivision(dto(24.0, "INCHES", "LENGTH"), dto(2.0, "FEET", "LENGTH"));

        // ===== WEIGHT =====
        System.out.println("\n=== WEIGHT COMPARISONS ===");
        controller.performComparison(dto(1.0, "KILOGRAM", "WEIGHT"), dto(1000.0, "GRAM",     "WEIGHT"));
        controller.performComparison(dto(1.0, "TONNE",    "WEIGHT"), dto(1000.0, "KILOGRAM", "WEIGHT"));

        System.out.println("\n=== WEIGHT CONVERSIONS ===");
        controller.performConversion(dto(1.0,    "KILOGRAM", "WEIGHT"), "GRAM");
        controller.performConversion(dto(2000.0, "GRAM",     "WEIGHT"), "KILOGRAM");

        System.out.println("\n=== WEIGHT ADDITION ===");
        controller.performAddition(dto(1.0, "KILOGRAM", "WEIGHT"), dto(500.0, "GRAM", "WEIGHT"));
        controller.performAddition(dto(1.0, "KILOGRAM", "WEIGHT"), dto(500.0, "GRAM", "WEIGHT"), "GRAM");

        System.out.println("\n=== WEIGHT SUBTRACTION ===");
        controller.performSubtraction(dto(10.0, "KILOGRAM", "WEIGHT"), dto(5000.0, "GRAM", "WEIGHT"));

        System.out.println("\n=== WEIGHT DIVISION ===");
        controller.performDivision(dto(10.0, "KILOGRAM", "WEIGHT"), dto(5.0, "KILOGRAM", "WEIGHT"));

        // ===== VOLUME =====
        System.out.println("\n=== VOLUME COMPARISONS ===");
        controller.performComparison(dto(1.0, "LITRE",  "VOLUME"), dto(1000.0,  "MILLILITRE", "VOLUME"));
        controller.performComparison(dto(1.0, "GALLON", "VOLUME"), dto(3.78541, "LITRE",      "VOLUME"));

        System.out.println("\n=== VOLUME CONVERSIONS ===");
        controller.performConversion(dto(1.0, "LITRE",  "VOLUME"), "MILLILITRE");
        controller.performConversion(dto(1.0, "GALLON", "VOLUME"), "LITRE");

        System.out.println("\n=== VOLUME ADDITION ===");
        controller.performAddition(dto(1.0, "LITRE", "VOLUME"), dto(1000.0, "MILLILITRE", "VOLUME"));

        System.out.println("\n=== VOLUME SUBTRACTION ===");
        controller.performSubtraction(dto(5.0, "LITRE", "VOLUME"), dto(500.0, "MILLILITRE", "VOLUME"));

        System.out.println("\n=== VOLUME DIVISION ===");
        controller.performDivision(dto(1000.0, "MILLILITRE", "VOLUME"), dto(1.0, "LITRE", "VOLUME"));

        // ===== TEMPERATURE =====
        System.out.println("\n=== TEMPERATURE COMPARISONS ===");
        controller.performComparison(dto(0.0,   "CELSIUS",    "TEMPERATURE"), dto(32.0,  "FAHRENHEIT", "TEMPERATURE"));
        controller.performComparison(dto(100.0, "CELSIUS",    "TEMPERATURE"), dto(212.0, "FAHRENHEIT", "TEMPERATURE"));
        controller.performComparison(dto(-40.0, "CELSIUS",    "TEMPERATURE"), dto(-40.0, "FAHRENHEIT", "TEMPERATURE"));

        System.out.println("\n=== TEMPERATURE CONVERSIONS ===");
        controller.performConversion(dto(100.0, "CELSIUS",    "TEMPERATURE"), "FAHRENHEIT");
        controller.performConversion(dto(32.0,  "FAHRENHEIT", "TEMPERATURE"), "CELSIUS");


        System.out.println("\n=== CROSS-CATEGORY PREVENTION (expected false/errors) ===");
        controller.performComparison(dto(100.0, "CELSIUS",  "TEMPERATURE"), dto(100.0, "FEET",     "LENGTH"));
        controller.performComparison(dto(100.0, "CELSIUS",  "TEMPERATURE"), dto(100.0, "KILOGRAM", "WEIGHT"));
    }
}