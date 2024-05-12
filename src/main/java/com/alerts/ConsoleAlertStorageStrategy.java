package com.alerts;

public class ConsoleAlertStorageStrategy implements AlertStorageStrategy {

    @Override
    public void store(Alert alert) {
        System.err.println(String.format("ALERT: PatientId = %s Condition: Triggered timestamp: %d Label: %s",
                alert.getPatientId(),
                alert.getTimestamp(),
                alert.getLabel()));
    }
}
