package com.app.quantitymeasurementapp.service;

import com.app.quantitymeasurementapp.entity.QuantityDTO;
import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurementapp.entity.QuantityModel;
import com.app.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.app.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurementapp.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) throw new IllegalArgumentException("Repository must not be null");
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized with {}", repository.getClass().getSimpleName());
    }

    // ==================== compare ====================

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        validateNotNull(q1, q2, "COMPARE");
        try {
            boolean result = toQuantity(q1).equals(toQuantity(q2));
            repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, result));
            logger.debug("COMPARE {} == {} => {}", q1, q2, result);
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, e.getMessage()));
            throw e;
        } catch (Exception e) {
            String msg = "COMPARE failed: " + e.getMessage();
            repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, msg));
            throw new QuantityMeasurementException(msg, e);
        }
    }

    // ==================== convert ====================

    @Override
    public QuantityDTO convert(QuantityDTO source, QuantityDTO targetUnitDTO) {
        if (source == null || targetUnitDTO == null)
            throw new QuantityMeasurementException("Null input is not allowed for CONVERT");
        try {
            QuantityDTO result = convertInternal(source, targetUnitDTO);
            repository.save(new QuantityMeasurementEntity("CONVERT", source, result));
            logger.debug("CONVERT {} => {}", source, result);
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity("CONVERT", source, null, e.getMessage()));
            throw e;
        } catch (Exception e) {
            String msg = "CONVERT failed: " + e.getMessage();
            repository.save(new QuantityMeasurementEntity("CONVERT", source, null, msg));
            throw new QuantityMeasurementException(msg, e);
        }
    }

    // ==================== add ====================

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        validateNotNull(q1, q2, "ADD");
        try {
            QuantityDTO result = addInternal(q1, q2);
            repository.save(new QuantityMeasurementEntity("ADD", q1, q2, result));
            logger.debug("ADD {} + {} => {}", q1, q2, result);
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity("ADD", q1, q2, e.getMessage()));
            throw e;
        } catch (UnsupportedOperationException e) {
            repository.save(new QuantityMeasurementEntity("ADD", q1, q2, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage(), e);
        } catch (Exception e) {
            String msg = "ADD failed: " + e.getMessage();
            repository.save(new QuantityMeasurementEntity("ADD", q1, q2, msg));
            throw new QuantityMeasurementException(msg, e);
        }
    }

    // ==================== subtract ====================

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        validateNotNull(q1, q2, "SUBTRACT");
        try {
            QuantityDTO result = subtractInternal(q1, q2);
            repository.save(new QuantityMeasurementEntity("SUBTRACT", q1, q2, result));
            logger.debug("SUBTRACT {} - {} => {}", q1, q2, result);
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity("SUBTRACT", q1, q2, e.getMessage()));
            throw e;
        } catch (UnsupportedOperationException e) {
            repository.save(new QuantityMeasurementEntity("SUBTRACT", q1, q2, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage(), e);
        } catch (Exception e) {
            String msg = "SUBTRACT failed: " + e.getMessage();
            repository.save(new QuantityMeasurementEntity("SUBTRACT", q1, q2, msg));
            throw new QuantityMeasurementException(msg, e);
        }
    }

    // ==================== divide ====================

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {
        validateNotNull(q1, q2, "DIVIDE");
        try {
            QuantityDTO result = divideInternal(q1, q2);
            repository.save(new QuantityMeasurementEntity("DIVIDE", q1, q2, result));
            logger.debug("DIVIDE {} / {} => {}", q1, q2, result);
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity("DIVIDE", q1, q2, e.getMessage()));
            throw e;
        } catch (UnsupportedOperationException | ArithmeticException e) {
            repository.save(new QuantityMeasurementEntity("DIVIDE", q1, q2, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage(), e);
        } catch (Exception e) {
            String msg = "DIVIDE failed: " + e.getMessage();
            repository.save(new QuantityMeasurementEntity("DIVIDE", q1, q2, msg));
            throw new QuantityMeasurementException(msg, e);
        }
    }

    // ==================== Internal operation helpers ====================

    @SuppressWarnings("unchecked")
    private QuantityDTO convertInternal(QuantityDTO source, QuantityDTO target) {
        validateSameCategory(source, target, "CONVERT");
        String type = source.getUnit().getMeasurementType();
        switch (type) {
            case "LENGTH": {
                LengthUnit t = LengthUnit.valueOf(target.getUnit().getUnitName());
                Quantity<LengthUnit> r = toModel(source, LengthUnit.class).toQuantity().convertTo(t);
                return new QuantityDTO(r.getValue(), dtoLengthUnit(r.getUnit()));
            }
            case "WEIGHT": {
                WeightUnit t = WeightUnit.valueOf(target.getUnit().getUnitName());
                Quantity<WeightUnit> r = toModel(source, WeightUnit.class).toQuantity().convertTo(t);
                return new QuantityDTO(r.getValue(), dtoWeightUnit(r.getUnit()));
            }
            case "VOLUME": {
                VolumeUnit t = VolumeUnit.valueOf(target.getUnit().getUnitName());
                Quantity<VolumeUnit> r = toModel(source, VolumeUnit.class).toQuantity().convertTo(t);
                return new QuantityDTO(r.getValue(), dtoVolumeUnit(r.getUnit()));
            }
            case "TEMPERATURE": {
                TemperatureUnit t = TemperatureUnit.valueOf(target.getUnit().getUnitName());
                Quantity<TemperatureUnit> r = toModel(source, TemperatureUnit.class).toQuantity().convertTo(t);
                return new QuantityDTO(r.getValue(), dtoTemperatureUnit(r.getUnit()));
            }
            default: throw new QuantityMeasurementException("Unknown measurement type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    private QuantityDTO addInternal(QuantityDTO q1, QuantityDTO q2) {
        validateSameCategory(q1, q2, "ADD");
        String type = q1.getUnit().getMeasurementType();
        switch (type) {
            case "LENGTH": {
                Quantity<LengthUnit> r = toModel(q1, LengthUnit.class).toQuantity().add(toModel(q2, LengthUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoLengthUnit(r.getUnit()));
            }
            case "WEIGHT": {
                Quantity<WeightUnit> r = toModel(q1, WeightUnit.class).toQuantity().add(toModel(q2, WeightUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoWeightUnit(r.getUnit()));
            }
            case "VOLUME": {
                Quantity<VolumeUnit> r = toModel(q1, VolumeUnit.class).toQuantity().add(toModel(q2, VolumeUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoVolumeUnit(r.getUnit()));
            }
            case "TEMPERATURE":
                throw new QuantityMeasurementException("Temperature does not support ADD because temperature values are absolute points on a scale not additive quantities.");
            default: throw new QuantityMeasurementException("Unknown type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    private QuantityDTO subtractInternal(QuantityDTO q1, QuantityDTO q2) {
        validateSameCategory(q1, q2, "SUBTRACT");
        String type = q1.getUnit().getMeasurementType();
        switch (type) {
            case "LENGTH": {
                Quantity<LengthUnit> r = toModel(q1, LengthUnit.class).toQuantity().subtract(toModel(q2, LengthUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoLengthUnit(r.getUnit()));
            }
            case "WEIGHT": {
                Quantity<WeightUnit> r = toModel(q1, WeightUnit.class).toQuantity().subtract(toModel(q2, WeightUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoWeightUnit(r.getUnit()));
            }
            case "VOLUME": {
                Quantity<VolumeUnit> r = toModel(q1, VolumeUnit.class).toQuantity().subtract(toModel(q2, VolumeUnit.class).toQuantity());
                return new QuantityDTO(r.getValue(), dtoVolumeUnit(r.getUnit()));
            }
            case "TEMPERATURE":
                throw new QuantityMeasurementException("Temperature does not support SUBTRACT.");
            default: throw new QuantityMeasurementException("Unknown type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    private QuantityDTO divideInternal(QuantityDTO q1, QuantityDTO q2) {
        validateSameCategory(q1, q2, "DIVIDE");
        String type = q1.getUnit().getMeasurementType();
        double ratio;
        QuantityDTO.IMeasurableUnit resultUnit;
        switch (type) {
            case "LENGTH":
                ratio      = toModel(q1, LengthUnit.class).toQuantity().divide(toModel(q2, LengthUnit.class).toQuantity());
                resultUnit = dtoLengthUnit(LengthUnit.valueOf(q1.getUnit().getUnitName())); break;
            case "WEIGHT":
                ratio      = toModel(q1, WeightUnit.class).toQuantity().divide(toModel(q2, WeightUnit.class).toQuantity());
                resultUnit = dtoWeightUnit(WeightUnit.valueOf(q1.getUnit().getUnitName())); break;
            case "VOLUME":
                ratio      = toModel(q1, VolumeUnit.class).toQuantity().divide(toModel(q2, VolumeUnit.class).toQuantity());
                resultUnit = dtoVolumeUnit(VolumeUnit.valueOf(q1.getUnit().getUnitName())); break;
            case "TEMPERATURE":
                throw new QuantityMeasurementException("Temperature does not support DIVIDE.");
            default:
                throw new QuantityMeasurementException("Unknown type: " + type);
        }
        return new QuantityDTO(ratio, resultUnit);
    }

    // ==================== Private conversion helpers ====================

    private Quantity<?> toQuantity(QuantityDTO dto) {
        return toModelRaw(dto).toQuantity();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private QuantityModel toModelRaw(QuantityDTO dto) {
        if (dto == null || dto.getUnit() == null)
            throw new QuantityMeasurementException("QuantityDTO and its unit must not be null");
        String type     = dto.getUnit().getMeasurementType();
        String unitName = dto.getUnit().getUnitName();
        try {
            return switch (type) {
                case "LENGTH"      -> new QuantityModel<>(dto.getValue(), LengthUnit.valueOf(unitName));
                case "WEIGHT"      -> new QuantityModel<>(dto.getValue(), WeightUnit.valueOf(unitName));
                case "VOLUME"      -> new QuantityModel<>(dto.getValue(), VolumeUnit.valueOf(unitName));
                case "TEMPERATURE" -> new QuantityModel<>(dto.getValue(), TemperatureUnit.valueOf(unitName));
                default -> throw new QuantityMeasurementException("Unsupported measurement type: " + type);
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException("Unknown unit '" + unitName + "' for type " + type, e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <U extends Enum<U> & com.app.quantitymeasurementapp.unit.IMeasurable> QuantityModel<U> toModel(QuantityDTO dto, Class<U> clazz) {
        return (QuantityModel<U>) toModelRaw(dto);
    }

    private QuantityDTO.LengthUnit      dtoLengthUnit(LengthUnit u)           { return QuantityDTO.LengthUnit.valueOf(u.name()); }
    private QuantityDTO.WeightUnit      dtoWeightUnit(WeightUnit u)           { return QuantityDTO.WeightUnit.valueOf(u.name()); }
    private QuantityDTO.VolumeUnit      dtoVolumeUnit(VolumeUnit u)           { return QuantityDTO.VolumeUnit.valueOf(u.name()); }
    private QuantityDTO.TemperatureUnit dtoTemperatureUnit(TemperatureUnit u) { return QuantityDTO.TemperatureUnit.valueOf(u.name()); }

    private void validateNotNull(QuantityDTO q1, QuantityDTO q2, String op) {
        if (q1 == null || q2 == null)
            throw new QuantityMeasurementException("Null operand is not allowed for " + op);
    }

    private void validateSameCategory(QuantityDTO q1, QuantityDTO q2, String op) {
        String t1 = q1.getUnit().getMeasurementType();
        String t2 = q2.getUnit().getMeasurementType();
        if (!t1.equals(t2))
            throw new QuantityMeasurementException("Cannot " + op + " different measurement categories: " + t1 + " vs " + t2);
    }
}
