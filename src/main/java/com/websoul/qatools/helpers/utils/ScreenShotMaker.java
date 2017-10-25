package com.websoul.qatools.helpers.utils;

import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

/**
 * Implementation for ScreenShot generation from webDrivers browser
 * This is used mostly for debug and ticketing purposes
 */
@Service("ScreenShotMaker")
public class ScreenShotMaker {

    private final Logger slf4jLogger = LoggerFactory.getLogger(ScreenShotMaker.class);


    @Autowired
    BrowserDriver browserDriver;

    @Value("#{properties['screenshot_path']}")
    private File screenShotPath;

    /**
     * Generates screenShot file
     *
     * @return ScreenShot file
     */
    public File getScreenShot() throws IOException {
        return ((TakesScreenshot) browserDriver.getCurrentDriver()).getScreenshotAs(OutputType.FILE);
    }
    /**
     * Generates screenShot byte array
     *
     * @return ScreenShot byte array
     */
    public byte[] getScreenShotBytes() throws IOException {
        return ((TakesScreenshot) browserDriver.getCurrentDriver()).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Puts screenShot to specified location (i.e. screenShotPath)
     *
     * @throws IOException
     */
    public void addScreenShotToFile() throws IOException {
        FileUtils.copyFile(getScreenShot(), screenShotPath);
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attachScreenshot() throws IOException {
        return this.getScreenShotBytes();
    }

    @Attachment(value = "{0}", type = "image/png")
    public byte[] captureScreenshot(String attachName) throws IOException {
        return ((TakesScreenshot) browserDriver.getCurrentDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "image/png")
    public void captureScreenshotAndCopy(String attachName, File screenShotPathCustom) throws IOException {
        FileUtils.copyFile(getScreenShot(), screenShotPathCustom);
    }
}
