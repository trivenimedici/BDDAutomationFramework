#demosite automation feature/scenarios
@demotest
Feature: Demo for selenium functions
Background:
Given User navigates to "dev_url"

@chapter1validation
#Author:Triveni 
Scenario Outline: Validation of chapter 1 functionalities in demo site
When User clicked on "<link>"
Then Verify "<message>" is displayed
Examples:
|link|
|Chapter1|



