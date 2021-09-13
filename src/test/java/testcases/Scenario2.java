package testcases;
import java.io.File;
/**
* @author Author: Alex Podimov
*
*/
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


//can run from here run as test-ng

public class Scenario2 {

	// ResponseSpecification, some defaults to verify on every call
	ResponseSpecification test1 = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
	
	ResponseSpecification test2 = new ResponseSpecBuilder().expectStatusCode(201)
			.expectContentType(ContentType.JSON).build();
	
	
	static String basePathTest1 = "https://swapi.dev";
	static String basePathTest2 = "https://reqres.in";
	File jsonDataFile = new File(System.getProperty("user.dir") + "/src/test/resources/payload/S1t2_json_file.json");
	
	@Test
	public void test01(){ 

	String testValue = "Luke Skywalker";	
		
	RestAssured.baseURI = basePathTest1;
	RestAssured.
	       get("/api/people/1").
	       	then().assertThat().spec(test1).
	       and().extract().body().jsonPath().get("name").equals(testValue);

	}
	
	//the following cannot be succeeded as I think the API cannot get name and title
	//the response header will be filled (e.g. HTTP/1.1 201 Created, and other stuff)
	//body will have : {
    // "id": "773",
    //  "createdAt": "2021-09-13T00:35:20.159Z"
	//	}
	//I was unable to find the data in /api/users for my POST request
	@Test
	public void test2(){ 
		
		RestAssured.baseURI = basePathTest2;
		RestAssured.given().body(jsonDataFile.toString()).
	       when().post("/api/users").
	      then().assertThat().spec(test2).and().log().all();
	       
	}
}
