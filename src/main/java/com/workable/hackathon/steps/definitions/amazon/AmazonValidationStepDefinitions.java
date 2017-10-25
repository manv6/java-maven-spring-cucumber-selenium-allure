package com.workable.hackathon.steps.definitions.amazon;

import com.workable.hackathon.ui.AmazonProductPageActions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class AmazonValidationStepDefinitions {

    private Scenario scenario;

    @Autowired
    AmazonProductPageActions amazonProductPageActions;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }


    @Then("^Product can ship to \"([^\"]*)\"$")
    public void productCanShipTo(String location) throws Throwable {
        amazonProductPageActions.verifyProductShipsTo(location);
    }


    @Then("^Product should be \"([^\"]*)\"$")
    public void productShouldBe(String stockAvailability) throws Throwable {
        amazonProductPageActions.productShouldHaveAvailability(stockAvailability);
    }

    @Then("^Merchant should be verified by \"([^\"]*)\"$")
    public void merchantShouldBeVerifiedBy(String merchantStatus) throws Throwable {
        amazonProductPageActions.merchantShouldHaveStatus(merchantStatus);
    }

}
