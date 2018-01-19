package com.websoul.qatools.ui;

import com.websoul.qatools.helpers.annotations.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
/**
 * Created by manolis vlastos on 20/10/2017.
 */
@Page
@Service
public class AmazonLoginPage {

    @FindBy(id="ap_email")
    WebElement emailInput;

    @FindBy(id="ap_password")
    WebElement passwordInput;

    @FindBy(id="signInSubmit")
    WebElement submitButton;
}
