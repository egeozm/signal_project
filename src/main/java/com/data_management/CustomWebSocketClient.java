package com.data_management;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * CustomWebSocketClient is a specialized WebSocket client that processes
 * messages received from a WebSocket server and stores patient data and alerts.
 */
public class CustomWebSocketClient extends WebSocketClient {

    private final DataStorage dataStorage;

    /**
     * Constructs a new CustomWebSocketClient instance.
     *
     * @param serverURI   the URI of the WebSocket server.
     * @param dataStorage the DataStorage instance to store patient data.
     */
    public CustomWebSocketClient(URI serverURI, DataStorage dataStorage) {
        super(serverURI);
        this.dataStorage = dataStorage;
    }

    /**
     * Called when the WebSocket connection is opened.
     *
     * @param handshake the handshake data from the server.
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        // Print a message indicating the connection is opened
        System.out.println("Opened connection");
    }

    /**
     * Called when a message is received from the WebSocket server.
     * Processes the message and stores the appropriate data or alert.
     *
     * @param line the message received from the server.
     */
    @Override
    public void onMessage(String line) {
        // Get the singleton instance of AlertStorage
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        try {
            // Split the incoming message by commas
            String[] lineData = line.split(",");
            // Parse patient ID, timestamp, and label from the message
            int patientId = Integer.parseInt(lineData[0].trim());
            long timestamp = Long.parseLong(lineData[1].trim());
            String label = lineData[2].trim();

            // Check if the message is an alert or regular patient data
            if (!label.equals("Alert")) {
                // Parse the measurement value and add the patient data to storage
                double data = Double.parseDouble(lineData[3].trim().replace("%", ""));
                dataStorage.addPatientData(patientId, data, label, timestamp);
                System.out.printf("Patient Record added %s%n", line);
            } else {
                // Create and store an alert if the label is "Alert"
                String condition = lineData[3].trim();
                Alert alert = new Alert(String.valueOf(patientId), condition, timestamp, "Nurses or patients triggering the alert button");
                alertStorage.alertStore(alert);
            }
        } catch (Exception e) {
            // Print stack trace if an error occurs during message processing
            e.printStackTrace();
        }
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * @param code   the closing code sent by the server.
     * @param reason the reason for the closing.
     * @param remote whether the closing was initiated by the remote host.
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        // Print a message indicating the connection is closed, along with the reason and code
        System.out.println("Closed connection with exit code " + code + " additional info: " + reason);
    }

    /**
     * Called when an error occurs.
     *
     * @param e the exception that occurred.
     */
    @Override
    public void onError(Exception e) {
        // Print the stack trace of the exception
        e.printStackTrace();
    }
}