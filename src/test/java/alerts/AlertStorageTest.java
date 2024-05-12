package alerts;

import com.alerts.Alert;
import com.alerts.AlertStorage;
import com.alerts.AlertStorageStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for AlertStorage without using Mockito.
 */
public class AlertStorageTest {
    private AlertStorage alertStorage;
    private TestAlertStorageStrategy testStrategy;

    @BeforeEach
    void setUp() {
        // Reset the singleton instance to ensure test isolation
        AlertStorage.setAlertStorageInstance(null);
        // Implement a simple test strategy that records alerts
        testStrategy = new TestAlertStorageStrategy();
        alertStorage = new AlertStorage((AlertStorageStrategy) testStrategy);
        AlertStorage.setAlertStorageInstance(alertStorage);
    }

    @AfterEach
    void tearDown() {
        // Clear the singleton instance after each test
        AlertStorage.setAlertStorageInstance(null);
    }

    @Test
    void testSingletonBehavior() {
        AlertStorage firstInstance = AlertStorage.getAlertStorageInstance();
        AlertStorage secondInstance = AlertStorage.getAlertStorageInstance();
        assertSame(firstInstance, secondInstance, "Singleton instance should be the same across calls");
    }

    @Test
    void testAlertStoreNewAlert() {
        Alert newAlert = new Alert("1", "triggered", System.currentTimeMillis(), "Initial alert");

        alertStorage.alertStore(newAlert);

        assertTrue(testStrategy.storedAlerts.contains(newAlert), "Alert should be stored by the strategy");
        assertEquals(1, alertStorage.alerts.size(), "Alert should be added to the list");
        assertEquals("resolved", newAlert.getCondition(), "Alert condition should be set to 'resolved'");
    }

    @Test
    void testAlertStoreDuplicateAlert() {
        Alert firstAlert = new Alert("1", "triggered", System.currentTimeMillis(), "Initial alert");
        Alert duplicateAlert = new Alert("1", "triggered", System.currentTimeMillis(), "Initial alert");

        alertStorage.alertStore(firstAlert);
        alertStorage.alertStore(duplicateAlert);

        assertEquals(1, testStrategy.storedAlerts.size(), "Only the first alert should be stored by the strategy");
        assertEquals(1, alertStorage.alerts.size(), "Duplicate alerts should not be added");
    }

    @Test
    void testNoDuplicateForDifferentConditions() {
        Alert firstAlert = new Alert("1", "resolved", System.currentTimeMillis(), "Initial alert");
        Alert differentConditionAlert = new Alert("1", "resolved", System.currentTimeMillis(), "Urgent condition");

        alertStorage.alertStore(firstAlert);
        alertStorage.alertStore(differentConditionAlert);

        assertEquals(2, testStrategy.storedAlerts.size(), "Both alerts should be stored by the strategy");
        assertEquals(2, alertStorage.alerts.size(), "Alerts with different conditions should be added");
    }

    /**
     * A simple implementation of AlertStorageStrategy used for testing, which records alerts.
     */
    static class TestAlertStorageStrategy implements AlertStorageStrategy {
        List<Alert> storedAlerts = new ArrayList<>();

        @Override
        public void store(Alert alert) {
            storedAlerts.add(alert);
        }
    }
}
