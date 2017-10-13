package info.jmscsa.steps;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = 
			{"json", "json:target/cucumber.json"},
			//{"html:target/cucumberHtmlReport"},     //  for html result
			//{pretty:target/cucumber-json-report.json},  // for json result
		features = "classpath:features",
		glue = {"info.seleniumcucumber.stepdefinitions",   // predefined step definitions package
				"info.jmscsa.steps" // user step definitions package
			   }
)

public class CucumberExecutorTest{
}
