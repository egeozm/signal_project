package com.alerts;

import java.util.ArrayList;
import java.util.List;

public class AlertStorage {
    private static AlertStorage alertStorageInstance = null;
    private final AlertStorageStrategy alertStorageStrategy;
    public final List<Alert> alerts = new ArrayList<>();

    public AlertStorage() {
        alertStorageStrategy = new ConsoleAlertStorageStrategy();
    }

    public AlertStorage(AlertStorageStrategy alertStorageStrategy) {
        this.alertStorageStrategy = alertStorageStrategy;

    }

    public static void setAlertStorageInstance(AlertStorage alertStorageInstance) {
        AlertStorage.alertStorageInstance = alertStorageInstance;
    }

    public static AlertStorage getAlertStorageInstance() {
        if (alertStorageInstance == null) {
            alertStorageInstance = new AlertStorage();
        }
        return alertStorageInstance;
    }

    public AlertStorageStrategy getAlertStorageStrategy() {
        return alertStorageStrategy;
    }

    public void alertStore(Alert alert) {
        boolean patientAlreadyTriggered = alerts.stream()
                .anyMatch(x -> x.getPatientId()
                        .equals(alert.getPatientId())
                        && x.getCondition().equals("resolved")
                        && x.getLabel().equals(alert.getLabel()));

        if (!patientAlreadyTriggered) {
            alert.setCondition("resolved");
            alerts.add(alert);
            alertStorageStrategy.store(alert);
        }

    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
