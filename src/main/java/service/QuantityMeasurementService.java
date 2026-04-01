package service;

import entity.IMeasurable;
import entity.LengthUnit;
import entity.Quantity;
import entity.QuantityDTO;
import entity.TemperatureUnit;
import entity.VolumeUnit;
import entity.WeightUnit;
import exception.QuantityMeasurementException;


public class QuantityMeasurementService implements IQuantityMeasurementService {

    // ==================== IQuantityMeasurementService ====================

    @Override
    public QuantityDTO compare(QuantityDTO q1, QuantityDTO q2) {
        validate(q1);
        validate(q2);
        Quantity<IMeasurable> qty1 = toQuantity(q1);
        Quantity<IMeasurable> qty2 = toQuantity(q2);
        boolean result = qty1.equals(qty2);
        return new QuantityDTO(result ? 1.0 : 0.0, String.valueOf(result), "BOOLEAN");
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, String targetUnitName) {
        validate(source);
        if (targetUnitName == null || targetUnitName.isEmpty())
            throw new QuantityMeasurementException("Target unit cannot be empty");

        Quantity<IMeasurable> qty        = toQuantity(source);
        IMeasurable           targetUnit = resolveUnit(source.getMeasurementType(), targetUnitName);
        Quantity<IMeasurable> result     = qty.convertTo(targetUnit);

        return new QuantityDTO(result.getValue(), targetUnit.getUnitName(), source.getMeasurementType());
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnitName) {
        return binaryOp("ADD", q1, q2, targetUnitName);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnitName) {
        return binaryOp("SUBTRACT", q1, q2, targetUnitName);
    }

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {
        validate(q1);
        validate(q2);
        try {
            Quantity<IMeasurable> qty1   = toQuantity(q1);
            Quantity<IMeasurable> qty2   = toQuantity(q2);
            double                result = qty1.divide(qty2);
            return new QuantityDTO(result, "SCALAR", "DIMENSIONLESS");
        } catch (UnsupportedOperationException | ArithmeticException e) {
            throw new QuantityMeasurementException(e.getMessage(), e);
        }
    }

    // ==================== Private Helpers ====================

    private QuantityDTO binaryOp(String op, QuantityDTO q1, QuantityDTO q2, String targetUnitName) {
        validate(q1);
        validate(q2);
        try {
            Quantity<IMeasurable> qty1   = toQuantity(q1);
            Quantity<IMeasurable> qty2   = toQuantity(q2);
            Quantity<IMeasurable> result;

            if (targetUnitName != null && !targetUnitName.isEmpty()) {
                IMeasurable targetUnit = resolveUnit(q1.getMeasurementType(), targetUnitName);
                result = op.equals("ADD") ? qty1.add(qty2, targetUnit)
                                          : qty1.subtract(qty2, targetUnit);
            } else {
                result = op.equals("ADD") ? qty1.add(qty2) : qty1.subtract(qty2);
            }

            return new QuantityDTO(result.getValue(),
                    result.getUnit().getUnitName(), q1.getMeasurementType());

        } catch (UnsupportedOperationException e) {
            throw new QuantityMeasurementException(e.getMessage(), e);
        }
    }

    /** Converts a QuantityDTO into a Quantity<IMeasurable> for operations */
    @SuppressWarnings("unchecked")
    private Quantity<IMeasurable> toQuantity(QuantityDTO dto) {
        IMeasurable unit = resolveUnit(dto.getMeasurementType(), dto.getUnitName());
        return new Quantity<>(dto.getValue(), unit);
    }

    /** Resolves measurement type + unit name to an IMeasurable constant */
    private IMeasurable resolveUnit(String type, String unitName) {
        IMeasurable unit = null;
        switch (type.toUpperCase()) {
            case "LENGTH":      unit = LengthUnit.FEET.fromUnitName(unitName);         break;
            case "WEIGHT":      unit = WeightUnit.GRAM.fromUnitName(unitName);         break;
            case "VOLUME":      unit = VolumeUnit.LITRE.fromUnitName(unitName);        break;
            case "TEMPERATURE": unit = TemperatureUnit.CELSIUS.fromUnitName(unitName); break;
            default: throw new QuantityMeasurementException("Unknown type: " + type);
        }
        if (unit == null)
            throw new QuantityMeasurementException("Unknown unit '" + unitName + "' for type " + type);
        return unit;
    }

    private void validate(QuantityDTO dto) {
        if (dto == null)
            throw new QuantityMeasurementException("QuantityDTO cannot be null");
        if (dto.getUnitName() == null || dto.getUnitName().isEmpty())
            throw new QuantityMeasurementException("Unit name cannot be empty");
        if (dto.getMeasurementType() == null || dto.getMeasurementType().isEmpty())
            throw new QuantityMeasurementException("Measurement type cannot be empty");
    }
}