//package alerts.spesificAlertTests;
//
//import com.alerts.Alert;
//import com.alerts.AlertStorage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class SaturationAlertsTest extends AlertTestBase {
//
//    @Override
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//    }
//
//    @Test
//    public void testLowSaturationAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert alert = new Alert("1", "resolved", 1800000000000L, "Saturation level lower than 92.0%");
//        assertTrue(alertStorage.getAlerts().contains(alert));
//    }
//
//    @Test
//    public void testRapidDropSaturationAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert alert = new Alert("2", "resolved", 1800000000000L, "Rapid Saturation Drop Alert");
//        assertTrue(alertStorage.getAlerts().contains(alert));
//    }
//
//
//
//
//}
