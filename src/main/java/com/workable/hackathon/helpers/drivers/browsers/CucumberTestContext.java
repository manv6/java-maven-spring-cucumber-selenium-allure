package com.workable.hackathon.helpers.drivers.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by panagiotistsiakos on 11/23/16.
 */
public class CucumberTestContext {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberTestContext.class);

    /**
     * The inner Class Context.
     */
    static class Context {
       WebDriver driver;

        /**
         * Clear context.
         */
        public void clear() {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (WebDriverException wde) {
                    LOG.warn("Exception caught calling controller.quit(): \"" + wde.getMessage() + "\" additional info: " + wde.getAdditionalInformation());
                }
            }
            driver = null;
        }
    }

    /**
     * The inner context as a thread local variable.
     */
    private static ThreadLocal<Context> innerContext = new ThreadLocal<CucumberTestContext.Context>() {

        @Override
        protected Context initialValue() {
            return new Context(); //initial is empty;
        }

    };


    /**
     * Gets the web driver.
     *
     * @return the web driver
     */
    public static WebDriver getWebDriver() {
        return innerContext.get().driver;
    }

    public static void setWebDriver(WebDriver instance) {
        Context context = innerContext.get();
        context.driver = instance;

    }

    /**
     * Clean the local thread context
     */
    public static void clean() {
        innerContext.get().clear();
        innerContext.remove();
    }

}
