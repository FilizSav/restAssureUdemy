package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

public class Serialization {

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream fileOutPut = new PrintStream(new File("restAssured.log"));
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
                requestSpecBuilder.addHeader("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
                setContentType(ContentType.JSON).
                addFilter(new ResponseLoggingFilter(fileOutPut)).
                addFilter(new ResponseLoggingFilter(fileOutPut));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void validate_post_request_map_serialization() throws JsonProcessingException {
        HashMap<String, Object> mainObj = new HashMap<>();
        HashMap<String, String> nestedObj = new HashMap<>();
        nestedObj.put("name","Serialization");
        nestedObj.put("type","personal");
        nestedObj.put("description","rest assured");
        mainObj.put("workspace",nestedObj);

        // Using ObjectMapperClass
        ObjectMapper objMap = new ObjectMapper();
        String mainObject = objMap.writeValueAsString(mainObj);

        given().
                body(mainObj).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                assertThat().
                statusCode(200);
    }
}
