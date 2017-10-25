package com.workable.hackathon.helpers.context.cache;


import com.workable.hackathon.helpers.drivers.browsers.Browsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * List of properties to be cached initially
 */
public class InitialCachedData {

    private final Logger slf4jLogger = LoggerFactory.getLogger(InitialCachedData.class);

    Browsers browser;

    public Browsers getBrowser() {
        return browser;
    }

    public void setBrowser(Browsers browser) {
        this.browser = browser;
    }
}
