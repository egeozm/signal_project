package com.alerts;

public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp, String label) {
        return new BloodPressureAlert(patientId, condition, timestamp, label);
    }
}
