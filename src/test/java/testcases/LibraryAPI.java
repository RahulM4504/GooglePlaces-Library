package testcases;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseTest.base;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class LibraryAPI extends base{

	@DataProvider(name = "AddBook")
	public Object[][] dataprovider() throws EncryptedDocumentException, IOException {
		Object[][] data = readexcel();
		return data;
	}

//Add Books specified in excel to the library 
	
	@Test(dataProvider ="AddBook" )
	public void AddBook(String isbn, String asile) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
				.body("{\r\n"
				+ "\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+asile+"\",\r\n"
				+ "\"author\":\"John foe\"\r\n"
				+ "}\r\n"
				+ "")
				.when().post("Library/Addbook.php")
				.then().log().all().statusCode(200).extract().response().asString();		
		JsonPath jp = new JsonPath(response);
		String SuccessMessage = jp.get("Msg");
		Assert.assertEquals(SuccessMessage, "successfully added");
	}


//Deletes Books added  in addbook test^
	
	@Test(dataProvider = "AddBook", dependsOnMethods = "AddBook")
	public void deleteBook(String isbn, String asile) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
				.body("{\r\n"
						+ "\"ID\" : \""+isbn+asile+"\"\r\n"
						+ "} \r\n"
						+ "")
				.when().post("/Library/DeleteBook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath jp = new JsonPath(response);
		String SuccessDeleteMsg = jp.getString("msg");
		Assert.assertEquals(SuccessDeleteMsg, "book is successfully deleted");


	}

}
