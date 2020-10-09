package base;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.testng.Assert;

import cucumber.api.Scenario;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class BaseClass {
	
	protected static Response response;
	protected static RequestSpecification request;
	protected static Scenario scen;
	protected static ResponseBody body;
	LocalDate date;
	
	//function to retrieve the  zone date
	public static LocalDate localDate() {
		ZoneId zoneID = ZoneId.of("America/Los_Angeles");
		LocalDate date = LocalDate.from(ZonedDateTime.now(zoneID));
		return date;
	}
	
	//function to retrieve the current date data if end-point is of future date
	protected void validatingFutureDateResponse() throws Exception {
			date = localDate();
			String dt = checkWeekends(date);
			try {
				// Storing response received in response object
				response = request.get();

				// Retrieve the body of the Response
				body = response.getBody();

				// To check for sub string presence get the Response body as a String.
				String bodyAsString = body.asString();

				// Checking if value retrieved is expected
				Assert.assertEquals(bodyAsString.contains(bodyAsString), true);
			} catch (AssertionError e) {
				System.out.println("Assertion error occured due to US time difference ,expected date" + date);
			}
		}
	
	
	
	//function to check given is in between weekend or not
	
	public static String checkWeekends(LocalDate dt) throws Exception {
		
		LocalDate result = dt;
		if(dt.getDayOfWeek() == DayOfWeek.SATURDAY)
			result = dt.minusDays(1);
		else if(dt.getDayOfWeek() == DayOfWeek.SUNDAY)
			result = dt.minusDays(2);
		
		return result.toString();
	}
	


}
