package com.app.quantitymeasurementapp.application;

import com.app.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.app.quantitymeasurementapp.entity.QuantityDTO;
import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.app.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurementapp.util.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuantityMeasurementApp {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementApp.class);

    private final IQuantityMeasurementRepository repository;
    private final IQuantityMeasurementService    service;
    private final QuantityMeasurementController  controller;

    public QuantityMeasurementApp() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        logger.info("Starting QuantityMeasurementApp v{}", config.get("app.version", "1.0"));
        logger.info("Repository type: {}", config.getRepositoryType());

        this.repository = createRepository(config);
        this.service    = new QuantityMeasurementServiceImpl(repository);
        this.controller = new QuantityMeasurementController(service);

        logger.info("App initialized. Pool stats: {}", repository.getPoolStatistics());
    }

    // -------- Factory --------

    private IQuantityMeasurementRepository createRepository(ApplicationConfig config) {
        String repoType = config.getRepositoryType();
        if ("database".equalsIgnoreCase(repoType)) {
            logger.info("Creating QuantityMeasurementDatabaseRepository");
            return new QuantityMeasurementDatabaseRepository(config);
        }
        logger.info("Creating QuantityMeasurementCacheRepository");
        return QuantityMeasurementCacheRepository.getInstance();
    }

    // -------- Utility --------

    public void deleteAllMeasurements() {
        repository.deleteAll();
        logger.info("All measurements deleted. Total count: {}", repository.getTotalCount());
    }

    public void closeResources() {
        repository.releaseResources();
        logger.info("Resources released");
    }

    public IQuantityMeasurementRepository getRepository() { return repository; }

    // ==================== Main ====================

    public static void main(String[] args) {

        QuantityMeasurementApp app = new QuantityMeasurementApp();
        QuantityMeasurementController c = app.controller;

        logger.info("=== LENGTH COMPARISONS ===");
        c.performCompare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),   new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        c.performCompare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS),  new QuantityDTO(3.0,  QuantityDTO.LengthUnit.FEET));
        c.performCompare(new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET),   new QuantityDTO(2.0,  QuantityDTO.LengthUnit.INCHES));

        logger.info("=== LENGTH CONVERSIONS ===");
        c.performConvert(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),   new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
        c.performConvert(new QuantityDTO(3.0, QuantityDTO.LengthUnit.YARDS),  new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

        logger.info("=== LENGTH ADDITION ===");
        c.performAdd(new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),      new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

        logger.info("=== LENGTH SUBTRACTION ===");
        c.performSubtract(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET), new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES));

        logger.info("=== LENGTH DIVISION ===");
        c.performDivide(new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES), new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

        logger.info("=== WEIGHT ===");
        c.performCompare(new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));
        c.performConvert(new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(0.0,    QuantityDTO.WeightUnit.GRAM));
        c.performAdd    (new QuantityDTO(10.0,   QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(5000.0, QuantityDTO.WeightUnit.GRAM));
        c.performSubtract(new QuantityDTO(10.0,  QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(5000.0, QuantityDTO.WeightUnit.GRAM));
        c.performDivide (new QuantityDTO(10.0,   QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(5.0,    QuantityDTO.WeightUnit.KILOGRAM));

        logger.info("=== VOLUME ===");
        c.performCompare(new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE),       new QuantityDTO(1000.0,  QuantityDTO.VolumeUnit.MILLILITRE));
        c.performConvert(new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE),       new QuantityDTO(0.0,     QuantityDTO.VolumeUnit.MILLILITRE));
        c.performAdd    (new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE),       new QuantityDTO(500.0,   QuantityDTO.VolumeUnit.MILLILITRE));
        c.performSubtract(new QuantityDTO(5.0,   QuantityDTO.VolumeUnit.LITRE),       new QuantityDTO(500.0,   QuantityDTO.VolumeUnit.MILLILITRE));
        c.performDivide (new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE),  new QuantityDTO(1.0,     QuantityDTO.VolumeUnit.LITRE));

        logger.info("=== TEMPERATURE COMPARISONS ===");
        c.performCompare(new QuantityDTO(0.0,    QuantityDTO.TemperatureUnit.CELSIUS),    new QuantityDTO(32.0,  QuantityDTO.TemperatureUnit.FAHRENHEIT));
        c.performCompare(new QuantityDTO(100.0,  QuantityDTO.TemperatureUnit.CELSIUS),    new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        c.performCompare(new QuantityDTO(273.15, QuantityDTO.TemperatureUnit.KELVIN),     new QuantityDTO(0.0,   QuantityDTO.TemperatureUnit.CELSIUS));

        logger.info("=== TEMPERATURE CONVERSIONS ===");
        c.performConvert(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),    new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        c.performConvert(new QuantityDTO(0.0,   QuantityDTO.TemperatureUnit.CELSIUS),    new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.KELVIN));

        logger.info("=== TEMPERATURE ARITHMETIC (expected errors) ===");
        c.performAdd(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS), new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS));

        logger.info("=== CROSS-CATEGORY PREVENTION (expected errors) ===");
        c.performAdd(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET), new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM));

        List<QuantityMeasurementEntity> history = app.repository.getAllMeasurements();
        logger.info("Total operations stored: {}", history.size());
        logger.info("Pool stats: {}", app.repository.getPoolStatistics());

        int start = Math.max(0, history.size() - 3);
        history.subList(start, history.size()).forEach(e -> logger.info("Record: {}", e));

        app.deleteAllMeasurements();
        app.closeResources();
    }
}
