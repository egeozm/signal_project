package com.alerts;

public class BasicAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp, String label) {
        return new BasicAlert(patientId, condition, timestamp, label);
    }
}