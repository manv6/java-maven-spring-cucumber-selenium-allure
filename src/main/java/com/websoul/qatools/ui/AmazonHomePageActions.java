package com.websoul.qatools.ui;

import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by dimitris giannakos on 20/10/2017.
 */
@Service
public class AmazonHomePageActions {

    @Autowired
    AmazonHomePage amazonHomePage;

    @Autowired
    BrowserDriver driver;

    public void gotoLogin() {
        amazonHomePage.loginSpan.click();
    }

    public void searchForProduct(String product) {
        amazonHomePage.searchBarInput.clear();
        amazonHomePage.searchBarInput.sendKeys(product);
        amazonHomePage.searchSubmitButton.click();
    }

    public void proceedCheckOut() throws IOException {
        amazonHomePage.proceedToCheckout.click();
        driver.getCurrentDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void editQuantityValue() throws IOException {
        amazonHomePage.quantity.click();
        driver.getCurrentDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void editBasket() {
        amazonHomePage.viewCart.click();
        amazonHomePage.editBasket.click();
        amazonHomePage.quantity.click();
    }

    public void viewBasket() throws IOException {
        amazonHomePage.viewCart.click();
        driver.getCurrentDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
