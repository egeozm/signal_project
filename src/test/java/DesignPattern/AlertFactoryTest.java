package DesignPattern;

import com.alerts.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("patient1", "High Blood Pressure", System.currentTimeMillis(), "BP Alert");

        assertTrue(alert instanceof BloodPressureAlert);
        assertEquals("High Blood Pressure", alert.getCondition());
    }

    @Test
    public void testOxygenSaturationAlertFactory() {
        AlertFactory factory = new OxygenSaturationAlertFactory();
        Alert alert = factory.createAlert("patient2", "Low Oxygen Saturation", System.currentTimeMillis(), "O2 Alert");

        assertTrue(alert instanceof OxygenSaturationAlert);
        assertEquals("Low Oxygen Saturation", alert.getCondition());
    }

    @Test
    public void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("patient3", "Irregular Heart Rate", System.currentTimeMillis(), "ECG Alert");

        assertTrue(alert instanceof ECGAlert);
        assertEquals("Irregular Heart Rate", alert.getCondition());
    }

    @Test
    public void testBasicAlertFactory() {
        AlertFactory factory = new BasicAlertFactory();
        Alert alert = factory.createAlert("patient4", "General Alert", System.currentTimeMillis(), "Basic Alert");

        assertTrue(alert instanceof BasicAlert);
        assertEquals("General Alert", alert.getCondition());
    }
}