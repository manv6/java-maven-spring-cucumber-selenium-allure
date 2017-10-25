package com.websoul.qatools.suite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Test Runner for cucumber scenarios
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/com/websoul/qatools/scenarios/",
        glue = {"com.websoul.qatools.steps"},
        plugin = {"com.websoul.qatools.helpers.reporters.AllureReporter", "com.epam.reportportal.cucumber.StepReporter",
                "json", "json:target/cucumber.json"}
)
public class CucumberExecutorTest {

}
