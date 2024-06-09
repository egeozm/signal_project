package DesignPattern.SpecificAlertStrategyTests;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.HypotensiveHypoxemiaStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class HypotensiveHypoxemiaStrategyTest {
    private HypotensiveHypoxemiaStrategy strategy;
    private MockAlertGenerator alertGenerator;
    private List<PatientRecord> patientRecords;

    @BeforeEach
    void setUp() {
        strategy = new HypotensiveHypoxemiaStrategy();
        alertGenerator = new MockAlertGenerator();
        patientRecords = new ArrayList<>();
    }

    @Test
    void testHypotensiveHypoxemiaAlert() {
        patientRecords.add(new PatientRecord(1, 91, "Saturation", 1715285596092L));
        patientRecords.add(new PatientRecord(1, 80, "SystolicPressure", 1715086124171L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }

}
