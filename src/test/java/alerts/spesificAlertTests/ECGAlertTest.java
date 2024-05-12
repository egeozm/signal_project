//package alerts.spesificAlertTests;
//
//import com.alerts.Alert;
//import com.alerts.AlertStorage;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ECGAlertTest extends AlertTestBase{
//    @Override
//    public void setUp() {
//        super.setUp();
//    }
//
//    @Test
//    public void testLowHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert lowAlert = new Alert("2","resolved", 1800000000000L, "Heart Rate Lower Than 50 BPM");
//        System.out.println(alertStorage.getAlerts().size());
//        for (int i = 0; i < alertStorage.getAlerts().size(); i++) {
//            System.out.println(alertStorage.getAlerts().get(i).getLabel());
//        }
//        assertTrue(alertStorage.getAlerts().contains(lowAlert));
//    }
//
//    @Test
//    public void testHighHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert highAlert = new Alert("1","resolved", 1800000000000L, "Heart Rate Higher Than 100 BPM");
//        System.out.println(alertStorage.getAlerts().size());
//        for (int i = 0; i < alertStorage.getAlerts().size(); i++) {
//            System.out.println(alertStorage.getAlerts().get(i).getLabel());
//        }
//        assertTrue(alertStorage.getAlerts().contains(highAlert));
//    }
//
//    @Test
//    public void testIrregularHeartRateAlert() {
//        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
//        Alert alert = new Alert("181","resolved", 1800000000000L, "Irregular Beat Alert");
//        System.out.println(alertStorage.getAlerts().size());
//        for (int i = 0; i < alertStorage.getAlerts().size(); i++) {
//            System.out.println(alertStorage.getAlerts().get(i).getLabel());
//        }
//        assertTrue(alertStorage.getAlerts().contains(alert));
//    }
//
//}
//
//// I do not know what is wrong because I am not sure how to convert ECG to BPM