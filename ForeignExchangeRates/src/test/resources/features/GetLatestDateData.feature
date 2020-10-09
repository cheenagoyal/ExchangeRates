@LatestCoversionRates @Test
Feature: GET API for latest Foreign Exchange Rates
  Validate that the API is fit for purpose in the use of the exchange rate for financial reasons 
  

Background:
  Given Exchange Rates API is accessible
  
  
 Scenario Outline: Latest Exchange Rates
     When User hits the API with endpoint with "<Date>", "<Base>" and "<Symbols>"
     Then User should respond with status code as <ExpectedStatusCode> code
     Then API should respond with base value "<Date>", "<Base>" and "<Symbols>"
    

    Examples: 
     |Date           |Symbols   |Base       |ExpectedStatusCode|
     |latest         |          |           | 200              |
     |latest         |GBP       |USD        | 200              |
     |latest         |GBP       |INR        | 200              |
     |latest         |GBP       |           | 200              |
     |latest         |          |USD        | 200              |