package alerts;

import com.alerts.Alert;
import com.alerts.ConsoleAlertStorageStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ConsoleAlertStorageStrategy.
 */
public class ConsoleAlertStorageStrategyTest {
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(errContent);
    private ConsoleAlertStorageStrategy storageStrategy;

    @BeforeEach
    void setUp() {
        // Redirect System.err to capture outputs
        System.setErr(printStream);
        storageStrategy = new ConsoleAlertStorageStrategy();
    }

    @AfterEach
    void restoreStreams() {
        // Restore original System.err stream
        System.setErr(originalErr);
    }

    @Test
    void testStorePrintsCorrectOutput() {
        // Given
        Alert alert = new Alert("123", "Critical", 1590300000000L, "Emergency");

        // When
        storageStrategy.store(alert);

        // Then
        String expectedOutput = "ALERT: PatientId = 123 Condition: Triggered timestamp: 1590300000000 Label: Emergency\n";
        printStream.flush();
        String actualOutput = errContent.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Output should contain the expected text");
    }
}
