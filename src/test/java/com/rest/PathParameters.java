package com.rest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PathParameters {
   // @BeforeClass
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
/*    @Test
    public void path_query_parameters() {
        given().
                baseUri("https://reqres.in").
                pathParams("userId","2").
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }*/
    @Test
    public void multipart_form_dat() {
        given().
                baseUri("https://postman-echo.com").
                multiPart("foo1","bar1").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
