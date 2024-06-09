package com.data_management;

import java.net.URI;

/**
 * Implementation of the DataReader interface that reads data from a WebSocket server.
 */
public class WebSocketReaderImplementation implements DataReader {

    /**
     * Reads data from a WebSocket server and stores it in the provided DataStorage instance.
     * This method establishes a WebSocket connection to the server at the specified URI.
     *
     * @param dataStorage the DataStorage instance where the data read from the WebSocket will be stored
     * @throws Exception if an error occurs during the WebSocket connection or data reading
     */
    @Override
    public void readData(DataStorage dataStorage) throws Exception {
        // Create a new WebSocket client instance with the specified URI and data storage
        CustomWebSocketClient c = new CustomWebSocketClient(new URI("ws://localhost:8978"), dataStorage);
        // Connect to the WebSocket server
        c.connect();
    }
}