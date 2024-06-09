package WebSocket;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import com.alerts.AlertGenerator;
import com.data_management.CustomWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the WebSocket functionality involving CustomWebSocketClient, DataStorage, and Alert handling.
 * This class ensures that the different components interact correctly when processing valid and invalid data.
 */
public class WebSocketIntegrationTest {
    private CustomWebSocketClient customWebSocketClient;
    private TestDataStorage testDataStorage;
    private AlertGenerator alertGenerator;
    private TestAlertStorage testAlertStorage;

    /**
     * Simplified DataStorage for testing purposes.
     * Stores patient records in-memory for verification.
     */
    static class TestDataStorage extends DataStorage {
        private final List<PatientRecord> records = new ArrayList<>();

        @Override
        public void addPatientData(int patientId, double data, String label, long timestamp) {
            records.add(new PatientRecord(patientId, data, label, timestamp));
        }

        /**
         * Retrieves patient data records within a specified time range.
         *
         * @param patientId the patient ID
         * @param startTime the start time of the range
         * @param endTime   the end time of the range
         * @return a list of patient records
         */
        public List<PatientRecord> getPatientData(int patientId, long startTime, long endTime) {
            return records.stream()
                    .filter(record -> record.getPatientId() == patientId &&
                            record.getTimestamp() >= startTime &&
                            record.getTimestamp() <= endTime)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Simplified AlertStorage for testing purposes.
     * Stores alerts in-memory for verification.
     */
    static class TestAlertStorage extends AlertStorage {
        private final List<Alert> alerts = new ArrayList<>();

        @Override
        public void alertStore(Alert alert) {
            alerts.add(alert);
        }

        /**
         * Retrieves all stored alerts.
         *
         * @return a list of alerts
         */
        public List<Alert> getAlerts() {
            return alerts;
        }
    }

    /**
     * Sets up the test environment before each test.
     * Initializes the TestDataStorage, TestAlertStorage, CustomWebSocketClient, and AlertGenerator.
     *
     * @throws URISyntaxException if the WebSocket URI is incorrect.
     */
    @BeforeEach
    public void setUp() throws URISyntaxException {
        testDataStorage = new TestDataStorage();
        testAlertStorage = new TestAlertStorage();
        AlertStorage.setAlertStorageInstance(testAlertStorage); // Set the singleton instance to our test instance
        customWebSocketClient = new CustomWebSocketClient(new URI("ws://localhost:8978"), testDataStorage);
        alertGenerator = new AlertGenerator();
    }

    /**
     * Tests the integration of WebSocket client with data storage and alert generation.
     * Simulates receiving valid and alert data through WebSocket and verifies the correct handling and storage.
     */
    @Test
    public void testIntegration() {
        // Simulate WebSocket onOpen
        customWebSocketClient.onOpen(new ServerHandshake() {
            @Override
            public short getHttpStatus() {
                return 0;
            }

            @Override
            public String getHttpStatusMessage() {
                return null;
            }

            @Override
            public Iterator<String> iterateHttpFields() {
                return null;
            }

            @Override
            public String getFieldValue(String name) {
                return null;
            }

            @Override
            public boolean hasFieldValue(String name) {
                return false;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }
        });

        // Simulate receiving valid data for patient 1
        String validDataPatient1 = "1,1627848282,HeartRate,80";
        customWebSocketClient.onMessage(validDataPatient1);

        // Simulate receiving alert data for patient 1
        String alertDataPatient1 = "1,1627848282,Alert,High Heart Rate";
        customWebSocketClient.onMessage(alertDataPatient1);

        // Verify patient data for patient 1
        List<PatientRecord> recordsPatient1 = testDataStorage.getPatientData(1, 0, Long.MAX_VALUE);
        assertFalse(recordsPatient1.isEmpty());
        assertEquals(1, recordsPatient1.size());

        // Verify alert data for patient 1
        List<Alert> alertsPatient1 = testAlertStorage.getAlerts();
        assertFalse(alertsPatient1.isEmpty());
        assertEquals(1, alertsPatient1.size());

        // Simulate alert generation for patient 1
        Patient patient1 = new Patient(1);
        for (PatientRecord record : recordsPatient1) {
            patient1.addRecord(record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
        }
        alertGenerator.evaluateData(patient1);

        // Validate if any alerts were generated based on conditions for patient 1
        alertsPatient1 = testAlertStorage.getAlerts();
        // Example assertions based on assumed alert conditions
        assertFalse(alertsPatient1.isEmpty());
        Alert alert = alertsPatient1.get(0);
        assertEquals("1", alert.getPatientId());
        assertEquals("High Heart Rate", alert.getCondition());

        // Simulate WebSocket onClose
        customWebSocketClient.onClose(1000, "Normal closure", true);
    }

    /**
     * Tests the WebSocket client's error handling.
     * Simulates an error occurring and verifies the appropriate handling logic.
     */
    @Test
    public void testWebSocketErrorHandling() {
        // Simulate WebSocket onError
        customWebSocketClient.onError(new Exception("Test error"));
        // Validate error handling if any specific logic needs to be verified
    }
}
