package com.websoul.qatools.steps.definitions.amazon;

import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import com.websoul.qatools.helpers.drivers.browsers.BrowserFactory;
import com.websoul.qatools.ui.AmazonProductPageActions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AmazonValidationStepDefinitions {

    private Scenario scenario;
    @Autowired
    BrowserDriver browserDriver;

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

    @Then("^Page has title \"([^\"]*)\"$")
    public void hasTitle(String title) throws Throwable {
        assertEquals(title,browserDriver.getCurrentDriver().getTitle());
    }

}
