package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestColorJson {
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
    public void validate_post_request_payload_json_complex() {
        List<Integer> rgba1 = new ArrayList<>();
        rgba1.add(255);
        rgba1.add(255);
        rgba1.add(255);
        rgba1.add(1);
        HashMap<String, Object> code1 = new HashMap<>();
        code1.put("rgba",rgba1);
        code1.put("hex","#000");
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("color","black");
        item1.put("category","hue");
        item1.put("type","primary");
        item1.put("code",code1);

        List<Integer> rgba2 = new ArrayList<>();
        rgba2.add(0);
        rgba2.add(0);
        rgba2.add(0);
        rgba2.add(1);
        HashMap<String, Object> code2 = new HashMap<>();
        code2.put("rgba",rgba2);
        code2.put("hex","#FFF");
        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("color","white");
        item2.put("category","value");
        item2.put("code",code2);


        List<Map<String,Object>> colorArray = new ArrayList<>();
        colorArray.add(item1);
        colorArray.add(item2);

        HashMap<String,List<Map<String,Object>>> colors = new HashMap<>();
        colors.put("colors",colorArray);


    given().
            body(colors).
    when().
            post("/postColorsJson").
    then().
            log().all().
            assertThat().body("msg", equalTo("You did it!"));
    }
    @Test
    public void validate_post_request_payload_json_complex2() {
        File file = new File("src/main/resources/colors.json");
        given().
                body(file).
                when().
                post("/postColorsJson").
                then().
                log().all().
                assertThat().body("msg", equalTo("You did it!"));
    }
}
