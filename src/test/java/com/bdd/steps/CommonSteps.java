package com.bdd.steps;


import static com.bdd.pages.Chapter1.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CommonSteps {
	@Given("^User navigates to \"([^\"]*)\"$")
	public void user_navigates_to_url(String appUrl) {
		accessAppUrl(appUrl);
	}
	
	@When("^User clicked on \"([^\"]*)\"$")
	public void click_on_function(String webelement) {
		clickonLink1(webelement );
	}
}
