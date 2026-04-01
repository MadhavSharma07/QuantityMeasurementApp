package com.app.quantitymeasurementapp.service;

import com.app.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.app.quantitymeasurementapp.model.*;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurementapp.unit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // ==================== compare ====================

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = baseDTO("compare", thisQty, thatQty);
        try {
            @SuppressWarnings("rawtypes")
            boolean result = toQuantity(thisQty).equals(toQuantity(thatQty));
            dto.setResultString(String.valueOf(result));
            dto.setResultValue(0.0);
        } catch (Exception e) {
            setError(dto, e.getMessage());
        }
        repository.save(dto.toEntity());
        return dto;
    }

    // ==================== convert ====================

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = baseDTO("convert", thisQty, thatQty);
        try {
            validateSameCategory(thisQty, thatQty, "convert");
            IMeasurable targetUnit = resolveUnit(thatQty.getMeasurementType(), thatQty.getUnit());
            double base   = resolveUnit(thisQty.getMeasurementType(), thisQty.getUnit())
                                .convertToBaseUnit(thisQty.getValue());
            double result = targetUnit.convertFromBaseUnit(base);
            dto.setResultValue(result);
            dto.setResultUnit(thatQty.getUnit());
            dto.setResultMeasurementType(thisQty.getMeasurementType());
        } catch (Exception e) {
            setError(dto, e.getMessage());
        }
        repository.save(dto.toEntity());
        return dto;
    }

    // ==================== add ====================

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = baseDTO("add", thisQty, thatQty);
        try {
            validateSameCategory(thisQty, thatQty, "add");
            @SuppressWarnings("rawtypes")
            Quantity result = toQuantity(thisQty).add(toQuantity(thatQty));
            dto.setResultValue(result.getValue());
            dto.setResultUnit(((IMeasurable) result.getUnit()).getUnitName());
            dto.setResultMeasurementType(thisQty.getMeasurementType());
        } catch (Exception e) {
            setError(dto, "add Error: " + e.getMessage());
        }
        repository.save(dto.toEntity());
        return dto;
    }

    // ==================== subtract ====================

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = baseDTO("subtract", thisQty, thatQty);
        try {
            validateSameCategory(thisQty, thatQty, "subtract");
            @SuppressWarnings("rawtypes")
            Quantity result = toQuantity(thisQty).subtract(toQuantity(thatQty));
            dto.setResultValue(result.getValue());
            dto.setResultUnit(((IMeasurable) result.getUnit()).getUnitName());
            dto.setResultMeasurementType(thisQty.getMeasurementType());
        } catch (Exception e) {
            setError(dto, "subtract Error: " + e.getMessage());
        }
        repository.save(dto.toEntity());
        return dto;
    }

    // ==================== divide ====================

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = baseDTO("divide", thisQty, thatQty);
        try {
            validateSameCategory(thisQty, thatQty, "divide");
            @SuppressWarnings("rawtypes")
            double result = toQuantity(thisQty).divide(toQuantity(thatQty));
            dto.setResultValue(result);
            dto.setResultMeasurementType("DIMENSIONLESS");
        } catch (ArithmeticException e) {
            throw new QuantityMeasurementException("Divide by zero");
        } catch (Exception e) {
            setError(dto, "divide Error: " + e.getMessage());
        }
        repository.save(dto.toEntity());
        return dto;
    }

    // ==================== History & Count ====================

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByOperation(operation.toLowerCase()));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByThisMeasurementType(measurementType));
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByErrorTrue());
    }

    @Override
    public long countByOperation(String operation) {
        return repository.countByOperationAndErrorFalse(operation.toLowerCase());
    }

    // ==================== Private helpers ====================

    private QuantityMeasurementDTO baseDTO(String operation, QuantityDTO thisQty, QuantityDTO thatQty) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setOperation(operation);
        dto.setThisValue(thisQty.getValue());
        dto.setThisUnit(thisQty.getUnit());
        dto.setThisMeasurementType(thisQty.getMeasurementType());
        dto.setThatValue(thatQty.getValue());
        dto.setThatUnit(thatQty.getUnit());
        dto.setThatMeasurementType(thatQty.getMeasurementType());
        return dto;
    }

    private void setError(QuantityMeasurementDTO dto, String message) {
        dto.setError(true);
        dto.setErrorMessage(message);
    }

    /**
     * Returns a raw Quantity whose unit is always a real enum constant at runtime,
     * satisfying the <U extends Enum<U> & IMeasurable> bound dynamically.
     * The raw type suppression is safe because resolveUnit() guarantees an enum.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Quantity toQuantity(QuantityDTO dto) {
        Enum unit = (Enum) resolveUnit(dto.getMeasurementType(), dto.getUnit());
        return new Quantity(dto.getValue(), unit);
    }

    private IMeasurable resolveUnit(String measurementType, String unitName) {
        try {
            return switch (measurementType) {
                case "LengthUnit"      -> LengthUnit.valueOf(unitName.toUpperCase());
                case "WeightUnit"      -> WeightUnit.valueOf(unitName.toUpperCase());
                case "VolumeUnit"      -> VolumeUnit.valueOf(unitName.toUpperCase());
                case "TemperatureUnit" -> TemperatureUnit.valueOf(unitName.toUpperCase());
                default -> throw new QuantityMeasurementException(
                        "Unknown measurement type: " + measurementType);
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException(
                    "Unknown unit '" + unitName + "' for type " + measurementType);
        }
    }

    private void validateSameCategory(QuantityDTO q1, QuantityDTO q2, String op) {
        if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
            throw new QuantityMeasurementException(
                    "Cannot perform " + op + " between different measurement categories: "
                    + q1.getMeasurementType() + " and " + q2.getMeasurementType());
    }
}