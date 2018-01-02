package com.websoul.qatools.helpers.utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.websoul.qatools.helpers.context.ContextManagement;
import com.websoul.qatools.helpers.context.cache.RuntimeCachedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

@Service
public class APICommonTools {

    @Autowired
    ContextManagement cachedDataContext;

    public void get(String uri) {
        RuntimeCachedData runtimeCachedData = (RuntimeCachedData) cachedDataContext.getObject("runtimeCachedData");
        runtimeCachedData.setResponse(given().log().uri().when().get(uri));
        System.out.println("response: " + runtimeCachedData.getResponse().prettyPrint());
    }

    public void post(JsonObject json, String uri) {
        RuntimeCachedData runtimeCachedData = (RuntimeCachedData) cachedDataContext.getObject("runtimeCachedData");
        runtimeCachedData.setResponse(given().log().uri().when().contentType("application/json")
                .body(json.toString())
                .when()
                .post(uri));
        System.out.println("response: " + runtimeCachedData.getResponse().prettyPrint());
    }

}
