package com.app.quantitymeasurementapp.integrationTests;

import com.app.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.app.quantitymeasurementapp.entity.QuantityDTO;
import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.app.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurementapp.util.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementDatabaseRepository dbRepository;
    private IQuantityMeasurementService           service;
    private QuantityMeasurementController         controller;

    @Before
    public void setUp() {
        System.setProperty("db.url",           "jdbc:h2:mem:testdb_integ_" + System.nanoTime() + ";DB_CLOSE_DELAY=-1");
        System.setProperty("db.username",      "sa");
        System.setProperty("db.password",      "");
        System.setProperty("db.driver",        "org.h2.Driver");
        System.setProperty("pool.initialSize", "2");
        System.setProperty("pool.maxSize",     "5");
        ApplicationConfig config = ApplicationConfig.getInstance();
        dbRepository = new QuantityMeasurementDatabaseRepository(config);
        dbRepository.deleteAll();
        service    = new QuantityMeasurementServiceImpl(dbRepository);
        controller = new QuantityMeasurementController(service);
    }

    @After
    public void tearDown() {
        dbRepository.deleteAll();
        dbRepository.releaseResources();
    }

    // ==================== Compare ====================

    @Test
    public void testCompare_FeetEqualsInches_SavesToDB() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        boolean result = service.compare(q1, q2);
        assertTrue(result);
        assertEquals(1, dbRepository.getTotalCount());
    }

    @Test
    public void testCompare_TemperatureEquality_0C_32F() {
        QuantityDTO q1 = new QuantityDTO(0.0,  QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO q2 = new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertTrue(service.compare(q1, q2));
        assertEquals(1, dbRepository.getTotalCount());
    }

    @Test
    public void testCompare_KelvinToCelsius_PersistsRecord() {
        QuantityDTO q1 = new QuantityDTO(273.15, QuantityDTO.TemperatureUnit.KELVIN);
        QuantityDTO q2 = new QuantityDTO(0.0,    QuantityDTO.TemperatureUnit.CELSIUS);
        assertTrue(service.compare(q1, q2));
    }

    // ==================== Convert ====================

    @Test
    public void testConvert_CelsiusToFahrenheit_SavesToDB() {
        QuantityDTO source = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO target = new QuantityDTO(0.0,   QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO result = service.convert(source, target);
        assertEquals(212.0, result.getValue(), 1e-9);
        assertEquals(1, dbRepository.getTotalCount());
    }

    @Test
    public void testConvert_CelsiusToKelvin() {
        QuantityDTO source = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.KELVIN);
        QuantityDTO result = service.convert(source, target);
        assertEquals(273.15, result.getValue(), 1e-9);
    }

    // ==================== Add ====================

    @Test
    public void testAdd_FeetPlusInches_SavesToDB() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.add(q1, q2);
        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals(1, dbRepository.getTotalCount());
    }

    // ==================== Controller end-to-end ====================

    @Test
    public void testControllerEndToEnd_AllOperations_PersistAll() {
        controller.performCompare (new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),   new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performConvert (new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),   new QuantityDTO(0.0,  QuantityDTO.LengthUnit.INCHES));
        controller.performAdd     (new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),   new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performSubtract(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),   new QuantityDTO(6.0,  QuantityDTO.LengthUnit.INCHES));
        controller.performDivide  (new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES), new QuantityDTO(2.0,  QuantityDTO.LengthUnit.FEET));
        assertEquals(5, dbRepository.getTotalCount());
    }

    // ==================== Query by operation ====================

    @Test
    public void testQueryByOperation_AfterMultipleOps() {
        controller.performCompare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),  new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performCompare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS), new QuantityDTO(3.0,  QuantityDTO.LengthUnit.FEET));
        controller.performAdd    (new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),  new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        List<QuantityMeasurementEntity> compareOps = dbRepository.getMeasurementsByOperation("COMPARE");
        assertEquals(2, compareOps.size());
    }

    // ==================== Query by type ====================

    @Test
    public void testQueryByMeasurementType_AfterMixedOps() {
        controller.performCompare(new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),    new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performCompare(new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM), new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM));
        controller.performAdd    (new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),    new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        List<QuantityMeasurementEntity> lengthOps = dbRepository.getMeasurementsByType("LENGTH");
        assertEquals(2, lengthOps.size());
    }

    // ==================== Delete ====================

    @Test
    public void testDeleteAll_AfterOperations() {
        controller.performCompare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET), new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performAdd    (new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET), new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        assertEquals(2, dbRepository.getTotalCount());
        dbRepository.deleteAll();
        assertEquals(0, dbRepository.getTotalCount());
    }

    // ==================== Repository types ====================

    @Test
    public void testRepositoryFactory_DatabaseRepository_NotNull() {
        assertNotNull(dbRepository);
        assertTrue(dbRepository instanceof QuantityMeasurementDatabaseRepository);
    }

    @Test
    public void testRepositoryFactory_CacheRepository_NotNull() {
        IQuantityMeasurementRepository cacheRepo = QuantityMeasurementCacheRepository.getInstance();
        assertNotNull(cacheRepo);
        assertTrue(cacheRepo instanceof QuantityMeasurementCacheRepository);
    }

    // ==================== Pool ====================

    @Test
    public void testPoolStatistics_Accessible_NotEmpty() {
        String stats = dbRepository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
        assertTrue(stats.contains("ConnectionPool"));
    }

    // ==================== Error entities persisted ====================

    @Test
    public void testTemperatureAdd_ErrorPersisted() {
        controller.performAdd(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                              new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS));
        assertEquals(1, dbRepository.getTotalCount());
        assertTrue(dbRepository.getAllMeasurements().get(0).hasError());
    }

    @Test
    public void testCrossCategory_ErrorPersisted() {
        controller.performAdd(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                              new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM));
        assertEquals(1, dbRepository.getTotalCount());
        assertTrue(dbRepository.getAllMeasurements().get(0).hasError());
    }

    // ==================== Test isolation ====================

    @Test
    public void testH2Database_IsolationBetweenTests_StartsEmpty() {
        assertEquals(0, dbRepository.getTotalCount());
    }
}
