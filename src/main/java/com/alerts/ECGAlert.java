package com.alerts;

public class ECGAlert extends Alert{
    public ECGAlert(String patientId, String condition, long timestamp, String label) {
        super(patientId, condition, timestamp, label);
    }
}
