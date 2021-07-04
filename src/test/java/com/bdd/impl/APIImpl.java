package com.bdd.impl;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;

import io.restassured.config.ConnectionConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import static com.bdd.variables.GlobalVariables.*;

public class APIImpl {

	// method to initailize and setup connection for get request
	public static Response sendRequestGet(String url) {
		responseValue = given().contentType(ContentType.JSON).relaxedHTTPSValidation().when().get(url);
		return responseValue;
	}

	// method to initailize and setup connection for post request
	public static Response sendPostRequest(Map<String, String> values, String url) {
		responseValue = given().contentType(ContentType.JSON).relaxedHTTPSValidation().with().body(values).when()
				.post(url);
		return responseValue;
	}
	
	//method to verify response status code
	public static void validateResponseCode(int responseCode) {
		int actualCode=responseValue.getStatusCode();
		System.out.println("The actual response code value is "+actualCode);
		Assert.assertEquals(actualCode, responseCode);
	}
	
	//method to validate response header value
	public static void validateResponseHeaderValue(String responseHeaderValue, String responseheaderField) {
		String actualValue=responseValue.getHeader(responseheaderField);
		System.out.println("The actual response header value is "+actualValue);
		Assert.assertEquals(actualValue, responseHeaderValue);
	}
	
	//method to validate response body value
	public static void validateResponseBodyValue(Map<String,String> responseBodyValue) {
		String actualValue= (responseValue.getBody()).toString();
		String expRespValue= ((responseBodyValue.toString()).replace("=", ":")).replace(", ", ",");
		System.out.println("The expected response body value is "+expRespValue);
		System.out.println("The actual response body value is "+actualValue);
		Assert.assertEquals(actualValue, expRespValue);
	}
	
	//method to close the connection
	public static void closeAPIConnection() {
		ConnectionConfig.connectionConfig().closeIdleConnectionConfig();
	}
	
	//method to get the response header value
	public static Headers getResponseHeaderValue() {
		Headers headerValueofRequest= responseValue.getHeaders();
		System.out.println("The header value of the request are "+headerValueofRequest);
		return headerValueofRequest;
	}
	
	
	//method to get the response body value
	public static String getResponseBodyValue() {
		String bodyValueofRequest =(responseValue.getBody()).asString();
		System.out.println("The response body value of the request are "+bodyValueofRequest);
		return bodyValueofRequest;
	}
}
