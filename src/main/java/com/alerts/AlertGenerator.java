package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                break;
            }
            if (patientsBloodPressure.get(i + 1).getMeasurementValue() < patientsBloodPressure.get(i).getMeasurementValue() - 10
                    && patientsBloodPressure.get(i + 2).getMeasurementValue() < patientsBloodPressure.get(i + 1).getMeasurementValue() - 10) {
                Alert alert = new Alert(String.valueOf(patientsBloodPressure.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Decreasing Blood Pressure Trend Problem");
                triggerAlert(alert);
                break;
            }
        }

        // Threshold Alert
        for (PatientRecord patientRecord : patientsDiastolicPressure) {
            if (patientRecord.getMeasurementValue() > 120) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure higher than 120");
                triggerAlert(alert);
                break;
            }
            if (patientRecord.getMeasurementValue() < 60) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure lower than 60");
                triggerAlert(alert);
                break;
            }
        }

        for (PatientRecord patientRecord : patientsSystolicPressure) {
            if (patientRecord.getMeasurementValue() > 180) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure higher than 180");
                triggerAlert(alert);
                break;
            }

            if (patientRecord.getMeasurementValue() < 90) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure lower than 90");
                triggerAlert(alert);
                break;
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
                break;
            }
        }

        // Rapid Drop Alert
        for (int i = 0; i < patientsSaturationLevel.size() - 1; i++) {

            if (patientsSaturationLevel.get(i + 1).getTimestamp() - patientsSaturationLevel.get(i).getTimestamp() < 600000) {

                if (patientsSaturationLevel.get(i).getMeasurementValue() - patientsSaturationLevel.get(i + 1).getMeasurementValue() >= 5) {
                    Alert alert = new Alert(String.valueOf(patientsSaturationLevel.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Rapid Saturation Drop Alert");
                    triggerAlert(alert);
                    break;
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

            if (patientRecord.getMeasurementValue() < 92) {
                saturationAlert = true;
            }

            if (patientRecord.getMeasurementValue() < 90) {
                systolicAlert = true;
            }

            if (saturationAlert && systolicAlert) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Hypotensive Hypoxemia Alert");
                triggerAlert(alert);
                break;
            }
        }
    }

    // I used help from AI only for converting ECG to BPM, and I am sure that it's wrong.
    // I do not know how to fix it because I did not understand the logic of converting process.
    private void evaluateECG(List<PatientRecord> patientRecords) {
        List<PatientRecord> patientECG = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("ECG"))
                .collect(Collectors.toList());

        Map<Integer, List<PatientRecord>> ecgRecordsByPatient = patientECG.stream()
                .collect(Collectors.groupingBy(PatientRecord::getPatientId));

        ecgRecordsByPatient.forEach((patientId, records) -> {
            List<Long> peakTimes = detectPeaks(records);
            if (!peakTimes.isEmpty()) {
                double heartRate = calculateHeartRate(peakTimes);
                if (heartRate < 50) {
                    triggerAlert(new Alert(String.valueOf(patientId), "triggered", System.currentTimeMillis(), "Heart Rate Lower Than 50 BPM"));
                } else if (heartRate > 100) {
                    triggerAlert(new Alert(String.valueOf(patientId), "triggered", System.currentTimeMillis(), "Heart Rate Higher Than 100 BPM"));
                }
                if (detectIrregularBeats(peakTimes)) {
                    triggerAlert(new Alert(String.valueOf(patientId), "triggered", System.currentTimeMillis(), "Irregular Beat Alert"));
                }
            }
        });
    }

    private double calculateThreshold(List<PatientRecord> records) {
        if (records.isEmpty()) {
            return 0;
        }

        double mean = records.stream()
                .mapToDouble(r -> Double.parseDouble(String.valueOf(r.getMeasurementValue())))
                .average()
                .orElse(0.0);

        double standardDeviation = calculateStandardDeviation(records, mean);

        return mean + standardDeviation * 0.5;
    }

    private double calculateStandardDeviation(List<PatientRecord> records, double mean) {
        double sumOfSquaredDifferences = records.stream()
                .mapToDouble(r -> Double.parseDouble(String.valueOf(r.getMeasurementValue())))
                .map(value -> Math.pow(value - mean, 2))
                .sum();

        int n = records.size();
        return Math.sqrt(sumOfSquaredDifferences / n);
    }


    private List<Long> detectPeaks(List<PatientRecord> records) {
        List<Long> peakTimes = new ArrayList<>();
        double threshold = calculateThreshold(records);

        for (PatientRecord record : records) {
            double value = Double.parseDouble(String.valueOf(record.getMeasurementValue()));
            if (value > threshold) {
                peakTimes.add(record.getTimestamp());
            }
        }
        return peakTimes;
    }

    private double calculateHeartRate(List<Long> peakTimes) {
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakTimes.size(); i++) {
            double interval = (peakTimes.get(i) - peakTimes.get(i - 1)) / 1000.0; // convert ms to seconds
            intervals.add(interval);
        }
        double averageInterval = intervals.stream().mapToDouble(val -> val).average().orElse(0.0);
        return 60 / averageInterval; // Calculate BPM
    }

    private boolean detectIrregularBeats(List<Long> peakTimes) {
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakTimes.size(); i++) {
            intervals.add((peakTimes.get(i) - peakTimes.get(i - 1)) / 1000.0); // convert ms to seconds
        }
        double meanInterval = intervals.stream().mapToDouble(val -> val).average().orElse(Double.MAX_VALUE);
        double variance = intervals.stream().mapToDouble(i -> (i - meanInterval) * (i - meanInterval)).average().orElse(0);
        double sd = Math.sqrt(variance);


        return intervals.stream().anyMatch(i -> Math.abs(i - meanInterval) > 2 * sd);
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
