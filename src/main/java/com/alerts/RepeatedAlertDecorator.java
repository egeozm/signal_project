package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;
    private long interval;

    public RepeatedAlertDecorator(AlertInterface decoratedAlert, int repeatCount, long interval) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
        this.interval = interval;
    }

    public void triggerAlert() {
        for (int i = 0; i < repeatCount; i++) {
            decoratedAlert.triggerAlert();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
