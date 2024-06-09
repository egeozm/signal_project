//package alerts.spesificAlertTests;
//
//import com.data_management.DataReader;
//import com.data_management.DataReaderImplementation;
//import com.data_management.DataStorage;
//import com.alerts.AlertGenerator;
//import com.data_management.Patient;
//import org.junit.jupiter.api.BeforeEach;
//
//
//public abstract class AlertTestBase {
//    protected AlertGenerator alertGenerator;
//    protected DataStorage dataStorage;
//    protected DataReader reader;
//    protected static final String testDirectoryPath = "src/test/java/data_management/test_data";
//
//
//    @BeforeEach
//    public void setUp() {
//        reader = new DataReaderImplementation(testDirectoryPath);
//        dataStorage = new DataStorage();
//        try {
//            reader.readData(dataStorage);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to load test data",e);
//        }
//        alertGenerator = new AlertGenerator();
//        // Evaluate all patients' data to check for conditions that may trigger alerts
//        for (Patient patient : dataStorage.getAllPatients()) {
//            alertGenerator.evaluateData(patient);
//        }
//
//
//    }
//}
//
//
