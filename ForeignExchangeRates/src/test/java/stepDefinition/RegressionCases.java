package stepDefinition;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;

import org.testng.Assert;

import base.BaseClass;

public class RegressionCases extends BaseClass {
	
	protected static final String BASE_URL = "https://api.ratesapi.io/api/latest";
	private int actualStatusCode;
	
	
	// * All scenarios - Will run at start of each scenarios active for execution
	 

	@Given("^Foreign Exchange Rates API is accessible$")
	public void am_an_authorized_user() throws Throwable {
		System.out.println("--------API UNDER TEST-------" + BASE_URL);

		RestAssured.baseURI = BASE_URL;
		request = RestAssured.given();
		
		// Storing response received in response object
		response = request.get();

		System.out.println("API response recevied : " + response.asString());
	}
	

	// Method to Hit the specific EndPoints
	@When("^User hits the API with endpoint as \"([^\"]*)\"$")
	public void  hitEndpoints(String endPoint) {

		System.out.println("-----Hiting API with specific endpoint ----" + endPoint + " and getting response");

		// Specifying URI to test with end-point
		RestAssured.baseURI = BASE_URL+ endPoint;
		request = RestAssured.given();

		System.out.println("URI hit with endpoint is : " + RestAssured.baseURI);
		
	
	}
	
	
	// * Validating API responses
	
	 
	@Then("^Get response with status code as (\\d+)$")
	public void getStatusCode(int expectedStatusCode) throws Throwable {
		
		// Extracting status code to verify
		actualStatusCode = response.getStatusCode();

		// Checking if value retrieved is as expected
		Assert.assertEquals(expectedStatusCode, actualStatusCode);

		System.out.println("Response code recieved : " + expectedStatusCode);
	}

	//Verify the Response base value
	
	@Then("^API should respond with the base value as \"([^\"]*)\"$")
	public void userShouldGetBaseValue(String expectedBaseValue) throws Throwable {
		System.out.println("---------- Assert the base values from Response ----");

		// Retrieve the body of the Response
		body = response.getBody();
		
		String bodyasString = body.asString();

		// Checking if value retrieved is as expected
		Assert.assertEquals(bodyasString.contains(expectedBaseValue), true);
		System.out.println("Base Value Received : " + expectedBaseValue);

	}
	
	
	// * Verify the error message for invalid query parameters
	 

	@Then("^Error message should be displayed as \"([^\"]*)\"$")
	public void verifyErrorMessage(String expectedErrorMessage) throws Throwable {
		System.out.println("-----Verify the error message for incorrect endpoint -------");

		body = response.getBody();
		
		String responseBody = body.asString();
				
				// Checking if value retrieved is expected
		Assert.assertEquals(responseBody.contains(expectedErrorMessage), true);

		
		System.out.println("Error message received : " + expectedErrorMessage);
	}

	@Then("^API should return the current date rates$")
	public void getCurrentDateRates() throws Throwable {
		System.out.println("Verifying current date data should return although the endpoint is of future date");

		// Method for retrieving current date data if the end-point is of future date
		validatingFutureDateResponse();
	}



}
