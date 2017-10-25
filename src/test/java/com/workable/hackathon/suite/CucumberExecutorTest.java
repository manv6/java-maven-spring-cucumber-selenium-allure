package com.workable.hackathon.suite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Test Runner for cucumber scenarios
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/com/workable/wolverine/suite/scenarios/",
        glue = {"com.workable.hackathon.steps"},
        plugin = {"com.workable.hackathon.common.reporters.AllureReporter", "com.epam.reportportal.cucumber.StepReporter",
                "json", "json:target/cucumber.json"}
)
public class CucumberExecutorTest {

}
