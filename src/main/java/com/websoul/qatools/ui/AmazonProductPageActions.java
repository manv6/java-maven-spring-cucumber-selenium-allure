package com.websoul.qatools.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static org.junit.Assert.*;

/**
 * Created by manolis vlastos on 20/10/2017.
 */
@Service
public class AmazonProductPageActions {

    @Autowired
    AmazonProductPage amazonProductPage;


    public void productShouldHaveAvailability(String stockAvailability) {
        assertTrue(amazonProductPage.productAvailability.getText().contains(stockAvailability));
    }

    public void merchantShouldHaveStatus(String merchantStatus) {
        assertTrue(amazonProductPage.merchantInfo.getText().contains(merchantStatus));
    }

    public void verifyProductShipsTo(String location) {
        assertTrue(amazonProductPage.shippingLocation.getText().contains(location));
    }

    public void addToBasket(){
        amazonProductPage.addBasketbutton.click();
    }

}
