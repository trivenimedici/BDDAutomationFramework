package com.bdd.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static com.bdd.variables.GlobalVariables.*;

import java.util.Map;

import static com.bdd.utils.PropertiesImpl.*;
import static com.bdd.impl.APIImpl.*;

public class APICommonSteps {
@Given("^User perform GET operation for \"([^\"]*)\" with \"([^\"]*)\"$")
public void user_performs_get_operation(String endpoint ,String appuri) throws Exception {
	uri=getPropertyValue("app_uri");
	String url=uri+"/"+endpoint;
	sendRequestGet(url);
}

@Given("^User perform POST operation for \"([^\"]*)\" with \"([^\"]*)\"$")
public void user_perform_post_operation(String endpoint,String appuri) throws Exception {
	uri=getPropertyValue("app_uri");
	finalURI=uri+"/"+endpoint;
}

@And("^Pass the body Parameters for the post request$")
public void pass_body_parameters_for_post_request(DataTable tablevalues) {
	Map<String,String> mapvalues=tablevalues.asMap(String.class, String.class);
	sendPostRequest(mapvalues, finalURI);
}
 @Then("^The response code for the request should be \"([^\"]*)\"$")
 public void response_code_for_request(int responseCode) {
	validateResponseCode(responseCode); 
 }
 
 @Then("^The response header should have \"([^\"]*)\" for \"([^\"]*)\"$")
 public void response_header_should_have(String responseFieldValue,String responseField) {
	 validateResponseHeaderValue(responseFieldValue, responseField);	 
 }
 
 @And("^User closes the connection$")
 public void user_closes_connection() {
	 closeAPIConnection();
 }
 
 @And("^Get the response header value$")
 public void get_response_header_value() {
	 getResponseHeaderValue();
 }
 

 @And("^Get the response body for the request$")
 public void get_response_body_value() {
	 getResponseBodyValue();
 }
 
 @And("^The response body should have below values$")
 public void response_body_should_have_values(DataTable values) {
	 Map<String,String> mapvalues=values.asMap(String.class,String.class);
	 validateResponseBodyValue(mapvalues);
 }
 
}
