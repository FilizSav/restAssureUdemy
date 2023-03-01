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
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePost {
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
    public void validate_post_request_bdd() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"f004ce90-66e8-45ae-a15d-23783f92ef51\",\n" +
                "        \"name\": \"MyWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest assured created this\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
        when().
                post("/workspaces").
        then().assertThat().body("workspace.name", equalTo("MyWorkspace"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
    @Test
    public void validate_post_request_non_bdd() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"f004ce90-66e8-45ae-a15d-23783f92ef51\",\n" +
                "        \"name\": \"MyWorkspace2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest assured created this\"\n" +
                "    }\n" +
                "}";
        Response response = with().body(payload).post("/workspaces");
        assertThat(response.<String>path("workspace.name"),equalTo("MyWorkspace2"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
    }
    @Test
    public void validate_post_request_payload_from_file() {
     File file = new File("src/main/resources/CreateWorkSpacePayload.json");
        given().
                body(file).
                when().
                post("/workspaces").
                then().assertThat().body("workspace.name", equalTo("MyWorkspace5"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
    @Test
    public void validate_post_request_payload_as_map() {
        HashMap<String, Object> mainObject = new HashMap<>();

        HashMap<String, String> nestedObject = new HashMap<>();
        nestedObject.put("id","f004ce90-66e8-45ae-a15d-23783f92ef51");
        nestedObject.put("name","MyWorkspace6");
        nestedObject.put("type","personal");
        nestedObject.put("description","Rest assured created this");

        mainObject.put("workspace",nestedObject);

        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().assertThat().body("workspace.name", equalTo("MyWorkspace6"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
}
