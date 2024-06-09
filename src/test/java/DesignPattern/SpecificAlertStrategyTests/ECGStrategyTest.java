package DesignPattern.SpecificAlertStrategyTests;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.ECGStrategy;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class ECGStrategyTest {
    private ECGStrategy strategy;
    private MockAlertGenerator alertGenerator;
    private List<PatientRecord> patientRecords;

    @BeforeEach
    void setUp() {
        strategy = new ECGStrategy();
        alertGenerator = new MockAlertGenerator();
        patientRecords = new ArrayList<>();
    }

    @Test
    void testIrregularBeat() {
        patientRecords.add(new PatientRecord(1, -0.5, "ECG", 1700000000000L));
        patientRecords.add(new PatientRecord(1, 1.1, "ECG", 1700000001000L));
        patientRecords.add(new PatientRecord(1, -0.4, "ECG", 1700000003000L));
        patientRecords.add(new PatientRecord(1, 1.0, "ECG", 1700000003500L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }

    @Test
    void testLowHeartRateAlert() {
        patientRecords.add(new PatientRecord(1, -0.5, "ECG", 1700000000000L));
        patientRecords.add(new PatientRecord(1, 1.2, "ECG", 1700000001000L));
        patientRecords.add(new PatientRecord(1, -0.4, "ECG", 1700000003000L));
        patientRecords.add(new PatientRecord(1, 1.3, "ECG", 1700000003500L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }

    @Test
    void testHighHeartRateAlert() {
        patientRecords.add(new PatientRecord(1, -0.5, "ECG", 1700000000000L));
        patientRecords.add(new PatientRecord(1, 1.1, "ECG", 1700000000500L));
        patientRecords.add(new PatientRecord(1, -0.4, "ECG", 1700000001000L));
        patientRecords.add(new PatientRecord(1, 1.0, "ECG", 1700000001500L));
        patientRecords.add(new PatientRecord(1, -0.6, "ECG", 1700000002000L));
        patientRecords.add(new PatientRecord(1, 1.2, "ECG", 1700000002500L));

        strategy.checkAlert(patientRecords, alertGenerator);

        assertTrue(alertGenerator.isAlertTriggered());
    }
}
