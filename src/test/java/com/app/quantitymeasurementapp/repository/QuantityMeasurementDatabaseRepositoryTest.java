package com.app.quantitymeasurementapp.repository;

import com.app.quantitymeasurementapp.entity.QuantityDTO;
import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurementapp.util.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    @Before
    public void setUp() {
        System.setProperty("db.url",           "jdbc:h2:mem:testdb_repo_" + System.nanoTime() + ";DB_CLOSE_DELAY=-1");
        System.setProperty("db.username",      "sa");
        System.setProperty("db.password",      "");
        System.setProperty("db.driver",        "org.h2.Driver");
        System.setProperty("pool.initialSize", "2");
        System.setProperty("pool.maxSize",     "5");
        ApplicationConfig config = ApplicationConfig.getInstance();
        repository = new QuantityMeasurementDatabaseRepository(config);
        repository.deleteAll();
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        repository.releaseResources();
    }

    // ==================== Pool ====================

    @Test
    public void testConnectionPool_Initialization() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains("ConnectionPool"));
    }

    @Test
    public void testConnectionPool_StatsAfterSave() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }

    // ==================== Save ====================

    @Test
    public void testSave_CompareEntity() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testSave_ConvertEntity() {
        QuantityDTO source = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("CONVERT", source, result));
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testSave_ErrorEntity() {
        QuantityDTO q1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO q2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);
        repository.save(new QuantityMeasurementEntity("ADD", q1, q2, "Temperature does not support ADD"));
        assertEquals(1, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().get(0).hasError());
    }

    @Test
    public void testSave_Multiple() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE",  q1, q2, true));
        repository.save(new QuantityMeasurementEntity("CONVERT",  q1, q2));
        repository.save(new QuantityMeasurementEntity("ADD",      q1, q2, new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET)));
        assertEquals(3, repository.getTotalCount());
    }

    // ==================== Retrieve ====================

    @Test
    public void testGetAllMeasurements_Empty() {
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    public void testGetAllMeasurements_AfterSave() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        repository.save(new QuantityMeasurementEntity("ADD",     q1, q2, new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET)));
        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertEquals(2, all.size());
    }

    @Test
    public void testGetMeasurementsByOperation_Compare() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, false));
        repository.save(new QuantityMeasurementEntity("ADD",     q1, q2, new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET)));
        List<QuantityMeasurementEntity> compareOps = repository.getMeasurementsByOperation("COMPARE");
        assertEquals(2, compareOps.size());
    }

    @Test
    public void testGetMeasurementsByOperation_Empty() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        List<QuantityMeasurementEntity> divideOps = repository.getMeasurementsByOperation("DIVIDE");
        assertTrue(divideOps.isEmpty());
    }

    @Test
    public void testGetMeasurementsByType_Length() {
        QuantityDTO lenQ1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO lenQ2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO wgtQ1 = new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO wgtQ2 = new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM);
        repository.save(new QuantityMeasurementEntity("COMPARE", lenQ1, lenQ2, true));
        repository.save(new QuantityMeasurementEntity("COMPARE", wgtQ1, wgtQ2, false));
        List<QuantityMeasurementEntity> lengthOps = repository.getMeasurementsByType("LENGTH");
        assertEquals(1, lengthOps.size());
    }

    // ==================== Count ====================

    @Test
    public void testGetTotalCount_Empty() {
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    public void testGetTotalCount_AfterSaves() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, false));
        assertEquals(2, repository.getTotalCount());
    }

    // ==================== Delete ====================

    @Test
    public void testDeleteAll() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        repository.save(new QuantityMeasurementEntity("ADD",     q1, q2, new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET)));
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    public void testClear_SameAsDeleteAll() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        repository.clear();
        assertEquals(0, repository.getTotalCount());
    }

    // ==================== Schema + Timestamp ====================

    @Test
    public void testDatabaseSchema_TablesCreated() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertEquals(1, all.size());
        assertNotNull(all.get(0).getTimestamp());
    }

    // ==================== SQL Injection Prevention ====================

    @Test
    public void testSQLInjectionPrevention() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        List<QuantityMeasurementEntity> result = repository.getMeasurementsByOperation(
                "COMPARE'; DROP TABLE quantity_measurement_entity; --");
        assertTrue(result.isEmpty());
        assertTrue(repository.getTotalCount() >= 1);
    }

    // ==================== Batch Insert ====================

    @Test
    public void testBatchInsert_MultipleEntities() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        for (int i = 0; i < 10; i++) {
            repository.save(new QuantityMeasurementEntity("COMPARE", q1, q2, true));
        }
        assertEquals(10, repository.getTotalCount());
    }

    // ==================== Temperature ====================

    @Test
    public void testSave_TemperatureCompareEntity() {
        QuantityDTO t1 = new QuantityDTO(0.0,  QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        repository.save(new QuantityMeasurementEntity("COMPARE", t1, t2, true));
        List<QuantityMeasurementEntity> temps = repository.getMeasurementsByType("TEMPERATURE");
        assertEquals(1, temps.size());
    }
}
