package testcases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class googlePlaceTest {
	
	String placeid;

	@Test(priority =1)
	public void AddPlaceTest() {		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type","application/json")
				.body("{\r\n"
						+ "  \"location\": {\r\n"
						+ "    \"lat\": -38.383494,\r\n"
						+ "    \"lng\": 33.427362\r\n"
						+ "  },\r\n"
						+ "  \"accuracy\": 50,\r\n"
						+ "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
						+ "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n"
						+ "    \"shop\"\r\n"
						+ "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n"
						+ "  \"language\": \"French-IN\"\r\n"
						+ "}\r\n"
						+ "")
				.when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jp =new JsonPath(response);
		placeid = jp.get("place_id");
		String Status = jp.get("status");
		System.out.println("Place ID:" + placeid);
		Assert.assertEquals(Status,"OK");

	}
	
	
	@Test(priority=2)
	public void getPlaceTest() {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String getResponse = given().header("Content-Type","application/json")
				.queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println("*****"+getResponse);
//		JsonPath jp = new JsonPath(getResponse);
//		String lat = jp.get("location.latitude");
		
		
		
	}
	
	
	
	
	

	@Test(priority=3)
	public void deletePlaceTest() {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String deleteResponse = given().queryParam("key", "qaclick123")
		.body("{\r\n"
				+ "    \"place_id\":\""+placeid+"\"\r\n"
				+ "}\r\n"
				+ "")
		.when().delete("/maps/api/place/delete/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp=new JsonPath(deleteResponse);
		String status = jp.get("status");
		Assert.assertEquals(status,"OK");

	}
	
	

}
