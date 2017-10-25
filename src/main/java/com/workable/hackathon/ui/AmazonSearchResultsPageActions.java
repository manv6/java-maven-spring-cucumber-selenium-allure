package com.workable.hackathon.ui;

import com.workable.hackathon.helpers.drivers.browsers.BrowserDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class AmazonSearchResultsPageActions {

    @Autowired
    AmazonSearchResultsPage amazonSearchResultsPage;
    @Autowired
    BrowserDriver driver;


    public void checkProductsWithBadges(){
        for(WebElement element : amazonSearchResultsPage.listOfBadges){

        }
    }

    public String getSearchResultText() {
        return amazonSearchResultsPage.searchResults.getText();
    }

    public void selectSearchItem() throws IOException {
        driver.getCurrentDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        amazonSearchResultsPage.searchResultThirdItemSelect.click();
        driver.getCurrentDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
