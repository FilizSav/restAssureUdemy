package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class RequestJsonArray {
    @BeforeClass
    public void beforeClass(){
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
    }
    @Test
    public void validate_post_request_payload_json_array_as_list() {
        HashMap<String, String> obj5001 = new HashMap<>();
        obj5001.put("id","5001");
        obj5001.put("type","None");
        HashMap<String, String> obj5002 = new HashMap<>();
        obj5002.put("id","5002");
        obj5002.put("type","Glazed");

        List<Map<String,String>> jsonList = new ArrayList<>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

    given().
            body(jsonList).
    when().
            post("/post").
    then().
            log().all().
            assertThat().body("msg", equalTo("success"));
    }
}
