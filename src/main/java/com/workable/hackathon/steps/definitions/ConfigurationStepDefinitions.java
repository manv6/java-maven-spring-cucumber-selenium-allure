package com.workable.hackathon.steps.definitions;

import com.automation.remarks.junit.VideoRule;
import com.workable.hackathon.helpers.context.cache.InitialCachedData;
import com.workable.hackathon.helpers.context.cache.RuntimeCachedData;
import com.workable.hackathon.helpers.context.impl.CachedDataContextImpl;
import com.workable.hackathon.helpers.drivers.browsers.BrowserCachedData;
import com.workable.hackathon.helpers.drivers.browsers.BrowserDriver;
import com.workable.hackathon.helpers.drivers.browsers.Browsers;
import com.workable.hackathon.helpers.drivers.pagecontrollers.impl.GenericPageControllerImpl;
import com.workable.hackathon.helpers.utils.CommonTools;
import com.workable.hackathon.helpers.utils.ScreenShotMaker;
import com.workable.hackathon.helpers.utils.TestUtils;
import com.workable.hackathon.helpers.utils.VideoMaker;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.junit.Rule;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.IOException;


public class ConfigurationStepDefinitions {

    private Scenario scenario;
    @Autowired
    CachedDataContextImpl cachedDataContext;
    @Autowired
    GenericPageControllerImpl genericPageController;
    @Autowired
    CommonTools commonTools;
    @Autowired
    BrowserDriver browserDriver;
    @Autowired
    RuntimeCachedData runtimeCachedData;
    @Autowired
    BrowserCachedData browserCachedData;
    @Autowired
    ScreenShotMaker screenShotMaker;
    @Autowired
    TestUtils testUtils;
    @Autowired
    VideoMaker videoMaker;

    private final Logger slf4jLogger = LoggerFactory.getLogger(ConfigurationStepDefinitions.class);

    @Rule
    public VideoRule videoRule = new VideoRule();


    @Before
    public void before(Scenario scenario) throws IOException, AWTException {
        this.scenario = scenario;
    }

    @Value("#{properties['record_video']}")
    private String record_video;

    @Given("^I'm using browser \"([^\"]*)\"$")
    public void I_m_using_browser(String browser) throws Throwable {
        InitialCachedData initialCachedData = (InitialCachedData) cachedDataContext.getObject("initialCachedData");
        initialCachedData.setBrowser(Browsers.getBrowserForName(browser.toUpperCase()));
        genericPageController.initializeElementsForAllPages();
        if (browser.equalsIgnoreCase("remote"))
            commonTools.getVideoLinK(browserCachedData.getVideoLink());
        else if (record_video.equals("true")) {
            videoMaker.initialize();
            videoMaker.start();
            slf4jLogger.info("Video mode true...initializing VideoMaker");
        } else
            slf4jLogger.info("No video needed");
    }

    @And("^Navigate to url \"([^\"]*)\"$")
    public void navigateToUrl(String url) throws Throwable {
        browserDriver.getCurrentDriver().navigate().to(url);
    }

    @After
    public void after() throws IOException {
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) browserDriver.getCurrentDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // ... and embed it in the report.
        }
        if (record_video.equals("true")) {
            videoMaker.stop();
            slf4jLogger.info("Video mode true...VideoMaker stopped");
        }
       // TestUtils.runRule(videoRule,this,"after");
    }
}

