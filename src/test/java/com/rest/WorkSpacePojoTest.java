package com.rest;

import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WorkSpacePojoTest {
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
    public void workspace_serialize_deserialize_post_request_payload_as_map() {
        Workspace workspace = new Workspace("WorkPojo","personal","description");
        WorkspaceRoot workRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot desWorkspaceRoot = given().
                body(workRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                extract().response().
                as(WorkspaceRoot.class);

        assertThat(desWorkspaceRoot.getWorkspace().getName(),equalTo(workRoot.getWorkspace().getName()));
        assertThat(desWorkspaceRoot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }
    @Test (dataProvider = "workspace")
    public void workspace_serialize_deserialize_DataProvider(String name, String type, String description) {
        Workspace workspace = new Workspace(name,type,description);
        WorkspaceRoot workRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot desWorkspaceRoot = given().
                body(workRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                extract().response().
                as(WorkspaceRoot.class);

        assertThat(desWorkspaceRoot.getWorkspace().getName(),equalTo(workRoot.getWorkspace().getName()));
        assertThat(desWorkspaceRoot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }
    @DataProvider(name = "workspace")
    public Object[][] getWorkspace() {
        return new Object[][]{
                {"myWorkspaceDataProvider", "personal", "description"},
                {"myWorkspaceDataProvider2", "personal", "description"}
        };
    }
}
