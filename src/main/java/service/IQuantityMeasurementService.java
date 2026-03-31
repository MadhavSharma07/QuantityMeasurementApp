package service;

import entity.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO source, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO divide(QuantityDTO q1, QuantityDTO q2);
}