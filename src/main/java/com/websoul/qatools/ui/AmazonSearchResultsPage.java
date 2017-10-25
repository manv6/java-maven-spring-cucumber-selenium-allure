package com.websoul.qatools.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by dimitris giannakos on 20/10/2017.
 */
@Service
public class AmazonSearchResultsPage {

    @FindBy(id="twotabsearchtextbox")
    WebElement searchBarInput;

    @FindBy(css="input.nav-input")
    WebElement searchSubmitButton;

    @FindBy(css="span[class='a-badge-label-inner a-text-ellipsis']")
    List<WebElement> listOfBadges;

    // Use annotations to locate elements.
    @FindBy(css = "img.s-access-image.cfMarker")
    WebElement searchItemSelect;

    @FindBy(xpath = "//li[@id='result_0']/div/div/div/div[2]/div/a/h2")
    WebElement searchResults;

    @FindBy(css = "[class='s-access-image cfMarker'")
    WebElement searchResultThirdItemSelect;


}
