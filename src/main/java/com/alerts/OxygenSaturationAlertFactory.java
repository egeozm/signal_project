package com.alerts;

public class OxygenSaturationAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp, String label) {
        return new OxygenSaturationAlert(patientId, condition, timestamp, label);
    }
}
