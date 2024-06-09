package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;

    public PriorityAlertDecorator(AlertInterface decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    public void triggerAlert() {
        System.out.println("Priority: " + priority);
        decoratedAlert.triggerAlert();
    }
}
