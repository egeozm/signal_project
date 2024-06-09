//package alerts.spesificAlertTests;
//
//import com.alerts.Alert;
//import com.alerts.AlertStorage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ECGAlertTest extends AlertTestBase {
//
//    @Override
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//    }
//
//    @Test
//    public void testLowHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert lowAlert = new Alert("4", "resolved", 1800000000000L, "Abnormal Heart Rate Lower Than 50");
//        assertTrue(alertStorage.getAlerts().contains(lowAlert));
//    }
//
//    @Test
//    public void testHighHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert highAlert = new Alert("1", "resolved", 1800000000000L, "Abnormal Heart Rate Higher Than 100");
//        assertTrue(alertStorage.getAlerts().contains(highAlert));
//    }
//
//    @Test
//    public void testIrregularHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert alert = new Alert("181", "resolved", 1800000000000L, "Irregular Beat Detected");
//        assertTrue(alertStorage.getAlerts().contains(alert));
//    }
//
//}
