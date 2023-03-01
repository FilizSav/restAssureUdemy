package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class AutomateGet {

    @org.testng.annotations.Test
    public void validate_get_status_code(){
        System.out.println(given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
                when().
                get("/workspaces").
                then().log().all().
                assertThat().statusCode(200).
                body("workspaces.name",hasItems("Project1", "MyWorkspace"),
                        "workspaces.type", hasItems("personal", "personal"),
                        "workspaces[0].name", equalTo("Project1"),
                        "workspaces[1].name",is(equalTo("MyWorkspace")),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("Project1")));
    }

    @Test
    public void extract_response() {
        Response response =  given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
        when().
                get("/workspaces").
        then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("response = " + response.asString());
    }
    @Test
    public void extract_single_value_response() {
        Response response =  given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
        when().
                get("/workspaces").
        then().
                assertThat().
                statusCode(200).
                extract().
                response();
        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println("Jsonpath object workspace name  = " + jsonPath.getString("workspaces[0].name"));
        System.out.println("response object workspace name  = " + response.path("workspaces[0].name"));
    }
    @Test
    public void extract_single_value_response_toString() {
        String response =  given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
        when().
                get("/workspaces").
        then().
                assertThat().
                statusCode(200).
                extract().
                response().asString();
        //covert object to a string  after response().path and then directly print
        //System.out.println("response object workspace name  = " + response);
        System.out.println("Jsonpath object workspace name asString  = " + JsonPath.from(response).getString("workspaces[0].name"));

    }
    @Test
    public void hamcrest_assert_extracted_response() {
        String response =  given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
        when().
                get("/workspaces").
        then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        assertThat(response, equalTo("Project1"));

        //TestNg Assertion
        Assert.assertEquals(response, "Project1");
        //covert object to a string  after response().path and then directly print
        //System.out.println("response object workspace name  = " + response);
        //System.out.println("Jsonpath object workspace name asString  = " + JsonPath.from(response).getString("workspaces[0].name"));

    }
    @org.testng.annotations.Test
    public void validate_response_hamcrest(){
        System.out.println(given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
        when().
                get("/workspaces").
        then().log().all().
                assertThat().statusCode(200).
                body("workspaces.name",contains("Project1","MyWorkspace"),
                "workspaces[0]", hasEntry("id","6a2503a0-73ad-4f1c-9016-71e75051d093")));
    }
    @Test
    public void request_response_logging(){
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
                log().all().
        when().

                get("/workspaces").
        then().log().all().
                assertThat().statusCode(200).
                body("workspaces.name",contains("Project1","MyWorkspace"),
                        "workspaces[0]", hasEntry("id","6a2503a0-73ad-4f1c-9016-71e75051d093"));
    }

    @Test
    public void log_only_if_validation_fails(){
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
                //We can also log using the config then remove all log //restAssured method
                //config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                log().ifValidationFails().
        when().
                get("/workspaces").
        then().
                log().ifValidationFails().
                assertThat().statusCode(200).
                body("workspaces.name",contains("Project1","MyWorkspace"),
                        "workspaces[0]", hasEntry("id","6a2503a0-73ad-4f1c-9016-71e75051d093"));
    }
    @Test
    public void log_blacklist_header() {
        Set<String> headers = new HashSet<>();
        headers.add("x-api-key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-63f6301bdd289a369a0499a3-5e3ffe63c85364d8a495d154c13acf2da8").
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().ifValidationFails().
        when().
                get("/workspaces").
        then().
                log().ifValidationFails().
                assertThat().statusCode(200);
    }
}
