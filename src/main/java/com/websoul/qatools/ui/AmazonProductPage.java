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
public class AmazonProductPage {

    @FindBy(css="div#merchant-info.a-section.a-spacing-mini#merchant-info")
    WebElement merchantInfo;

    @FindBy(css="div#availability.a-section.a-spacing-none")
    WebElement productAvailability;

    @FindBy(id = "add-to-cart-button")
    WebElement addBasketbutton;

    @FindBy(css = "div.a-section.a-spacing-none")
    WebElement shippingLocation;

}
