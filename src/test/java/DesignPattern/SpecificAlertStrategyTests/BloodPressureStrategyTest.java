package DesignPattern.SpecificAlertStrategyTests;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.BloodPressureStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class BloodPressureStrategyTest {
    private BloodPressureStrategy strategy;
    private MockAlertGenerator alertGenerator;
    private List<PatientRecord> patientRecords;

    @BeforeEach
    void setUp() {
        strategy = new BloodPressureStrategy();
        alertGenerator = new MockAlertGenerator();
        patientRecords = new ArrayList<>();
    }

    @Test
    void testIncreasingTrend() {
        patientRecords.add(new PatientRecord(1, 90, "SystolicPressure", 1700000000001L));
        patientRecords.add(new PatientRecord(1, 101, "SystolicPressure", 1700000000002L));
        patientRecords.add(new PatientRecord(1, 112, "SystolicPressure", 1700000000003L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }

    @Test
    void testDecreasingTrend() {
        patientRecords.add(new PatientRecord(1, 100, "SystolicPressure", 1700000000001L));
        patientRecords.add(new PatientRecord(1, 89, "SystolicPressure", 1700000000002L));
        patientRecords.add(new PatientRecord(1, 78, "SystolicPressure", 1700000000003L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }


}
