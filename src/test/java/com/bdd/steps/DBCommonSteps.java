package com.bdd.steps;

import static com.bdd.utils.DataBaseOps.*;

import java.sql.SQLException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class DBCommonSteps {
	@Given("^User Established Connection to Database$")
	public void user_connects_to_db() throws SQLException {
		connectToDataBase();
	}
	
	@And("^Close the database connection$")
	public void close_db_connection() {
		closeConnection();
	}
}
