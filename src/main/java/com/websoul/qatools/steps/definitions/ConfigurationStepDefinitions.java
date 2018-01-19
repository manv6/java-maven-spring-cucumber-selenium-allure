package com.websoul.qatools.steps.definitions;

import com.automation.remarks.video.recorder.monte.MonteRecorder;
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
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.qameta.allure.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


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


    @Value("#{video['video.enabled']}")
    private String record_video;

    public MonteRecorder monteRecorder = new MonteRecorder();


    @Before
    public void before(Scenario scenario) throws IOException, AWTException {
        this.scenario = scenario;
        if (record_video.equals("true")) {
            slf4jLogger.info("Video mode true...VideoMaker started");
            monteRecorder.start();
        }
    }


    @Given("^I'm using browser \"([^\"]*)\"$")
    public void I_m_using_browser(String browser) throws Throwable {
        InitialCachedData initialCachedData = (InitialCachedData) cachedDataContext.getObject("initialCachedData");
        initialCachedData.setBrowser(Browsers.getBrowserForName(browser.toUpperCase()));
        genericPageController.initializeElementsForAllPages();
        if (browser.equalsIgnoreCase("remote")) commonTools.getVideoLinK(browserCachedData.getVideoLink());

    }

    @And("^Navigate to url \"([^\"]*)\"$")
    public void navigateToUrl(String url) throws Throwable {
        browserDriver.getCurrentDriver().navigate().to(url);
    }

    @After
    public void after() throws IOException {
        if (record_video.equals("true")) {
            monteRecorder.stopAndSave(scenario.getName());
            attachmentOfRecording();
            slf4jLogger.info("Video mode true...VideoMaker stopped");
        }
    }

    @Attachment(value = "video", type = "video/mp4")
    private byte[] attachmentOfRecording() {
        try {

            File video = monteRecorder.getLastRecording();
            return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
        } catch (IOException e) {
            slf4jLogger.warn("Allure listener exception" + e);
            return new byte[0];
        }
    }


}

