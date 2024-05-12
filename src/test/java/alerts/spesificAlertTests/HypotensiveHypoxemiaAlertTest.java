package alerts.spesificAlertTests;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HypotensiveHypoxemiaAlertTest extends AlertTestBase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testHypotensiveHypoxemiaAlert() {
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        Alert alert = new Alert("2", "resolved", 1800000000000L, "Hypotensive Hypoxemia Alert");
        assertTrue(alertStorage.getAlerts().contains(alert));
    }
}
