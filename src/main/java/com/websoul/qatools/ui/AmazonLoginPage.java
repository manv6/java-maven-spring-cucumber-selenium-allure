package com.websoul.qatools.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
/**
 * Created by dimitris giannakos on 20/10/2017.
 */
@Service
public class AmazonLoginPage {

    @FindBy(id="ap_email")
    WebElement emailInput;

    @FindBy(id="ap_password")
    WebElement passwordInput;

    @FindBy(id="signInSubmit")
    WebElement submitButton;
}
