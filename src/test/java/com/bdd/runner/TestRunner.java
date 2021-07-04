package com.bdd.runner;

import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.bdd.utils.Log;
import io.cucumber.testng.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

import static com.bdd.utils.PropertiesImpl.*;

import static com.bdd.variables.GlobalVariables.*;
import static com.bdd.utils.WebDriverUtils.*;

@CucumberOptions(features = "src/test/resources/featurefiles", glue = { "com.bdd.steps",
		"com.bdd.hooks" }, tags = "@demoAPItest", plugin = { "pretty", "html:target/cucumber-report.html",
				"json:target/cucumber.json" }, monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
	public static TestNGCucumberRunner testRunner;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void setup() throws IOException {
		testRunner = new TestNGCucumberRunner(this.getClass());  
		Log.info("Project path in runner is : " + projectPath);
		loadProperties();
		Log.setLogDirectoryName();

	}

//	@Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
//	public void scenario(PickleWrapper pickleEvent, FeatureWrapper  featureWrapper) {
//		try {
//			testRunner.runScenario(pickleEvent.getPickle());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@DataProvider
	public Object[][] scenarios() {
//		if (testRunner == null) {
//			testRunner = new TestNGCucumberRunner(this.getClass());
//		}
		return testRunner.provideScenarios();
	}

	@AfterClass
	public void tearDown() throws IOException {
		quitDriver();
		Log.info("End of execution for scenario: " + ScenarioName);
		Log.info("Driver is closed");
		//logFileName = Log.setLogfile(ScenarioName);
	//	Log.copyFile(logFileName);
		testRunner.finish();
	}

}
