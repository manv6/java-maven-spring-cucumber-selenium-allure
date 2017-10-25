package com.workable.hackathon.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dimitris giannakos on 20/10/2017.
 */
@Service
public class AmazonLoginPageActions {

    @Autowired
    AmazonLoginPage amazonLoginPage;

    public void loginWith(String username, String password) {
        amazonLoginPage.emailInput.sendKeys(username);
        amazonLoginPage.passwordInput.sendKeys(password);
        amazonLoginPage.submitButton.click();
    }
}
