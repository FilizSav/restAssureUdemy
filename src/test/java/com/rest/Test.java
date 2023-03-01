package com.rest;


import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Test {

    @org.testng.annotations.Test
    public void test() {


// Set up the base URL
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

// Create a RequestSpecification object
        RequestSpecification httpRequest = RestAssured.given();

// Set the request headers
        httpRequest.header("Content-Type", "application/json");

// Set the query parameter
        httpRequest.queryParam("userId", "2");

// Send the request and get the response
        Response response = httpRequest.get("/albums");

// Print the response body
        System.out.println(response.getBody().asString());

        assertThat(response.statusCode(),is(equalTo(200)));
    }
}
