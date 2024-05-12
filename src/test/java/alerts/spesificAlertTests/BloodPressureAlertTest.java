package alerts.spesificAlertTests;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloodPressureAlertTest extends AlertTestBase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testIncreasingTrendAlert() {
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        Alert alert = new Alert("14", "resolved", 1800000000000L, "Increasing Blood Pressure Trend Problem");
        assertTrue(alertStorage.getAlerts().contains(alert));
    }

    @Test
    public void testDecreasingTrendAlert() {
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        Alert alert = new Alert("13", "resolved", 1800000000000L, "Decreasing Blood Pressure Trend Problem");
        assertTrue(alertStorage.getAlerts().contains(alert));
    }

    @Test
    public void testSystolicPressureThreshold() {
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        Alert highAlert = new Alert("1","resolved", 1800000000000L, "Systolic Pressure higher than 180");
        Alert lowAlert = new Alert("2", "resolved", 1800000000000L, "Systolic Pressure lower than 90");
        assertTrue(alertStorage.getAlerts().contains(highAlert));
        assertTrue(alertStorage.getAlerts().contains(lowAlert));
    }

    @Test
    public void testDiastolicPressureThreshold() {
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        Alert highAlert = new Alert("1","resolved", 1800000000000L, "Diastolic Pressure higher than 120");
        Alert lowAlert = new Alert("2", "resolved", 1800000000000L, "Diastolic Pressure lower than 60");
        assertTrue(alertStorage.getAlerts().contains(highAlert));
        assertTrue(alertStorage.getAlerts().contains(lowAlert));

    }


}
