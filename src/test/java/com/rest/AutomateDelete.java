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
import static org.hamcrest.Matchers.*;

public class AutomateDelete {
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
    public void validate_delete_request_bdd() {
        String workspaceId = "fab18af0-fe83-49d8-a0b3-b1d1774f4e08";
        given().
               pathParams("workspaceId",workspaceId).
        when().
                delete("/workspaces/{workspaceId}").
        then().
                assertThat().body("workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }
    @Test
    public void validate_delete_request_non_bdd() {
        String workspaceId = "e4b377af-dd88-414b-8dd1-e0ad48a0af0a";
        Response response = with().pathParams("workspaceId",workspaceId).delete("/workspaces/{workspaceId}");
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.<String>path("workspace.id"),equalTo(workspaceId));
    }
}
