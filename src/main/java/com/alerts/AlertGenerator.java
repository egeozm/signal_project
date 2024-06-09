package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;


/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private AlertStorage alertStorage;
    private AlertStrategy alertStrategy;


    public void setAlertStorage(AlertStorage alertStorage) {
        this.alertStorage = alertStorage;
    }

    public void setAlertStrategy(AlertStrategy alertStrategy) {
        this.alertStrategy = alertStrategy;
    }


    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> patientRecords = patient.getRecords(1700000000000L, 1800000000000L);

        checkStrategy(new BloodPressureStrategy(), patientRecords, "BloodPressure");
        checkStrategy(new ECGStrategy(), patientRecords, "ECG");
        checkStrategy(new OxygenSaturationStrategy(), patientRecords, "OxygenSaturation");
        checkStrategy(new HypotensiveHypoxemiaStrategy(), patientRecords, "HypotensiveHypoxemia");
    }


    private void checkStrategy(AlertStrategy strategy, List<PatientRecord> patientRecords, String condition) {
        for (PatientRecord record : patientRecords) {
            if (strategy.checkAlert((List<PatientRecord>) record, this)) {
                String patientId = String.valueOf(record.getPatientId()); // Assume PatientRecord has getPatientId method
                long timestamp = record.getTimestamp();  // Use record's timestamp
                Alert alert = getFactory(condition).createAlert(patientId, condition, timestamp, "Label");
                triggerAlert(alert);
            }
        }
    }


    private AlertFactory getFactory(String condition) {
        switch (condition) {
            case "BloodPressure":
                return new BloodPressureAlertFactory();
            case "OxygenSaturation":
                return new OxygenSaturationAlertFactory();
            case "ECG":
                return new ECGAlertFactory();
            case "HypotensiveHypoxemia":
                return new HypotensiveHypoxemiaAlertFactory();
            default:
                return new BasicAlertFactory();
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    public void triggerAlert(Alert alert) {

        AlertInterface decoratedAlert = new RepeatedAlertDecorator(alert, 3, 1000);
        decoratedAlert = new PriorityAlertDecorator(decoratedAlert, "high");

        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        alertStorage.alertStore((Alert) decoratedAlert);


    }
}
