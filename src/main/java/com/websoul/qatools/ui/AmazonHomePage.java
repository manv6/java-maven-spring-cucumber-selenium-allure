package com.websoul.qatools.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
/**
 * Created by dimitris giannakos on 20/10/2017.
 */
@Service
public class AmazonHomePage {

    @FindBy(id="twotabsearchtextbox")
    WebElement searchBarInput;

    @FindBy(css="input.nav-input")
    WebElement searchSubmitButton;

    @FindBy(id="nav-link-accountList")
    WebElement accountList;

    @FindBy(id="nav-orders")
    WebElement orders;

    @FindBy(id="nav-cart")
    WebElement cart;

    @FindBy(id="nav-line-1")
    WebElement nameSpan;

    @FindBy(css = "a#nav-link-accountList > span")
    WebElement loginSpan;



    @FindBy(id = "nav-cart")
     WebElement viewCart;

    @FindBy(id = "a-autoid-2-announce")
     WebElement editBasket;

    // Increase quantity value to 3
    @FindBy(id = "dropdown1_2")
     WebElement quantity;

    @FindBy(name = "proceedToCheckout")
     WebElement proceedToCheckout;



}
