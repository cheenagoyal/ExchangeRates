package stepDefinition;

import base.BaseClass;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;

public class AcceptanceCriteria extends BaseClass {

	//REST API under test
	private static final String BASE_URL = "https://api.ratesapi.io/api";
	private int actualStatusCode;

	/*
	 * Precondition for all scenarios - Will run at start of each scenarios active for execution
	 */
	
	@Given("^Exchange Rates API is accessible$")
	public void i_am_an_authorized_user() throws Throwable {
		System.out.println("--------API UNDER TEST-------" + BASE_URL);

		// Specify URI to test
		RestAssured.baseURI = BASE_URL;
		request = RestAssured.given().log().all().header("Content-Type", "application/json");

	}

	// Method to Hit the EndPoints
	@When("^User hits the API with endpoint with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void  hitEndpoints(String date, String base, String symbols) {

		response = request.queryParam("base", base).queryParam("symbols", symbols).pathParam("val", date).when()
				.get("/{val}").then().log().body().extract().response();

	}

	
	// Validating the response code

	@Then("^User should respond with status code as (.*) code$")
	public void userShouldGetStatusCode(int expectedResponseCode) {

		// Extracting status code to verify
		actualStatusCode = response.getStatusCode();

		// Checking if value retrieved is expected
		Assert.assertEquals(expectedResponseCode, actualStatusCode);
		System.out.println("--------Actual Status Code-------" + actualStatusCode);

	}
	
	
	
	
	@Then("^API should respond with base value \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void ValidateBase(String expectedDate, String expectedBase, String expectedSymobols) {
		
		//Check if retrieved base value is as expected
		if (response.statusCode() == 200) {

			if (expectedBase == null || expectedBase.isEmpty()) {

				String actualBaseValue = response.jsonPath().getString("base");
				Assert.assertEquals("EUR", actualBaseValue);
				System.out.println("------Actual Base Value-----" + actualBaseValue);
			} else {
				String actualBaseValue = response.jsonPath().getString("base");
				Assert.assertEquals(expectedBase, actualBaseValue);
				System.out.println("------Actual Base Value------" + actualBaseValue);
			}
		}

	}

	
	
}
