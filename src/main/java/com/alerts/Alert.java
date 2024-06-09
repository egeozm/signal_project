package com.alerts;

import java.util.Objects;

// Represents an alert
public class Alert implements AlertInterface {
    private final String patientId;
    private String condition;
    private final long timestamp;
    private final String label;

    public Alert(String patientId, String condition, long timestamp, String label) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
        this.label = label;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(patientId, alert.patientId) && Objects.equals(condition, alert.condition) && Objects.equals(label, alert.label);
    }

    @Override
    public void triggerAlert() {

    }
}
