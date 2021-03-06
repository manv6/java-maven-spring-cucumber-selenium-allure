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
        plugin = {"ru.yandex.qatools.allure.cucumberjvm.AllureReporter",
                "com.epam.reportportal.cucumber.ScenarioReporter",
                "json",
                "json:target/cucumber.json"}
)
public class CucumberExecutorTest {

}
