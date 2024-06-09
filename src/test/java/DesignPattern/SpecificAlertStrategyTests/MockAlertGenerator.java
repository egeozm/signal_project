package DesignPattern.SpecificAlertStrategyTests;

import com.alerts.Alert;
import com.alerts.AlertGenerator;

class MockAlertGenerator extends AlertGenerator {
    private boolean alertTriggered = false;

    @Override
    public void triggerAlert(Alert alert) {
        alertTriggered = true;
    }

    public boolean isAlertTriggered() {
        return alertTriggered;
    }
}
