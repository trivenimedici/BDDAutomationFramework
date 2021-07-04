#apidemo automation feature/scenarios



Feature: Demo for  api automation

@demoAPItest
#Author:Triveni                Date:16/05/2021
Scenario Outline: Validation of get request with rest assured
Given User perform GET operation for "<endpoint>" with "app_uri"
Then The response code for the request should be "<responsecode>"
#And The response header should have "<responseheadervalue>" for "<responseheaderfield>"
#Then Get the response header value
And User closes the connection


Examples:
|responsecode|endpoint|responseheadervalue|responseheaderfield|
|403|test|test|test|

#Author:Triveni                Date:16/05/2021
Scenario Outline: Validation of post request with rest assured
Given User perform POST operation for "<endpoint>" with "app_uri"
When Pass the body Parameters for the post request
|"test"|"test"|
Then The response code for the request should be "<responsecode>"
When Get the response body for the request
Then The response body should have below values
|"test"|"test"|
And User closes the connection


Examples:
|endpoint|responsecode|
|test|200|

#Author:Triveni                Date:16/05/2021
Scenario Outline: Validation of post request with rest assured with json having array values
Given User perform POST operation for "<endpoint>" with "app_uri"
When Pass the body Parameters for the post request
|"test"|"test"|
|test|{"test1":[all],"test2":["all"]}|
Then The response code for the request should be "<responsecode>"
When Get the response body for the request
Then The response body should have below values
|"test"|"test"|
And User closes the connection

Examples:
|endpoint|responsecode|
|test|200|