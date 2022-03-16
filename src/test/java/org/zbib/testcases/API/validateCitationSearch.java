package org.zbib.testcases.API;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;

public class validateCitationSearch {
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
	@BeforeClass
	public void setters() {
		
		RestAssured.baseURI ="https://t0guvf0w17.execute-api.us-east-1.amazonaws.com";
		RestAssured.basePath="/Prod/search";		
	}
	
	@Test(priority=1)
	public void performSearch() {
		given()
			.contentType("text/plain")
			.body("Jane Eyre")
			.when()
				.post()
			.then()
				.statusCode(300)
				.assertThat().body("9781783199051.description", equalTo("Brontë, Williams – 2015 – Oberon Books"))
				.assertThat().body("9788853007742.itemType", equalTo("book"))
				.assertThat().body("9788853007742.title", equalTo("Jane Eyre"))
				.log().all();
				
	}
}
