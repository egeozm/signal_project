package DesignPattern.SpecificAlertStrategyTests;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.OxygenSaturationStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OxygenSaturationStrategyTest {
    private OxygenSaturationStrategy strategy;
    private MockAlertGenerator alertGenerator;
    private List<PatientRecord> patientRecords;

    @BeforeEach
    void setUp() {
        strategy = new OxygenSaturationStrategy();
        alertGenerator = new MockAlertGenerator();
        patientRecords = new ArrayList<>();
    }

    @Test
    void testCriticalDrop() {
        patientRecords.add(new PatientRecord(1, 98, "Saturation", 1715285596092L));
        patientRecords.add(new PatientRecord(1, 93, "Saturation", 1715285599000L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }

    @Test
    void testLowSaturation() {
        patientRecords.add(new PatientRecord(1, 91, "Saturation", 1715285596092L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }
}
