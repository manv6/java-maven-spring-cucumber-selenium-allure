package com.websoul.qatools.steps.definitions;

import com.automation.remarks.junit.VideoRule;
import com.websoul.qatools.helpers.context.cache.InitialCachedData;
import com.websoul.qatools.helpers.context.cache.RuntimeCachedData;
import com.websoul.qatools.helpers.context.impl.CachedDataContextImpl;
import com.websoul.qatools.helpers.drivers.browsers.BrowserCachedData;
import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import com.websoul.qatools.helpers.drivers.browsers.Browsers;
import com.websoul.qatools.helpers.drivers.pagecontrollers.impl.GenericPageControllerImpl;
import com.websoul.qatools.helpers.utils.CommonTools;
import com.websoul.qatools.helpers.utils.ScreenShotMaker;
import com.websoul.qatools.helpers.utils.TestUtils;
import com.websoul.qatools.helpers.utils.VideoMaker;
import cucumber.api.PendingException;
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

import static junit.framework.TestCase.fail;


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
            scenario.embed(screenShotMaker.captureScreenshot(scenario.getName()),"image/png");
        }
        if (record_video.equals("true")) {
            videoMaker.stop();
            slf4jLogger.info("Video mode true...VideoMaker stopped");
        }
    }

    @And("^Make scenario fail$")
    public void makeScenarioFail() throws Throwable {
        fail("Reason of fail");
    }
}

