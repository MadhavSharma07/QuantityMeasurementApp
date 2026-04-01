package com.app.quantitymeasurementapp.controller;

import com.app.quantitymeasurementapp.model.QuantityInputDTO;
import com.app.quantitymeasurementapp.model.QuantityMeasurementDTO;
import com.app.quantitymeasurementapp.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // ==================== POST endpoints ====================

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.compare(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.convert(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.add(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.subtract(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.divide(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    // ==================== GET endpoints ====================

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get measurement history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByOperation(
            @PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get measurement history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(
            @PathVariable String measurementType) {
        return ResponseEntity.ok(service.getHistoryByType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get all errored measurements")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of successful operations by type")
    public ResponseEntity<Long> countByOperation(@PathVariable String operation) {
        return ResponseEntity.ok(service.countByOperation(operation));
    }
}
