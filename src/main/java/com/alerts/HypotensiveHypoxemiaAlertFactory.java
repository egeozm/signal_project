package com.alerts;

public class HypotensiveHypoxemiaAlertFactory extends AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp, String label) {
        return new HypotensiveHypoxemiaAlert(patientId, condition, timestamp, label);
    }
}
