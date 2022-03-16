package org.zbib.testcases.API;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class validateBibliography {
	private static String payload ="{\r\n"
			+ "  \"title\": \"HP Biblography\",\r\n"
			+ "  \"citationStyle\": \"chicago-note-bibliography\",\r\n"
			+ "  \"items\": [\r\n"
			+ "    {\r\n"
			+ "      \"version\": 0,\r\n"
			+ "      \"itemType\": \"book\",\r\n"
			+ "      \"tags\": [],\r\n"
			+ "      \"creators\": [\r\n"
			+ "        {\r\n"
			+ "          \"creatorType\": \"author\",\r\n"
			+ "          \"firstName\": \"iohiou\",\r\n"
			+ "          \"lastName\": \"ijlij\"\r\n"
			+ "        }\r\n"
			+ "      ],\r\n"
			+ "      \"key\": \"SDTNXI8R\",\r\n"
			+ "      \"title\": \"kjklj\"\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}";
	
	public String key;
	
	@BeforeClass
	public void setters() {
		
		RestAssured.baseURI ="https://v1snar4wu4.execute-api.us-east-1.amazonaws.com";
	}
	
	@Test(priority=1)
	public void createNewBiblography() {
		Response response = given().basePath("/1").contentType("application/json").body(payload).when().post();
		assertEquals(200, response.getStatusCode());
		String json = response.asString();
		JsonPath jp = new JsonPath(json);
		key = jp.get("key");
		System.out.println(key);
	}
	
	@Test(priority=2)
	public void viewBiblograhy() {
			Response response = given().basePath("/1/"+ key).when().get();
			assertEquals(200, response.getStatusCode());
			String json = response.asString();
			JsonPath jp = new JsonPath(json);
			String title = jp.get("title");
			assertEquals("HP Biblography", title);	
	}
}
