package WebSocket;

import com.data_management.CustomWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for handling WebSocket errors in the CustomWebSocketClient.
 * This class includes tests to ensure that corrupted and malformed data
 * are properly handled without causing issues or storing invalid records.
 */
public class WebSocketErrorHandlingTest {
    private CustomWebSocketClient customWebSocketClient;
    private TestDataStorage testDataStorage;

    /**
     * Simplified DataStorage for testing purposes.
     * This class inherits all methods from DataStorage directly.
     */
    static class TestDataStorage extends DataStorage {
        // This class inherits all methods from DataStorage directly.
    }

    /**
     * Sets up the test environment before each test.
     * Initializes the TestDataStorage and CustomWebSocketClient.
     *
     * @throws URISyntaxException if the WebSocket URI is incorrect.
     */
    @BeforeEach
    public void setUp() throws URISyntaxException {
        testDataStorage = new TestDataStorage();
        customWebSocketClient = new CustomWebSocketClient(new URI("ws://localhost:8978"), testDataStorage);
    }

    /**
     * Tests the handling of corrupted data received through WebSocket.
     * Ensures that no records are stored when corrupted data is received.
     */
    @Test
    public void testReceivingCorruptedData() {
        // Simulate receiving corrupted data
        String corruptedData = "1,invalid_timestamp,HeartRate,80";
        customWebSocketClient.onMessage(corruptedData);
        List<PatientRecord> records = testDataStorage.getRecords(1, 0, Long.MAX_VALUE);
        assertTrue(records.isEmpty(), "No records should be stored if data is corrupted.");
    }

    /**
     * Tests the handling of malformed data received through WebSocket.
     * Ensures that no records are stored when malformed data is received.
     */
    @Test
    public void testHandlingMalformedData() {
        // Simulate receiving malformed data
        String malformedData = "this is not a valid message";
        customWebSocketClient.onMessage(malformedData);
        List<PatientRecord> records = testDataStorage.getRecords(1, 0, Long.MAX_VALUE);
        assertTrue(records.isEmpty(), "No records should be stored if data is malformed.");
    }
}
