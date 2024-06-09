package com.alerts;

public class BasicAlert extends Alert {
    public BasicAlert(String patientId, String condition, long timestamp, String label) {
        super(patientId, condition, timestamp, label);
    }

    @Override
    public void triggerAlert() {
        System.out.println("Basic Alert: " + getCondition());
    }
}