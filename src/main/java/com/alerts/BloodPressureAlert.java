package com.alerts;

public class BloodPressureAlert extends Alert {

    public BloodPressureAlert(String patientId, String condition, long timestamp, String label) {
        super(patientId, condition, timestamp, label);
    }
}
