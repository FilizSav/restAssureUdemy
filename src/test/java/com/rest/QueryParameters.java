package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class QueryParameters {
    @BeforeClass
/*    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io/");
        requestSpecBuilder.addHeader("x-mock-match-request-body", "true").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }*/
    @Test
    public void single_query_parameters() {
        given().
                baseUri("https://postman-echo.com").
                queryParam("foo1","bar1").
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void multiple_query_parameters() {
        given().
                baseUri("https://postman-echo.com").
                queryParam("foo1","bar1").
                queryParam("foo2","bar2").
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void multiple_query_parameters_hash_map() {
        HashMap<String, String > queryMap = new HashMap<>();
        queryMap.put("foo", "bar");
        queryMap.put("foo1", "bar1");
        queryMap.put("foo2", "bar2");
        given().
                baseUri("https://postman-echo.com").
                when().
                queryParams(queryMap).
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void multiple_value_query_parameters() {
        given().
                baseUri("https://postman-echo.com").
                queryParam("foo1","bar1;bar2;bar3").
                log().all().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
}
