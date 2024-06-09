package com.alerts;

public abstract class AlertDecorator implements AlertInterface{
    protected AlertInterface decoratedAlert;

    public AlertDecorator(AlertInterface decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public void triggerAlert() {
        decoratedAlert.triggerAlert();
    }
}
