package com.workable.hackathon.helpers.listeners;

import com.workable.hackathon.helpers.drivers.browsers.CucumberTestContext;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Attachment;

/**
 * Created by manolisvlastos on 21/10/16.
 */
public class CucumberListener extends RunListener{

    private final Logger slf4jLogger = LoggerFactory.getLogger(CucumberListener.class);

    @Override
    public void testFailure(Failure failure) {
        if (CucumberTestContext.getWebDriver() != null) {
            try {
//                captureScreenshot(failure.getTestHeader(), CucumberTestContext.getWebDriver());
            } catch (Exception e) {
                slf4jLogger.error(e.getMessage());

            }
        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public byte[] captureScreenshot(String attachName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}