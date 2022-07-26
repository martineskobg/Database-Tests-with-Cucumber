package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Standard runner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/Cucumber.feature",
        glue = "step.definitions",
        plugin = {"pretty"},
        tags = "@customersAndAddresses"
)
public class TestRunner {
}