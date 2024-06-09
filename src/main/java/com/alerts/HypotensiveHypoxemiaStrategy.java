package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;
import java.util.stream.Collectors;

public class HypotensiveHypoxemiaStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(List<PatientRecord> patientRecords, AlertGenerator alertGenerator) {
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
                alertGenerator.triggerAlert(alert);

            }
        }
        return saturationAlert;
    }
}
