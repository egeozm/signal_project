package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
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
        evaluateBloodPressure(patientRecords);
        evaluateOxygenSaturation(patientRecords);
        evaluateHypotensiveHypoxemia(patientRecords);
        evaluateECG(patientRecords);
    }

    private void evaluateBloodPressure(List<PatientRecord> patientRecords) {
        List<PatientRecord> patientsBloodPressure = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("DiastolicPressure") || x.getRecordType().equals("SystolicPressure"))
                .collect(Collectors.toList());

        List<PatientRecord> patientsDiastolicPressure = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("DiastolicPressure"))
                .collect(Collectors.toList());

        List<PatientRecord> patientsSystolicPressure = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("SystolicPressure"))
                .collect(Collectors.toList());

        // Trend Alert
        for (int i = 0; i < patientsBloodPressure.size() - 2; i++) {
            if (patientsBloodPressure.get(i + 1).getMeasurementValue() > patientsBloodPressure.get(i).getMeasurementValue() + 10
                    && patientsBloodPressure.get(i + 2).getMeasurementValue() > patientsBloodPressure.get(i + 1).getMeasurementValue() + 10) {
                Alert alert = new Alert(String.valueOf(patientsBloodPressure.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Increasing Blood Pressure Trend Problem");
                triggerAlert(alert);

            }
            if (patientsBloodPressure.get(i + 1).getMeasurementValue() < patientsBloodPressure.get(i).getMeasurementValue() - 10
                    && patientsBloodPressure.get(i + 2).getMeasurementValue() < patientsBloodPressure.get(i + 1).getMeasurementValue() - 10) {
                Alert alert = new Alert(String.valueOf(patientsBloodPressure.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Decreasing Blood Pressure Trend Problem");
                triggerAlert(alert);

            }
        }

        // Threshold Alert
        for (PatientRecord patientRecord : patientsDiastolicPressure) {
            if (patientRecord.getMeasurementValue() > 120) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure higher than 120");
                triggerAlert(alert);

            }
            if (patientRecord.getMeasurementValue() < 60) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure lower than 60");
                triggerAlert(alert);
            }
        }

        for (PatientRecord patientRecord : patientsSystolicPressure) {
            if (patientRecord.getMeasurementValue() > 180) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure higher than 180");
                triggerAlert(alert);

            }

            if (patientRecord.getMeasurementValue() < 90) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure lower than 90");
                triggerAlert(alert);
            }
        }
    }

    private void evaluateOxygenSaturation(List<PatientRecord> patientRecords) {
        // Saturation Level Alert
        List<PatientRecord> patientsSaturationLevel = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("Saturation"))
                .collect(Collectors.toList());

        // Low Saturation Alert
        for (PatientRecord patientRecord : patientsSaturationLevel) {

            if (patientRecord.getMeasurementValue() < 92) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Saturation level lower than 92.0%");
                triggerAlert(alert);

            }
        }

        // Rapid Drop Alert
        for (int i = 0; i < patientsSaturationLevel.size() - 1; i++) {

            if (patientsSaturationLevel.get(i + 1).getTimestamp() - patientsSaturationLevel.get(i).getTimestamp() < 600000) {

                if (patientsSaturationLevel.get(i).getMeasurementValue() - patientsSaturationLevel.get(i + 1).getMeasurementValue() >= 5) {
                    Alert alert = new Alert(String.valueOf(patientsSaturationLevel.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Rapid Saturation Drop Alert");
                    triggerAlert(alert);

                }
            }
        }
    }

    private void evaluateHypotensiveHypoxemia(List<PatientRecord> patientRecords) {
        // Hypotensive Hypoxemia Alert
        List<PatientRecord> patientsSaturationSystolicPressure = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("Saturation") || x.getRecordType().equals("SystolicPressure"))
                .collect(Collectors.toList());

        boolean saturationAlert = false;
        boolean systolicAlert = false;

        for (PatientRecord patientRecord : patientsSaturationSystolicPressure) {

            if (patientRecord.getRecordType().equals("Saturation") && patientRecord.getMeasurementValue() < 92) {
                saturationAlert = true;
            }

            if (patientRecord.getRecordType().equals("SystolicPressure") && patientRecord.getMeasurementValue() < 90) {
                systolicAlert = true;
            }

            if (saturationAlert && systolicAlert) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Hypotensive Hypoxemia Alert");
                triggerAlert(alert);

            }
        }
    }

    private void evaluateECG(List<PatientRecord> patientRecords) {
        List<PatientRecord> ecgRecords = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("ECG"))
                .collect(Collectors.toList());

        String currentPatientId = "";
        List<PatientRecord> currentPatientEcgRecords = new ArrayList<>();

        for (PatientRecord record : ecgRecords) {
            String patientId = String.valueOf(record.getPatientId());

            if (!patientId.equals(currentPatientId) && !currentPatientEcgRecords.isEmpty()) {
                processECGRecords(currentPatientId, currentPatientEcgRecords);
                currentPatientEcgRecords.clear();
            }

            currentPatientId = patientId;
            currentPatientEcgRecords.add(record);
        }

        if (!currentPatientEcgRecords.isEmpty()) {
            processECGRecords(currentPatientId, currentPatientEcgRecords);
        }
    }

    private void processECGRecords(String patientId, List<PatientRecord> patientEcgRecords) {
        double totalRRInterval = 0.0;
        int beatCount = 0;
        List<Double> rrIntervals = new ArrayList<>();

        for (int i = 1; i < patientEcgRecords.size(); i++) {
            double previousData = patientEcgRecords.get(i - 1).getMeasurementValue();
            double currentData = patientEcgRecords.get(i).getMeasurementValue();
            if (previousData < 0 && currentData > 0) {
                // Detected a beat (R peak crossing zero points)
                long previousTimestamp = patientEcgRecords.get(i - 1).getTimestamp();
                long currentTimestamp = patientEcgRecords.get(i).getTimestamp();
                double rrInterval = (currentTimestamp - previousTimestamp) / 1000.0; // Convert ms to seconds
                rrIntervals.add(rrInterval);
                totalRRInterval += rrInterval;
                beatCount++;
            }
        }

        if (beatCount > 0) {
            double averageRRInterval = totalRRInterval / beatCount;
            double heartRate = 60 / averageRRInterval; // Calculate heart rate in beats per minute

//            // Debug statements
//            System.out.println("Patient ID: " + patientId + " - Total RR Interval: " + totalRRInterval);
//            System.out.println("Patient ID: " + patientId + " - Beat Count: " + beatCount);
//            System.out.println("Patient ID: " + patientId + " - Average RR Interval (seconds): " + averageRRInterval);
//            System.out.println("Patient ID: " + patientId + " - Calculated Heart Rate (BPM): " + heartRate);

            // Abnormal Heart Rate Alert
            if (heartRate < 50) {
                Alert alert = new Alert(patientId, "triggered", System.currentTimeMillis(), "Abnormal Heart Rate Lower Than 50");
                triggerAlert(alert);
            }
            if (heartRate > 100) {
                Alert alert = new Alert(patientId, "triggered", System.currentTimeMillis(), "Abnormal Heart Rate Higher Than 100");
                triggerAlert(alert);
            }

            // Irregular Beat Alert
            double averageRRIntervalSum = rrIntervals.stream().mapToDouble(Double::doubleValue).sum() / rrIntervals.size();
            double sumOfSquaredDifferences = rrIntervals.stream().mapToDouble(interval -> Math.pow(interval - averageRRIntervalSum, 2)).sum();
            double standardDeviation = Math.sqrt(sumOfSquaredDifferences / rrIntervals.size());

//            // Debug statement
//            System.out.println("Patient ID: " + patientId + " - Standard Deviation of RR Intervals: " + standardDeviation);

            if (standardDeviation > 0.1) { // Threshold for detecting irregular beats
                Alert alert = new Alert(patientId, "triggered", System.currentTimeMillis(), "Irregular Beat Detected");
                triggerAlert(alert);
            }
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
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();
        alertStorage.alertStore(alert);

    }
}
