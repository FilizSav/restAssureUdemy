package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simplePojo.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JacksonApiPojo {

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream fileOutPut = new PrintStream(new File("restAssured.log"));
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
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
    public void simple_Pojo(){
        SimplePojo simplePojo = new SimplePojo("value1","value2");
        given().
                body(simplePojo).
                when().
                post("/postPojo").
        then().spec(responseSpecification).
                assertThat().
                body("key1", equalTo(simplePojo.getKey1()),"key2",
                        equalTo(simplePojo.getKey2())).
                statusCode(200);
    }
    @Test
    public void simple_Pogo_deserialization() throws JsonProcessingException {

        SimplePojo simplePojo = new SimplePojo("value1","value2");
        SimplePojo deSer = given().
                body(simplePojo).
                when().
                post("/postPojo").
                then().spec(responseSpecification).
                extract().response().as(SimplePojo.class);
        //Jackson will do the deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        String desPojo = objectMapper.writeValueAsString(deSer);
        String simPojo = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(desPojo),equalTo(objectMapper.readTree(simPojo)));
    }
}
