package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AutomateHeaders {

    @Test
    public void multiple_headers() {
        // you can create header object or pass values directly
        Header header = new Header("header", "value2");
        given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                header(header).
                header("x-mock-match-request-headers", "header").
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    @Test
    public void multiple_headers_using_Headers() {
        // you can create header object or pass values directly
        Header header = new Header("header", "value2");
        Header header2 = new Header("x-mock-match-request-headers", "header");
        // creating multiple headers
        Headers headers = new Headers(header,header2);
        given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    @Test
    public void multiple_headers_using_Headers_map() {
        // you can also store headers in map
        HashMap<String,String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    @Test
    public void multi_value_header() {
        // you can also store headers in map // you cant use hashmap for multiValue headers bc no duplicate key value
        //HashMap<String,String> headers = new HashMap<>();
        Header header1 = new Header("header", "value2");
        Header header2 = new Header("x-mock-match-request-headers", "header");

        Headers headersObjects = new Headers(header1,header2);


        given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headersObjects).
                log().headers().
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
    @Test
    public void assert_headers() {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200).
                header("respHeader","resValue2");
    }
    @Test
    public void extract_response_headers() {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200).extract().
                headers();
        for(Header h : extractedHeaders){
            System.out.println("Header name and value = " + h.getName() + " , " + h.getValue());
        }
        System.out.println("Header name = " + extractedHeaders.get("respHeader").getName());
        System.out.println("Header value = " + extractedHeaders.get("respHeader").getValue());
        System.out.println("Header value = " + extractedHeaders.getValue("respHeader"));
    }
    @Test
    public void extract_multiValue_response_header() {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://910479f0-365c-4607-be85-69a204529e11.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200).extract().
                headers();
        List<String> values = extractedHeaders.getValues("multiValueHeader");
        for(String s : values){
            System.out.println(s);
        }
    }

}
