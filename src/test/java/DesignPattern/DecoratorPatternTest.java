package DesignPattern;

import com.alerts.AlertInterface;
import com.alerts.PriorityAlertDecorator;
import com.alerts.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockAlert implements AlertInterface {
    private boolean triggered = false;

    @Override
    public void triggerAlert() {
        triggered = true;
        System.out.println("Alert Triggered");
    }

    public boolean isTriggered() {
        return triggered;
    }

}

public class DecoratorPatternTest {

    @Test
    public void testRepeatedAlertDecorator() {
        MockAlert mockAlert = new MockAlert();
        RepeatedAlertDecorator repeatedAlert = new RepeatedAlertDecorator(mockAlert, 3, 100);


        repeatedAlert.triggerAlert();


        assertTrue(mockAlert.isTriggered());
    }

    @Test
    public void testPriorityAlertDecorator() {
        MockAlert mockAlert = new MockAlert();
        PriorityAlertDecorator priorityAlert = new PriorityAlertDecorator(mockAlert, "High");

        priorityAlert.triggerAlert();

        assertTrue(mockAlert.isTriggered());
        System.out.println("Priority should be: High");
    }
}