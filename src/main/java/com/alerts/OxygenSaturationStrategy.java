package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;
import java.util.stream.Collectors;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(List<PatientRecord> patientRecords, AlertGenerator alertGenerator) {
        // Saturation Level Alert
        List<PatientRecord> patientsSaturationLevel = patientRecords.stream()
                .filter(x -> x.getRecordType().equals("Saturation"))
                .collect(Collectors.toList());

        // Low Saturation Alert
        for (PatientRecord patientRecord : patientsSaturationLevel) {

            if (patientRecord.getMeasurementValue() < 92) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Saturation level lower than 92.0%");
                alertGenerator.triggerAlert(alert);

            }
        }

        // Rapid Drop Alert
        for (int i = 0; i < patientsSaturationLevel.size() - 1; i++) {

            if (patientsSaturationLevel.get(i + 1).getTimestamp() - patientsSaturationLevel.get(i).getTimestamp() < 600000) {

                if (patientsSaturationLevel.get(i).getMeasurementValue() - patientsSaturationLevel.get(i + 1).getMeasurementValue() >= 5) {
                    Alert alert = new Alert(String.valueOf(patientsSaturationLevel.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Rapid Saturation Drop Alert");
                    alertGenerator.triggerAlert(alert);

                }
            }
        }
        return false;
    }
}
