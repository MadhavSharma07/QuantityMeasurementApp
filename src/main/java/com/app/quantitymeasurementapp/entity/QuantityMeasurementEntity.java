package com.app.quantitymeasurementapp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String        operationId;
    private final String        operationType;
    private final QuantityDTO   operand1;
    private final QuantityDTO   operand2;
    private final QuantityDTO   result;
    private final boolean       comparisonResult;
    private final boolean       hasError;
    private final String        errorMessage;
    private final LocalDateTime timestamp;

    // -------- Constructor: single-operand (CONVERT) --------
    public QuantityMeasurementEntity(String operationType, QuantityDTO operand1, QuantityDTO result) {
        this.operationId      = UUID.randomUUID().toString();
        this.operationType    = operationType;
        this.operand1         = operand1;
        this.operand2         = null;
        this.result           = result;
        this.comparisonResult = false;
        this.hasError         = false;
        this.errorMessage     = null;
        this.timestamp        = LocalDateTime.now();
    }

    // -------- Constructor: binary-operand (ADD / SUBTRACT / DIVIDE) --------
    public QuantityMeasurementEntity(String operationType, QuantityDTO operand1, QuantityDTO operand2, QuantityDTO result) {
        this.operationId      = UUID.randomUUID().toString();
        this.operationType    = operationType;
        this.operand1         = operand1;
        this.operand2         = operand2;
        this.result           = result;
        this.comparisonResult = false;
        this.hasError         = false;
        this.errorMessage     = null;
        this.timestamp        = LocalDateTime.now();
    }

    // -------- Constructor: comparison (COMPARE) --------
    public QuantityMeasurementEntity(String operationType, QuantityDTO operand1, QuantityDTO operand2, boolean comparisonResult) {
        this.operationId      = UUID.randomUUID().toString();
        this.operationType    = operationType;
        this.operand1         = operand1;
        this.operand2         = operand2;
        this.result           = null;
        this.comparisonResult = comparisonResult;
        this.hasError         = false;
        this.errorMessage     = null;
        this.timestamp        = LocalDateTime.now();
    }

    // -------- Constructor: error --------
    public QuantityMeasurementEntity(String operationType, QuantityDTO operand1, QuantityDTO operand2, String errorMessage) {
        this.operationId      = UUID.randomUUID().toString();
        this.operationType    = operationType;
        this.operand1         = operand1;
        this.operand2         = operand2;
        this.result           = null;
        this.comparisonResult = false;
        this.hasError         = true;
        this.errorMessage     = errorMessage;
        this.timestamp        = LocalDateTime.now();
    }

    // ==================== Getters ====================

    public String        getOperationId()      { return operationId; }
    public String        getOperationType()    { return operationType; }
    public QuantityDTO   getOperand1()         { return operand1; }
    public QuantityDTO   getOperand2()         { return operand2; }
    public QuantityDTO   getResult()           { return result; }
    public boolean       getComparisonResult() { return comparisonResult; }
    public boolean       hasError()            { return hasError; }
    public String        getErrorMessage()     { return errorMessage; }
    public LocalDateTime getTimestamp()        { return timestamp; }

    @Override
    public String toString() {
        if (hasError)
            return String.format("[%s] %-12s | ERROR: %s", timestamp, operationType, errorMessage);
        if ("COMPARE".equals(operationType))
            return String.format("[%s] %-12s | %s == %s => %b", timestamp, operationType, operand1, operand2, comparisonResult);
        if (operand2 == null)
            return String.format("[%s] %-12s | %s => %s", timestamp, operationType, operand1, result);
        return String.format("[%s] %-12s | %s , %s => %s", timestamp, operationType, operand1, operand2, result);
    }
}
