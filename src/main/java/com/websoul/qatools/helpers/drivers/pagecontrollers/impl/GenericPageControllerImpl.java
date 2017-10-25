package com.websoul.qatools.helpers.drivers.pagecontrollers.impl;


import com.websoul.qatools.ui.*;
import com.websoul.qatools.helpers.context.cache.RuntimeCachedData;
import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import com.websoul.qatools.helpers.drivers.pagecontrollers.PageController;
import com.websoul.qatools.ui.AmazonHomePage;
import com.websoul.qatools.ui.AmazonLoginPage;
import com.websoul.qatools.ui.AmazonProductPage;
import com.websoul.qatools.ui.AmazonSearchResultsPage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.PageFactoryFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Page Controller for pageObjects
 */
@Controller("genericPageController")
public class GenericPageControllerImpl implements PageController {

    private final Logger slf4jLogger = LoggerFactory.getLogger(GenericPageControllerImpl.class);

    @Autowired
    BrowserDriver browserDriver;
    @Autowired
    RuntimeCachedData runtimeCachedData;
    @Autowired
    AmazonHomePage amazonHomePage;
    @Autowired
    AmazonLoginPage amazonLoginPage;
    @Autowired
    AmazonProductPage amazonProductPage;
    @Autowired
    AmazonSearchResultsPage amazonSearchResultsPage;


    /**
     * Initializes elements of google home search pageObject using Selenium's PageFactory
     */


    public void initializeElementsForAllPages() throws IOException {
        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonHomePage);
        slf4jLogger.info("Page Factory: AmazonHomePage object initialized successfully");
        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonLoginPage);
        slf4jLogger.info("Page Factory: AmazonLoginPage object initialized successfully");
        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonProductPage);
        slf4jLogger.info("Page Factory: AmazonProductPage object initialized successfully");
        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonSearchResultsPage);
        slf4jLogger.info("Page Factory: AmazonSearchResultsPage object initialized successfully");
    }

}
