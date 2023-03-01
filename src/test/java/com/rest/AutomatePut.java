package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
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
    public void validate_put_request_bdd() {
        String workspaceId = "fab18af0-fe83-49d8-a0b3-b1d1774f4e08";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"f004ce90-66e8-45ae-a15d-23783f92ef51\",\n" +
                "        \"name\": \"MyWorkspace3\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest assured created this\"\n" +
                "    }\n" +
                "}";
        given().
               body(payload).
               pathParams("workspaceId",workspaceId).
        when().
                put("/workspaces/{workspaceId}").
        then().
                assertThat().body("workspace.name", equalTo("MyWorkspace3"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }
}
