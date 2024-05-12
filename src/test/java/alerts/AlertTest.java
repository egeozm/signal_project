package alerts;

import com.alerts.Alert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertTest {

    @Test
    void testAlertConstructorAndGetters() {
        long currentTime = System.currentTimeMillis();
        Alert alert = new Alert("1", "High", currentTime, "BloodPressure");

        assertEquals("1", alert.getPatientId());
        assertEquals("High", alert.getCondition());
        assertEquals(currentTime, alert.getTimestamp());
        assertEquals("BloodPressure", alert.getLabel());
    }

    @Test
    void testSetCondition() {
        Alert alert = new Alert("1", "High", System.currentTimeMillis(), "BloodPressure");
        alert.setCondition("Low");
        assertEquals("Low", alert.getCondition());
    }

    @Test
    void testEqualsSameValues() {
        long currentTime = System.currentTimeMillis();
        Alert alert1 = new Alert("1", "High", currentTime, "BloodPressure");
        Alert alert2 = new Alert("1", "High", currentTime, "BloodPressure");

        assertEquals(alert1, alert2);
    }

    @Test
    void testEqualsDifferentValues() {
        Alert alert1 = new Alert("1", "High", System.currentTimeMillis(), "BloodPressure");
        Alert alert2 = new Alert("1", "Low", System.currentTimeMillis(), "BloodPressure");

        assertNotEquals(alert1, alert2);
    }

    @Test
    void testEqualsDifferentObjects() {
        Alert alert = new Alert("1", "High", System.currentTimeMillis(), "BloodPressure");
        Object obj = new Object();

        assertNotEquals(alert, obj);
    }
}