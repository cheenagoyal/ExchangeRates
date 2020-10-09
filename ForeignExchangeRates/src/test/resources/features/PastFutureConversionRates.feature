@PastFutureConversionRates @Test
Feature: To get past/future dates conversion rates from Foreign Exchange Rates API
	Description: Validate if API is fit for purpose in the use of past conversion rates for financial reasons

Background: 
	Given Foreign Exchange Rates API is accessible
	
Scenario: Validate the correct response with latest date
	When User hits the API with endpoint as "?base=USD"
	Then API should respond with the base value as "USD"
	
Scenario: Validate results on providing incorrect/incomplete endpoint
	When User hits the API with endpoint as "?base=123"
	Then Get response with status code as 400
	And Error message should be displayed as "Base '123' is not supported."
	
Scenario: Assert the success status of past conversion rates response
	When User hits the API with endpoint as "/2010-01-12"
	Then Get response with status code as 200
	
Scenario: Assert the success response - content of the past conversion rates response
	When User hits the API with endpoint as "/2010-01-12?base=INR"
	Then API should respond with the base value as "INR"
	
Scenario: Hit the API with future date and validate current date data should present
	When User hits the API with endpoint as "/2021-05-12"
	Then API should return the current date rates