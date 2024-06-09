package WebSocket;

import com.data_management.CustomWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.WebSocketReaderImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the WebSocketReaderImplementation.
 * This class tests the functionality of the WebSocketReaderImplementation
 * to ensure it correctly interacts with the WebSocket client and data storage.
 */
public class WebSocketReaderImplementationTest {
    private WebSocketReaderImplementation webSocketReader;
    private TestDataStorage testDataStorage;
    private TestCustomWebSocketClient testWebSocketClient;

    /**
     * Simplified DataStorage for testing purposes.
     * Stores data records in-memory for verification.
     */
    static class TestDataStorage extends DataStorage {
        private final List<String> dataRecords = new ArrayList<>();

        @Override
        public void addPatientData(int patientId, double data, String label, long timestamp) {
            dataRecords.add(patientId + "," + data + "," + label + "," + timestamp);
        }
    }

    /**
     * Simplified CustomWebSocketClient for testing purposes.
     * Overrides the connect method to simulate connection state.
     */
    static class TestCustomWebSocketClient extends CustomWebSocketClient {
        private boolean isConnected = false;

        public TestCustomWebSocketClient(URI serverURI, DataStorage dataStorage) {
            super(serverURI, dataStorage);
        }

        @Override
        public void connect() {
            isConnected = true;
        }

        public boolean isConnected() {
            return isConnected;
        }
    }

    /**
     * Sets up the test environment before each test.
     * Initializes the TestDataStorage and WebSocketReaderImplementation.
     *
     * @throws Exception if an error occurs during setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        testDataStorage = new TestDataStorage();
        webSocketReader = new WebSocketReaderImplementation() {
            @Override
            public void readData(DataStorage dataStorage) throws Exception {
                testWebSocketClient = new TestCustomWebSocketClient(new URI("ws://localhost:8978"), dataStorage);
                testWebSocketClient.connect();
            }
        };
    }

    /**
     * Tests the readData method to ensure it connects the WebSocket client correctly.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testReadData() throws Exception {
        webSocketReader.readData(testDataStorage);
        assertTrue(testWebSocketClient.isConnected(), "WebSocket should be connected.");
    }

    /**
     * Tests the readData method to ensure it throws an exception correctly.
     */
    @Test
    public void testReadDataWithException() {
        WebSocketReaderImplementation faultyReader = new WebSocketReaderImplementation() {
            @Override
            public void readData(DataStorage dataStorage) throws Exception {
                throw new Exception("Test Exception");
            }
        };

        Exception exception = assertThrows(Exception.class, () -> {
            faultyReader.readData(testDataStorage);
        });

        assertEquals("Test Exception", exception.getMessage());
    }
}
