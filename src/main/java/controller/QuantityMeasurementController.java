package controller;

import entity.QuantityDTO;
import service.IQuantityMeasurementService;


public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) throw new IllegalArgumentException("Service cannot be null");
        this.service = service;
    }

    // ==================== API Methods ====================

    public QuantityDTO performComparison(QuantityDTO q1, QuantityDTO q2) {
        try {
            QuantityDTO result = service.compare(q1, q2);
            System.out.println("[COMPARE] " + q1 + " vs " + q2 + " => " + result.getUnitName());
            return result;
        } catch (Exception e) {
            System.out.println("[COMPARE] ERROR: " + e.getMessage());
            return null;
        }
    }

    public QuantityDTO performConversion(QuantityDTO source, String targetUnit) {
        try {
            QuantityDTO result = service.convert(source, targetUnit);
            System.out.println("[CONVERT] " + source + " => " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[CONVERT] ERROR: " + e.getMessage());
            return null;
        }
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {
        return performAddition(q1, q2, null);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            QuantityDTO result = service.add(q1, q2, targetUnit);
            System.out.println("[ADD] " + q1 + " + " + q2 + " => " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[ADD] ERROR: " + e.getMessage());
            return null;
        }
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        return performSubtraction(q1, q2, null);
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            QuantityDTO result = service.subtract(q1, q2, targetUnit);
            System.out.println("[SUBTRACT] " + q1 + " - " + q2 + " => " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[SUBTRACT] ERROR: " + e.getMessage());
            return null;
        }
    }

    public QuantityDTO performDivision(QuantityDTO q1, QuantityDTO q2) {
        try {
            QuantityDTO result = service.divide(q1, q2);
            System.out.println("[DIVIDE] " + q1 + " / " + q2 + " => " + result.getValue());
            return result;
        } catch (Exception e) {
            System.out.println("[DIVIDE] ERROR: " + e.getMessage());
            return null;
        }
    }
}