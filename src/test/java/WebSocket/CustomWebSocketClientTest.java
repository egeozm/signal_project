package WebSocket;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import com.data_management.CustomWebSocketClient;
import com.data_management.DataStorage;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CustomWebSocketClient class.
 */
public class CustomWebSocketClientTest {
    private CustomWebSocketClient customWebSocketClient;
    private TestDataStorage testDataStorage;
    private TestAlertStorage testAlertStorage;

    /**
     * A test implementation of the DataStorage class.
     */
    static class TestDataStorage extends DataStorage {
        private final List<String> dataRecords = new ArrayList<>();

        @Override
        public void addPatientData(int patientId, double data, String label, long timestamp) {
            dataRecords.add(patientId + "," + data + "," + label + "," + timestamp);
        }

        public List<String> getDataRecords() {
            return dataRecords;
        }
    }

    /**
     * A test implementation of the AlertStorage class.
     */
    static class TestAlertStorage extends AlertStorage {
        private final List<Alert> alerts = new ArrayList<>();

        @Override
        public void alertStore(Alert alert) {
            alerts.add(alert);
        }

        public List<Alert> getAlerts() {
            return alerts;
        }
    }

    /**
     * Sets up the test environment before each test.
     *
     * @throws URISyntaxException if the URI is invalid.
     */
    @BeforeEach
    public void setUp() throws URISyntaxException {
        testDataStorage = new TestDataStorage();
        testAlertStorage = new TestAlertStorage();
        AlertStorage.setAlertStorageInstance(testAlertStorage); // Set the singleton instance to our test instance
        customWebSocketClient = new CustomWebSocketClient(new URI("ws://test.url"), testDataStorage);
    }

    /**
     * Tests the onOpen method of CustomWebSocketClient.
     */
    @Test
    public void testOnOpen() {
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
        // Check console output if necessary or any state changes
    }

    /**
     * Tests the onMessage method of CustomWebSocketClient with valid data.
     */
    @Test
    public void testOnMessageValidData() {
        String validData = "1,1627848282,HeartRate,80";
        customWebSocketClient.onMessage(validData);
        List<String> records = testDataStorage.getDataRecords();
        assertFalse(records.isEmpty());
        assertEquals("1,80.0,HeartRate,1627848282", records.get(0));
    }

    /**
     * Tests the onMessage method of CustomWebSocketClient with malformed data.
     */
    @Test
    public void testOnMessageMalformedData() {
        String malformedData = "invalid data";
        customWebSocketClient.onMessage(malformedData);
        // Check that no records were added to data storage
        assertTrue(testDataStorage.getDataRecords().isEmpty());
    }

    /**
     * Tests the onMessage method of CustomWebSocketClient with alert data.
     */
    @Test
    public void testOnMessageAlertData() {
        String alertData = "2,1627848282,Alert,High Blood Pressure";
        customWebSocketClient.onMessage(alertData);
        List<Alert> alerts = testAlertStorage.getAlerts();
        assertFalse(alerts.isEmpty());
        Alert alert = alerts.get(0);
        assertEquals("2", alert.getPatientId());
        assertEquals("High Blood Pressure", alert.getCondition());
        assertEquals(1627848282L, alert.getTimestamp());
    }

    /**
     * Tests the onClose method of CustomWebSocketClient.
     */
    @Test
    public void testOnClose() {
        customWebSocketClient.onClose(1000, "Normal closure", true);
        // Check console output if necessary or any state changes
    }

    /**
     * Tests the onError method of CustomWebSocketClient.
     */
    @Test
    public void testOnError() {
        customWebSocketClient.onError(new Exception("Test error"));
        // Check console output for error stack trace
    }
}