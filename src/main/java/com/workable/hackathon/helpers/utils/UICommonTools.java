package com.workable.hackathon.helpers.utils;

import com.workable.hackathon.helpers.conditions.CustomExpectedConditions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by manolisvlastos on 20/05/16.
 */
@Service
public class UICommonTools {

    private final Logger slf4jLogger = LoggerFactory.getLogger(UICommonTools.class);

    public void doubleClickText(WebDriver driver, String element1) throws InterruptedException {
        WebElement element = driver.findElement(By.cssSelector(element1));
        Actions select = new Actions(driver);
        select.doubleClick(element).build().perform();
    }

    public void clickContextMenu(WebDriver driver, String ele, CharSequence keysSequence) throws InterruptedException {
        WebElement element = driver.findElement(By.xpath(ele));
        Actions act = new Actions(driver);
        act.contextClick(element).sendKeys(Keys.chord(keysSequence)).build().perform();
        Thread.sleep(4000);
    }

    public void highligthDisplayedElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].set Attribute(arguments[1], arguments[2])", element, "style", "border: 2px solid yellow; color: yellow; font-weight: bold;");
    }

    public void highligthDisplayedElementExpectedText(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("document.getElementById('locatorid').style.backgroundColor = 'green';");
    }

    public void highligthDisplayedElementNotExpectedText(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("document.shadowRoot.getElementById('" + element.getAttribute("id") + "').style.backgroundColor = 'red';");
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void changeShadowDomElementAttributeByRootElementWithId(WebDriver driver, WebElement element, String className, String newClassName, String attribute) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector(\"#" + element.getAttribute("id") + "\").shadowRoot.querySelector('div." + className + "').setAttribute('" + attribute + "','" + newClassName + "');");
    }

    public WebElement getClipperShadowRoot(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rootElement = (WebElement) js.executeScript("return document.querySelector(\"#" + element.getAttribute("id") + "\").shadowRoot.querySelector('div > div');");
        return rootElement;
    }


    public WebElement getClipperELementId(WebDriver driver, String id) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        return wait.until(CustomExpectedConditions.visibilityOfId(id));
    }

    public WebElement getClipperELementAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        return wait.until(CustomExpectedConditions.shadowRootVisibilityOf(element, cssSelector));
    }

    public WebElement getClipperAllELementAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector, int position) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        return wait.until(CustomExpectedConditions.shadowRootVisibilityOfAllElements(element, cssSelector)).get(position);
    }

    public void waitForClipperElementNoLongerToBeVisible(WebDriver driver, WebElement element, String cssSelector) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(CustomExpectedConditions.shadowRootInvisibilityOfElementLocated(element, cssSelector));
    }

    public WebElement getClipperNestedELementAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector1, String cssSelector2) {
        slf4jLogger.info("Query executed :return document.querySelector(\"#" + element.getAttribute("id") + "\").shadowRoot.querySelector('" + cssSelector1 + "').querySelector('" + cssSelector2 + "');");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rootElement = (WebElement) js.executeScript("return document.querySelector('#" + element.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector1 + "').querySelector('" + cssSelector2 + "');");
        return wait.until(ExpectedConditions.visibilityOf(rootElement));
    }

    public WebElement getClipperELementAfterShadowRoot(WebDriver driver, String element, String cssSelector) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        return wait.until(CustomExpectedConditions.shadowRootVisibilityOf(element, cssSelector));
    }

    public boolean getNotClipperELementAfterShadowRoot(WebDriver driver, final WebElement element, final String cssSelector) {
        slf4jLogger.info("Query executed :document.querySelector(\"#" + element + "\").shadowRoot.querySelector('" + cssSelector + "');");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rootElement = (WebElement) js.executeScript("return document.querySelector('#" + element + "').shadowRoot.querySelector('" + cssSelector + "');");
        //return wait.until(ExpectedConditions.visibilityOf(rootElement));
        return wait.until(new ExpectedCondition<Boolean>() {

            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver driver) {
                try {
                    getClipperELementAfterShadowRoot(driver, element, cssSelector);
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                }
            }
        });
    }

    public List<WebElement> getClipperELementListAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector) {
        slf4jLogger.info("Query executed :document.querySelector(\"#" + element.getAttribute("id") + "\").shadowRoot.querySelector('" + cssSelector + "').children;");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> rootElement = (List<WebElement>) js.executeScript("return document.querySelector('#" + element.getAttribute("id") + "').shadowRoot.querySelector('" + cssSelector + "').children;");
        return rootElement;
    }

    public List<WebElement> getClipperAllELementListAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector) {
        slf4jLogger.info("Query executed :document.querySelector(\"#" + element.getAttribute("id") + "\").shadowRoot.querySelectorAll('" + cssSelector + "');");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> rootElement = (List<WebElement>) js.executeScript("return document.querySelector('#" + element.getAttribute("id") + "').shadowRoot.querySelectorAll('" + cssSelector + "');");
        return rootElement;
    }

    public WebElement getElementByText(WebDriver driver, String cssSelector) {
        slf4jLogger.info("Query executed :document.querySelector('" + cssSelector + "');");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rootElement = (WebElement) js.executeScript("return document.querySelector('" + cssSelector + "');");
        return wait.until(ExpectedConditions.visibilityOf(rootElement));
    }

    public void mouseHoverToAfterShadow(WebDriver driver, String shadowRoot, String type) throws InterruptedException {
        MouseOverAction mouseOverAction = new MouseOverAction();
        mouseOverAction.runMe(driver, shadowRoot, type);
    }

    public List<WebElement> findElementsWithByTag(List<WebElement> list, String tag) {
        List<WebElement> filteredList = new ArrayList<>();
        for (WebElement tempElement : list) {
            List<WebElement> spanList = tempElement.findElements(By.tagName(tag));
            if (spanList.size() > 1) {
                for (WebElement element : spanList) {
                    if (element.getAttribute("data-com.workable.hackathon.ui") != null) {
                        filteredList.add(element);
                    }
                }
            }
        }

        return filteredList;
    }

    public WebElement findElementByTag(WebElement list, String tag) {
        WebElement tempElement = list.findElement(By.tagName(tag));
//        if (tempElement.getTagName().equalsIgnoreCase("span")) {
            return tempElement;
//        }
//        else
//        return null;
    }

    public boolean checkNewTab(WebDriver driver) {
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        if (tabs2.size() > 1)
            return true;
        else
            return false;
//        driver.switchTo().window(tabs2.get(1));
//        driver.close();
//        driver.switchTo().window(tabs2.get(0));
//        return false;
    }

    public void turnOffImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public void turnOnImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public void turnOnImplicitWaitsWithSeconds(WebDriver driver, long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public boolean isWebElementDisplayed(WebDriver driver, WebElement webElement, long secondsToWait) {
        turnOffImplicitWaits(driver);
        turnOnImplicitWaitsWithSeconds(driver, secondsToWait);
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            turnOnImplicitWaits(driver);
        }
    }

    public void clickClipperELementAfterShadowRoot(WebDriver driver, WebElement element, String cssSelector) {

        CustomExpectedConditions.shadowRootClickOf(element, cssSelector);
    }

    public void changeTabToLeft(WebDriver driver) {
        Actions action= new Actions(driver);
        action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).build().perform();
    }

    public class MouseOverAction extends Thread {

        private volatile boolean running = true; // Run unless told to pause

        public void runMe(WebDriver driver, String shadowRoot, String type) throws InterruptedException {
            run();
            Actions action = new Actions(driver);
            action.moveToElement(getClipperELementAfterShadowRoot(driver, shadowRoot, type)).build().perform();
            Thread.sleep(1000);
            pauseThread();
        }

        @Override
        public void run() {
            while (!running) ;
        }

        public void pauseThread() throws InterruptedException {
            running = false;
        }

        public void resumeThread() {
            running = true;
        }

    }
}
