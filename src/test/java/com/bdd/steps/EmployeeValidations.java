package com.bdd.steps;

import static com.bdd.impl.EmployeeImplementations.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeValidations {
	public static LinkedHashMap<String,LinkedList<String>> rs;
	@When("^User execute the query for given employee name \"([^\"]*)\"$")
	public void user_execute_query_for_employee(String employeeName) {
		rs=getEmployeeID(employeeName);
	}
	@Then("The DB should fetch the id of the employee")
	public static void the_db_should_fetch_the_id_of_the_employee() {
		String actualvalue=rs.get("EMPLOYEEID").toString().replace("[", "").replace("]", "");
		String expectedvalue="testid1";
		System.out.println("actualvalue is "+actualvalue+" expected value is "+expectedvalue);
	  Assert.assertEquals(actualvalue,expectedvalue );
	}
}
