package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ReqSpecBuilder {

    @BeforeClass
    public void beforeClass(){
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    requestSpecBuilder.setBaseUri("https://api.postman.com");
    requestSpecBuilder.addHeader("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8");
    requestSpecBuilder.log(LogDetail.ALL);
    // build the request spec
    RestAssured.requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void validate_status_nonBDD() {
        Response response = get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }
    @Test
    public void validate_status_body_nonBDD() {
        Response response = get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"),is(equalTo("Project1")));
    }
    @Test
    public void query_request_spec(){
        QueryableRequestSpecification query = SpecificationQuerier.query(requestSpecification);
        System.out.println(query.getBaseUri());
        System.out.println(query.getHeaders());
    }

}
