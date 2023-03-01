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

public class RequestComplexJsonArray {
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
        List<Integer> idArray = new ArrayList<>();
        idArray.add(5);
        idArray.add(9);
        HashMap<String, Object> batterMap2 = new HashMap<>();
        batterMap2.put("id",idArray);
        batterMap2.put("type","Chocolate");
        HashMap<String, Object> batterMap1 = new HashMap<>();
        batterMap1.put("id","1001");
        batterMap1.put("type","Regular");

        List<Map<String,Object>> batterArray = new ArrayList<>();
        batterArray.add(batterMap1);
        batterArray.add(batterMap2);
        HashMap<String,List<Map<String,Object>>> batters = new HashMap<>();
        batters.put("batter",batterArray);

        List<String> typeArray = new ArrayList<>();
        typeArray.add("test1");
        typeArray.add("test2");
        HashMap<String, Object> toppingMap2 = new HashMap<>();
        toppingMap2.put("id","5002");
        toppingMap2.put("type",typeArray);
        HashMap<String, Object> toppingMap1 = new HashMap<>();
        batterMap1.put("id","5001");
        batterMap1.put("type","None");

        List<HashMap<String, Object>> toppingArray = new ArrayList<>();
        toppingArray.add(toppingMap1);
        toppingArray.add(toppingMap2);

        HashMap<String, Object> mainHashMap = new HashMap<>();
        mainHashMap.put("id","0001");
        mainHashMap.put("type","donut");
        mainHashMap.put("name","Cake");
        mainHashMap.put("ppu",0.55);
        mainHashMap.put("batters", batters);
        mainHashMap.put("topping", toppingArray);

    given().
            body(mainHashMap).
    when().
            post("/postComplexJson").
    then().
            log().all().
            assertThat().body("msg", equalTo("success"));
    }
}
