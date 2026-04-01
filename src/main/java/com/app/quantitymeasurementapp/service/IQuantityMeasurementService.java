package com.app.quantitymeasurementapp.service;

import com.app.quantitymeasurementapp.model.QuantityDTO;
import com.app.quantitymeasurementapp.model.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare (QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityMeasurementDTO convert (QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityMeasurementDTO add     (QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityMeasurementDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityMeasurementDTO divide  (QuantityDTO thisQty, QuantityDTO thatQty);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByType(String measurementType);
    List<QuantityMeasurementDTO> getErrorHistory();
    long                         countByOperation(String operation);
}
