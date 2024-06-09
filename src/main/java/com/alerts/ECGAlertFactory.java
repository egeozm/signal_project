package com.alerts;

public class ECGAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp, String label) {
        return new ECGAlert(patientId, condition, timestamp, label);
    }
}
