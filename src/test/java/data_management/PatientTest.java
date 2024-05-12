package data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Provides JUnit tests for the Patient class in the com.data_management package.
 * These tests aim to verify the functionality of the Patient class, particularly focusing on
 * adding records and retrieving them under various conditions, including filtering by specific time ranges.
 */
public class PatientTest {
    private Patient patient;

    /**
     * Sets up a new instance of Patient with a unique ID for each test case.
     * This method is executed before each test to ensure test isolation and to work with a fresh instance.
     */
    @BeforeEach
    void setUp() {
        patient = new Patient(1); // Initialize a Patient with ID 1
    }

    /**
     * Tests the addition of a single record to a patient's medical history.
     * Ensures that the record is correctly added and retrievable.
     */
    @Test
    void testAddRecord() {
        long currentTime = System.currentTimeMillis();
        patient.addRecord(120.0, "HeartRate", currentTime);

        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE);
        assertEquals(1, records.size(), "There should be one record.");

        PatientRecord record = records.get(0);
        assertEquals(120.0, record.getMeasurementValue(), 0.001, "Measurement value should match the one provided.");
        assertEquals("HeartRate", record.getRecordType(), "Record type should match.");
        assertEquals(currentTime, record.getTimestamp(), "Timestamp should match the one provided.");
    }

    /**
     * Tests the retrieval of patient records within a specific time range.
     * Adds records before, during, and after the specified time range, and checks that
     * only the records within the time range are retrieved.
     */
    @Test
    void testGetRecordsWithinTimeRange() {
        long baseTime = System.currentTimeMillis();

        patient.addRecord(120.0, "HeartRate", baseTime - 1000);  // 1 second before the base time
        patient.addRecord(130.0, "HeartRate", baseTime);         // At the base time
        patient.addRecord(140.0, "HeartRate", baseTime + 1000);  // 1 second after the base time

        List<PatientRecord> records = patient.getRecords(baseTime - 1000, baseTime + 500);
        assertEquals(2, records.size(), "Should retrieve 2 records within the specified time range.");

        assertEquals(120.0, records.get(0).getMeasurementValue(), 0.001, "First record's measurement value should match.");
        assertEquals(130.0, records.get(1).getMeasurementValue(), 0.001, "Second record's measurement value should match.");
    }

    /**
     * Tests that no records are retrieved when querying outside any existing record's timestamp.
     * Ensures the method correctly handles and returns an empty list when no records match the query criteria.
     */
    @Test
    void testNoRecordsFound() {
        long timeOutsideRecords = Long.MIN_VALUE + 1;
        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, timeOutsideRecords);
        assertTrue(records.isEmpty(), "Should return an empty list when no records fall within the time range.");
    }
}
