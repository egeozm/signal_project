package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;
import java.util.stream.Collectors;

public class BloodPressureStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(List<PatientRecord> patientRecords, AlertGenerator alertGenerator) {
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
                Alert alert = new BloodPressureAlert(String.valueOf(patientsBloodPressure.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Increasing Blood Pressure Trend Problem");
                alertGenerator.triggerAlert(alert);

            }
            if (patientsBloodPressure.get(i + 1).getMeasurementValue() < patientsBloodPressure.get(i).getMeasurementValue() - 10
                    && patientsBloodPressure.get(i + 2).getMeasurementValue() < patientsBloodPressure.get(i + 1).getMeasurementValue() - 10) {
                Alert alert = new Alert(String.valueOf(patientsBloodPressure.get(i).getPatientId()), "triggered", System.currentTimeMillis(), "Decreasing Blood Pressure Trend Problem");
                alertGenerator.triggerAlert(alert);

            }
        }

        // Threshold Alert
        for (PatientRecord patientRecord : patientsDiastolicPressure) {
            if (patientRecord.getMeasurementValue() > 120) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure higher than 120");
                alertGenerator.triggerAlert(alert);

            }
            if (patientRecord.getMeasurementValue() < 60) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Diastolic Pressure lower than 60");
                alertGenerator.triggerAlert(alert);
            }
        }

        for (PatientRecord patientRecord : patientsSystolicPressure) {
            if (patientRecord.getMeasurementValue() > 180) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure higher than 180");
                alertGenerator.triggerAlert(alert);

            }

            if (patientRecord.getMeasurementValue() < 90) {
                Alert alert = new Alert(String.valueOf(patientRecord.getPatientId()), "triggered", System.currentTimeMillis(), "Systolic Pressure lower than 90");
                alertGenerator.triggerAlert(alert);
            }
        }
        return false;
    }
}
