package com.rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestSpecificationExample {

    RequestSpecification requestSpecification;
    @BeforeClass
    public void beforeClass(){
        requestSpecification =
                given().
                        baseUri("https://api.postman.com").
                        header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8");
    }
    @Test
    public void validate_status() {
        given(requestSpecification).
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void validate_status_body() {
        given().spec(requestSpecification).
                when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces[0].name",equalTo("Project1"));

    }
    @Test
    public void validate_status_nonBDD() {
        //non BDD method created a response object and asserted the response code
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }
    @Test
    public void validate_status_body_nonBDD() {
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"),is(equalTo("Project1")));
    }
}
