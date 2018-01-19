package com.websoul.qatools.helpers.listeners;

import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import com.websoul.qatools.helpers.drivers.browsers.CucumberTestContext;
import cucumber.api.Scenario;
import io.qameta.allure.Attachment;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by manolisvlastos on 21/10/16.
 */
public class CucumberListener extends RunListener {

    private final Logger slf4jLogger = LoggerFactory.getLogger(CucumberListener.class);

    @Autowired
    BrowserDriver browserDriver;
    private Scenario scenario;

    @cucumber.api.java.Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void testFailure(Failure failure) throws IOException {

        try {
            captureScreenshot(failure.getTestHeader(), CucumberTestContext.getWebDriver());
        } catch (Exception e) {
            slf4jLogger.error(e.getMessage());

        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public byte[] captureScreenshot(String attachName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}