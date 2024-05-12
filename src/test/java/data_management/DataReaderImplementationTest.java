package data_management;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import com.data_management.DataReaderImplementation;
import com.data_management.DataStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the DataReaderImplementation class by reading predefined test files.
 */
public class DataReaderImplementationTest {
    private static final String testDirectoryPath = "src/test/java/data_management/test_data";
    private static DataReaderImplementation dataReader;

    @BeforeAll
    static void setup() {
        // Setup directory and files for testing
        setUpDirectory();
        // Instantiate DataReader with the path to the test directory
        dataReader = new DataReaderImplementation(testDirectoryPath);
    }

//    @AfterAll
//    static void cleanup() {
//        // Cleanup test files after all tests
//        try {
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "Cholesterol.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "SystolicPressure.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "DiastolicPressure.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "ECG.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "RedBloodCells.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "WhiteBloodCells.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "Saturation.txt"));
//            Files.deleteIfExists(Paths.get(testDirectoryPath, "Alert.txt"));
//        } catch (IOException e) {
//            System.err.println("Error cleaning up test files: " + e.getMessage());
//        }
//    }

    private static void setUpDirectory() {
        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get(testDirectoryPath));

            // Write test data to Cholesterol.txt
            Files.writeString(Paths.get(testDirectoryPath, "Cholesterol.txt"),
                    "Patient ID: 1, Timestamp: 1715086124172, Label: Cholesterol, Data: 195.632\n" +
                            "Patient ID: 39, Timestamp: 1715086124192, Label: Cholesterol, Data: 152.983\n" +
                            "Patient ID: 48, Timestamp: 1715086124177, Label: Cholesterol, Data: 191.474");

            // Write test data to SystolicPressure.txt
            Files.writeString(Paths.get(testDirectoryPath, "SystolicPressure.txt"),
                    "Patient ID: 1, Timestamp: 1715086124171, Label: SystolicPressure, Data: 190.0\n" +
                            "Patient ID: 2, Timestamp: 1715086124171, Label: SystolicPressure, Data: 80.0\n" +
                            "Patient ID: 13, Timestamp: 1715086124203, Label: SystolicPressure, Data: 130.0\n" +
                            "Patient ID: 13, Timestamp: 1715086124206, Label: SystolicPressure, Data: 119.0\n" +
                            "Patient ID: 13, Timestamp: 1715086124208, Label: SystolicPressure, Data: 108.0");

            // Write test data to DiastolicPressure.txt
            Files.writeString(Paths.get(testDirectoryPath, "DiastolicPressure.txt"),
                    "Patient ID: 1, Timestamp: 1715086124171, Label: DiastolicPressure, Data: 130.0\n" +
                            "Patient ID: 2, Timestamp: 1715086124171, Label: DiastolicPressure, Data: 50.0\n" +
                            "Patient ID: 14, Timestamp: 1715086124203, Label: DiastolicPressure, Data: 90.0\n" +
                            "Patient ID: 14, Timestamp: 1715086124206, Label: DiastolicPressure, Data: 101.0\n" +
                            "Patient ID: 14, Timestamp: 1715086124208, Label: DiastolicPressure, Data: 112.0");

            // Write test data to ECG.txt
            Files.writeString(Paths.get(testDirectoryPath, "ECG.txt"),
                    "Patient ID: 2, Timestamp: 1715551123342, Label: ECG, Data: -0.576\n" +
                            "Patient ID: 2, Timestamp: 1715551125342, Label: ECG, Data: -0.480\n" +
                            "Patient ID: 1, Timestamp: 1715551123342, Label: ECG, Data: 0.109\n" +
                            "Patient ID: 1, Timestamp: 1715551125342, Label: ECG, Data: 0.115\n" +
                            "Patient ID: 181, Timestamp: 1715551132197, Label: ECG, Data: 0.498\n" +
                            "Patient ID: 181, Timestamp: 1715551135597, Label: ECG, Data: 0.506\n" +
                            "Patient ID: 181, Timestamp: 1715551136697, Label: ECG, Data: 0.495");

            // Write test data to RedBloodCells.txt
            Files.writeString(Paths.get(testDirectoryPath, "RedBloodCells.txt"),
                    "Patient ID: 1, Timestamp: 1715086124171, Label: RedBloodCells, Data: 114.0\n" +
                            "Patient ID: 11, Timestamp: 1715086124203, Label: RedBloodCells, Data: 116.0\n" +
                            "Patient ID: 13, Timestamp: 1715086124206, Label: RedBloodCells, Data: 127.0\n" +
                            "Patient ID: 31, Timestamp: 1715086124206, Label: RedBloodCells, Data: 119.0");

            // Write test data to Saturation.txt
            Files.writeString(Paths.get(testDirectoryPath, "Saturation.txt"),
                    "Patient ID: 1, Timestamp: 1715285596092, Label: Saturation, Data: 91.0%\n" +
                            "Patient ID: 2, Timestamp: 1715285596092, Label: Saturation, Data: 98.0%\n" +
                            "Patient ID: 2, Timestamp: 1715285599000, Label: Saturation, Data: 93.0%");

            // Write test data to WhiteBloodCells.txt
            Files.writeString(Paths.get(testDirectoryPath, "WhiteBloodCells.txt"),
                    "Patient ID: 1, Timestamp: 1715086124171, Label: WhiteBloodCells, Data: 114.0\n" +
                            "Patient ID: 11, Timestamp: 1715086124203, Label: WhiteBloodCells, Data: 116.0\n" +
                            "Patient ID: 13, Timestamp: 1715086124206, Label: WhiteBloodCells, Data: 127.0\n" +
                            "Patient ID: 31, Timestamp: 1715086124206, Label: WhiteBloodCells, Data: 119.0");

            // Write test data to Alert.txt
            Files.writeString(Paths.get(testDirectoryPath, "Alert.txt"),
                    "Patient ID: 1, Timestamp: 1715086124171, Label: Alert, Data: triggered\n" +
                            "Patient ID: 11, Timestamp: 1715086124203, Label: Alert, Data: triggered\n" +
                            "Patient ID: 13, Timestamp: 1715086124206, Label: Alert, Data: triggered\n" +
                            "Patient ID: 31, Timestamp: 1715086124206, Label: Alert, Data: triggered");

        } catch (IOException e) {
            throw new RuntimeException("Failed to set up test directory", e);
        }
    }

    @Test
    void testReadData() {
        // Simulated DataStorage and AlertStorage instances to verify data reading
        MockDataStorage dataStorage = new MockDataStorage();
        MockAlertStorage alertStorage = new MockAlertStorage();
        AlertStorage.setAlertStorageInstance(alertStorage);

        try {
            dataReader.readData(dataStorage);
        } catch (IOException e) {
            fail("Should not throw exception during reading: " + e.getMessage());
        }

        // Assertions to verify data reading logic
        assertTrue(dataStorage.hasData("Cholesterol"), "Cholesterol data should be read");
        assertTrue(dataStorage.hasData("SystolicPressure"), "SystolicPressure data should be read");

        // Assertions to verify alert processing logic
        assertTrue(alertStorage.hasAlerts(), "Alert data should be processed and stored");
        assertEquals(4, alertStorage.getAlerts().size(), "Four alerts should be stored");

        // Check a specific record to ensure data is parsed correctly
        assertTrue(dataStorage.hasData("ECG"), "ECG data should be read");
        assertTrue(alertStorage.getAlerts().stream().anyMatch(alert -> alert.getPatientId().equals("1")
                && alert.getCondition().equals("triggered")), "Correct alert data should be stored");
    }

    @Test
    void testErrorHandlingForMissingFile() {
        MockDataStorage dataStorage = new MockDataStorage();

        // Rename a file temporarily to simulate it missing
        try {
            Files.move(Paths.get(testDirectoryPath, "Cholesterol.txt"), Paths.get(testDirectoryPath, "Cholesterol.tmp"));
        } catch (IOException e) {
            fail("Failed to rename file for testing");
        }

        try {
            dataReader.readData(dataStorage);
            fail("IOException should be thrown for missing file");
        } catch (IOException e) {
            // Expected
        } finally {
            // Restore file
            try {
                Files.move(Paths.get(testDirectoryPath, "Cholesterol.tmp"), Paths.get(testDirectoryPath, "Cholesterol.txt"));
            } catch (IOException e) {
                fail("Failed to restore file after testing");
            }
        }
    }


    /**
     * Mock implementation of DataStorage to track read operations.
     */
    static class MockDataStorage extends DataStorage {
        private final Set<String> readLabels = new HashSet<>();

        @Override
        public void addPatientData(int patientId, double data, String label, long timestamp) {
            readLabels.add(label);
        }


        public boolean hasData(String label) {
            return readLabels.contains(label);
        }
    }

    static class MockAlertStorage extends AlertStorage {
        private final List<Alert> alerts = new ArrayList<>();

        @Override
        public void alertStore(Alert alert) {
            alerts.add(alert);
        }

        public boolean hasAlerts() {
            return !alerts.isEmpty();
        }

        public List<Alert> getAlerts() {
            return alerts;
        }
    }
}
