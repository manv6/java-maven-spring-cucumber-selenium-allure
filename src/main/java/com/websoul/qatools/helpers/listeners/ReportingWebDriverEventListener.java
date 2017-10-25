package com.websoul.qatools.helpers.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class LogDriver.
 */
public class ReportingWebDriverEventListener implements WebDriverEventListener {

    /**
     * The log.
     */
    private static final Logger EVENTS_LOGGER = LoggerFactory.getLogger("ReportingWebDriverEventListener");

    /**
     * Instantiates a new log driver.
     */
    public ReportingWebDriverEventListener() {
    }

    /**
     * Info.
     *
     * @param message the message
     */
    public static void info(String message) {
        EVENTS_LOGGER.info(message);
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeNavigateTo(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterNavigateTo(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void afterNavigateTo(String url, WebDriver driver) {
        info("Navigate to " + url);
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeNavigateBack(org.openqa.selenium.WebDriver)
     */
    public void beforeNavigateBack(WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterNavigateBack(org.openqa.selenium.WebDriver)
     */
    public void afterNavigateBack(WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeNavigateForward(org.openqa.selenium.WebDriver)
     */
    public void beforeNavigateForward(WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterNavigateForward(org.openqa.selenium.WebDriver)
     */
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeClickOn(WebElement element, WebDriver driver) {
    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterClickOn(WebElement element, WebDriver driver) {
        String locator = element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));
        info("The element with locator '" + locator + "' was clicked");
    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeChangeValueOf(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterChangeValueOf(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterChangeValueOf(WebElement element, WebDriver driver) {

        String value = element.getAttribute("value");
        String locator = element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));

        if (!value.isEmpty()) {
            info("Value '" + value + "' was typed in element with locator '" + locator + "'");
        }
    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#beforeScript(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void beforeScript(String script, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#afterScript(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void afterScript(String script, WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.ReportingWebDriverEventListener#onException(java.lang.Throwable, org.openqa.selenium.WebDriver)
     */
    public void onException(Throwable throwable, WebDriver driver) {

    }

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }
}