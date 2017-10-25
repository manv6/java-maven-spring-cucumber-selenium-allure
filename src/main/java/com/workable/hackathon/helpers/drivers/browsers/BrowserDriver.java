package com.workable.hackathon.helpers.drivers.browsers;


import com.workable.hackathon.helpers.context.cache.InitialCachedData;
import com.workable.hackathon.helpers.context.ContextManagement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * WebDriver service used in order to access page through a browser's instance
 */
@Service("BrowserDriver")
public class BrowserDriver {


    private final Logger slf4jLogger = LoggerFactory.getLogger(BrowserDriver.class);
    @Autowired
    ContextManagement cachedDataContext;
    @Autowired
    BrowserFactory browserFactory;
    @Value("#{properties['testBrowser']}")
    private String testBrowser;
    private WebDriver mDriver;

    /**
     * @return WebDriver instance based either property file value or cached data value
     */
    public synchronized WebDriver getCurrentDriver() throws IOException {
        if (mDriver == null) {
            Browsers browser;
            InitialCachedData initialCachedData = (InitialCachedData) cachedDataContext.getObject("initialCachedData");
            if (initialCachedData.getBrowser() == null) {
                browser = Browsers.getBrowserForName(testBrowser);
            } else {
                browser = initialCachedData.getBrowser();
            }
            try {
                mDriver = browserFactory.initBrowser(browser);
            } catch (UnreachableBrowserException e) {
                mDriver = browserFactory.initBrowser(browser);
            } catch (WebDriverException e) {
                mDriver = browserFactory.initBrowser(browser);
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    /**
     * Closes the browser and destroys it's instance
     */
    public void close() throws IOException {
        if (mDriver != null) {
            try {
                getCurrentDriver().quit();
                mDriver = null;
                slf4jLogger.info("closing the browser");
            } catch (UnreachableBrowserException e) {
                slf4jLogger.info("cannot close browser: unreachable browser");
            }
        }
    }

    /**
     * Loads url on webDriver instance
     */
    public void loadPage(String url) throws IOException {
        slf4jLogger.info("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

    /**
     * Terminates webDriver's thread3
     */
    private class BrowserCleanup implements Runnable {
        public void run() {
            slf4jLogger.info("Closing the browser");
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
