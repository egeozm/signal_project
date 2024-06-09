package com.alerts;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp, String label);
}
