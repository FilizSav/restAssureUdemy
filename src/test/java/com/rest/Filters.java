package com.rest;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Filters {

    @Test
    public void filters() {
        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
    @Test
    public void logging_filter() {
        PrintStream fileOutput = null;
        try {
            fileOutput = new PrintStream(new File("restAssured.log"));
            given().
                    baseUri("https://postman-echo.com").
                    filter(new RequestLoggingFilter(fileOutput)).
                    filter(new ResponseLoggingFilter(fileOutput)).
                    //or you can log specific details
                    // filter(new RequestLoggingFilter(fileOutput)).
                    // filter(new ResponseLoggingFilter(fileOutput)).
                    when().
                    get("/get").
                    then().
                    assertThat().
                    statusCode(200);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
