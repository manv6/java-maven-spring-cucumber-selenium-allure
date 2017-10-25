package com.workable.hackathon.helpers.conditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by panagiotistsiakos on 11/20/16.
 */
public class CustomExpectedConditions {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(CustomExpectedConditions.class);

    public static ExpectedCondition<WebElement> visibilityOfId(String id) {
        slf4jLogger.info("Execute Query: return document.getElementById('"+id+"');");
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                try {
                    slf4jLogger.debug("Polling in shadow DOM for finding element with id '" + id + "");
                    WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('"+id+"');");
                    return (element != null && element.isDisplayed()) ? element : null;
                } catch (StaleElementReferenceException var1) {
                    return null;
                }
            }

            public String toString() {
                return "visibility of element located under shadow root element by id " + id;
            }
        };
    }

    public static ExpectedCondition<WebElement> shadowRootVisibilityOf(WebElement shadowRoot, String cssSelector) {
        slf4jLogger.info("Execute Query: return document.querySelector(\"#" + shadowRoot.getAttribute("id") + "\").shadowRoot.querySelector('" + cssSelector + "')");
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                try {
                    slf4jLogger.debug("Polling in shadow DOM for finding element with css selector '" + cssSelector + "");
                    WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('#" + shadowRoot.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector + "');");
                    return (element != null && element.isDisplayed()) ? element : null;
                } catch (StaleElementReferenceException var1) {
                    return null;
                }
            }

            public String toString() {
                return "visibility of element located under shadow root element by css selector " + cssSelector;
            }
        };
    }

    public static ExpectedCondition<WebElement> shadowRootClickOf(WebElement shadowRoot, String cssSelector) {
        slf4jLogger.info("Execute Query: return document.querySelector('#" + shadowRoot.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector + "').click();");
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                try {
                    slf4jLogger.debug("Clicking in shadow DOM for finding element with css selector '" + cssSelector + "");
                    WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript("document.querySelector('#" + shadowRoot.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector + "').click();");
                    return (element != null && element.isDisplayed()) ? element : null;
                } catch (StaleElementReferenceException var1) {
                    return null;
                }
            }

            public String toString() {
                return "visibility of element located under shadow root element by css selector " + cssSelector;
            }
        };
    }

    public static ExpectedCondition<WebElement> shadowRootVisibilityOf(String locator, String cssSelector) {
        slf4jLogger.info("Query executed :document.querySelector(\"#" + locator + "\").shadowRoot.querySelector('" + cssSelector + "');");
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                try {
                    slf4jLogger.debug("Polling in shadow DOM for finding element with css selector '" + cssSelector + "");
                    WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('#" + locator + "').shadowRoot.querySelector('" + cssSelector + "');");
                    return (element != null && element.isDisplayed()) ? element : null;
                } catch (StaleElementReferenceException var1) {
                    return null;
                }
            }

            public String toString() {
                return "visibility of element located under shadow root element by css selector " + cssSelector;
            }
        };
    }


    public static ExpectedCondition<List<WebElement>> shadowRootVisibilityOfAllElements(WebElement shadowRoot, String cssSelector) {
        slf4jLogger.info("Execute Query : return document.querySelector(\"#" + shadowRoot.getAttribute("id") + "\").shadowRoot.querySelectorAll('" + cssSelector + "')");
        return new ExpectedCondition<List<WebElement>>() {
            public List<WebElement> apply(WebDriver driver) {
                slf4jLogger.debug("Polling in shadow DOM for finding elements with css selector '" + cssSelector + "");
                List<WebElement> elements = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.querySelector('#" + shadowRoot.getAttribute("id") + "').shadowRoot.querySelectorAll('" + cssSelector + "');");
                if (elements != null) {
                    for (WebElement element : elements) {
                        if (!element.isDisplayed()) {
                            return null;
                        }
                    }
                }
                return elements.size() > 0 ? elements : null;
            }

            public String toString() {
                return "visibility of all elements located under shadow root by css selector " + cssSelector;
            }
        };
    }


    public static ExpectedCondition<Boolean> shadowRootInvisibilityOfElementLocated(WebElement shadowRoot, String cssSelector) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    slf4jLogger.debug("Polling in shadow DOM for finding element with css selector no longer to be visible'" + cssSelector + "");
                    WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('#" + shadowRoot.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector + "');");
                    if (element == null || !element.isDisplayed()) {
                        return true;
                    }

                } catch (StaleElementReferenceException var1) {
                    return true;
                }
                return false;
            }

            public String toString() {
                return "element located under shadow root element by css selector " + cssSelector + " no longer be visible";
            }
        };
    }

}
