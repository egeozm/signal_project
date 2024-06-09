package DesignPattern;

import com.data_management.DataStorage;
import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SingletonPatternTest {

    @Test
    public void testDataStorageSingleton() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        // Assert that both instances are the same
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    public void testHealthDataSimulatorSingleton() {
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();

        // Assert that both instances are the same
        assertSame(instance1, instance2, "Both instances should be the same");
    }
}