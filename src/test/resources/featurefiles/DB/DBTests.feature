#DB automation testing features



@demotest
Feature: Demo for DB automation testing


Background:
Given User Established Connection to Database

@getEmployeeID
#Author:Triveni 
Scenario Outline: Validation to get the employee id for the given employee
When User execute the query for given employee name "<employeename>"
Then The DB should fetch the id of the employee
And Close the database connection
Examples:
|employeename|
|testuser1|



