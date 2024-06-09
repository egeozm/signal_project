package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ECGStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(List<PatientRecord> patientRecords, AlertGenerator alertGenerator) {
        List<PatientRecord> ecgRecords = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("ECG"))
                .collect(Collectors.toList());

        String currentPatientId = "";
        List<PatientRecord> currentPatientEcgRecords = new ArrayList<>();

        for (PatientRecord record : ecgRecords) {
            String patientId = String.valueOf(record.getPatientId());

            if (!patientId.equals(currentPatientId) && !currentPatientEcgRecords.isEmpty()) {
                processECGRecords(currentPatientId, currentPatientEcgRecords, alertGenerator);
                currentPatientEcgRecords.clear();
            }

            currentPatientId = patientId;
            currentPatientEcgRecords.add(record);
        }

        if (!currentPatientEcgRecords.isEmpty()) {
            processECGRecords(currentPatientId, currentPatientEcgRecords, alertGenerator);
        }
        return false;
    }

    private void processECGRecords(String patientId, List<PatientRecord> patientEcgRecords, AlertGenerator alertGenerator) {
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
                Alert alert = new ECGAlert(patientId, "triggered", System.currentTimeMillis(), "Abnormal Heart Rate Lower Than 50");
                alertGenerator.triggerAlert(alert);
            }
            if (heartRate > 100) {
                Alert alert = new Alert(patientId, "triggered", System.currentTimeMillis(), "Abnormal Heart Rate Higher Than 100");
                alertGenerator.triggerAlert(alert);
            }

            // Irregular Beat Alert
            double averageRRIntervalSum = rrIntervals.stream().mapToDouble(Double::doubleValue).sum() / rrIntervals.size();
            double sumOfSquaredDifferences = rrIntervals.stream().mapToDouble(interval -> Math.pow(interval - averageRRIntervalSum, 2)).sum();
            double standardDeviation = Math.sqrt(sumOfSquaredDifferences / rrIntervals.size());

//            // Debug statement
//            System.out.println("Patient ID: " + patientId + " - Standard Deviation of RR Intervals: " + standardDeviation);

            if (standardDeviation > 0.1) { // Threshold for detecting irregular beats
                Alert alert = new Alert(patientId, "triggered", System.currentTimeMillis(), "Irregular Beat Detected");
                alertGenerator.triggerAlert(alert);
            }
        }
    }
}
