package com.workable.hackathon.steps.definitions.amazon;

import com.workable.hackathon.ui.*;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class AmazonActionStepDefinitions {
    private Scenario scenario;

    @Autowired
    AmazonHomePageActions amazonHomePageActions;

    @Autowired
    AmazonLoginPageActions amazonLoginPageActions;

    @Autowired
    AmazonProductPageActions amazonProductPageActions;

    @Autowired
    AmazonSearchResultsPageActions amazonSearchResultsPageActions;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @When("^Login to Amazon with \"([^\"]*)\" username and \"([^\"]*)\" password$")
    public void loginToAmazonWithUsernameAndPassword(String username, String password) throws Throwable {
        amazonHomePageActions.gotoLogin();
        amazonLoginPageActions.loginWith(username, password);
        Thread.sleep(15000);
    }

    @When("^Search for \"([^\"]*)\" product$")
    public void searchForProduct(String product) throws Throwable {
        amazonHomePageActions.searchForProduct(product);
    }

    @When("^Show results for \"([^\"]*)\" category only$")
    public void showResultsForCategoryOnly(String category) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Sort by \"([^\"]*)\"$")
    public void sortBy(String sortCriteria) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Select first product in results$")
    public void selectFirstProductInResults() throws Throwable {
        amazonSearchResultsPageActions.selectSearchItem();
    }

    @When("^Add Item to shopping cart$")
    public void addItemToShoppingCart() throws Throwable {
        amazonProductPageActions.addToBasket();
    }

    @When("^View shopping cart$")
    public void viewShoppingCart() throws Throwable {
        amazonHomePageActions.viewBasket();
    }

    @When("^Proceed to checkout$")
    public void proceedToCheckout() throws Throwable {
        amazonHomePageActions.proceedCheckOut();
    }

    @When("^Notify friends for delivery date$")
    public void notifyFriendsForDeliveryDate() throws Throwable {

    }

}
