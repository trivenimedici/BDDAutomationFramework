package com.bdd.hooks;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.bdd.utils.Log;

import static com.bdd.utils.WebDriverUtils.quitDriver;
import static com.bdd.variables.GlobalVariables.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
public class GlobalHooks {
//public Scenario scenarioVal=null;
public String scenarioID="";
Date ScenarioTimeStart=null;
Date ScenarioTimeEnd=null;
SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy HH:MM:SS.SSSS");
SimpleDateFormat dateformat2 = new SimpleDateFormat("HH:mm:ss.SSSS");
boolean tableHeaderWrote=false;
String projectPath=System.getProperty("user.dir");
public static String tableDataOutput="";
@Before
public void beforeScenario(Scenario scenario) {	
	System.setProperty("user.home", System.getProperty("user.dir") + "\\logger.logs");
	ScenarioTimeStart= new Date();
	//Utility.message=scenario;
	ScenarioName=scenario.getName();
	//check feature name is equivalent to current feature name
//	if(ScenarioName==null) {
//		ScenarioName=currentScenarioName;
//		Log.info("Scenario Name :"+ScenarioName);
//	}else if(!ScenarioName.equals(currentScenarioName)) {
//		ScenarioName=currentScenarioName;
//		Log.info("Scenario Name :"+ScenarioName);
//	}else {
//		Log.info("Scenario Name :"+ScenarioName);
//	}
	Log.info("Scenario Name :"+ScenarioName);
	System.out.println("Running scenario : "+scenario.getName());
//	scenarioVal=scenario;
	
	//scenarioID =scenario.getName().substring(0,scenario.getName().length());
	//Log.debug("<p style=color:Tomato;><b> ScenarioName :-</p> " + "<p style=color:green;><b>" + ScenarioName+ "</p>");
}

@After
public void afterScenario(Scenario scenario) {
	quitDriver();
	ScenarioTimeEnd=new Date();
	Log.info(scenario.getName());
	Log.info("Status :"+scenario.getStatus());
	
	// if status of the scenario execution is skipped
	if(!skipStatusFlag) {
		Log.endTestScript(scenario.getName(), scenario.getStatus().toString());	
	}else {
		Log.endTestScript(scenario.getName(), "SKIPPED");	
		skipStatusFlag=false;
	}
	String s=scenario.getStatus().toString();
	System.out.println("\n\nScenario execution status :"+s+"\n\n");
	
	
	//if status of scenario execution failed
	if(scenario.isFailed()) {
		String screenshotName=scenario.getName().replaceAll(" ", "_");
		try {
			//take screenshot to specified location
			File sourcepath=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			//destination path to store screenshot
			File destinationPath=new File(projectPath+"/ExecutionReports/screenshots/"+screenshotName+".png");
			Log.info("Screenshot path "+destinationPath);
			
			
			//copy screenshot from source to destination path
			FileUtils.copyFile(sourcepath,destinationPath);
			scenario.attach(FileUtils.readFileToByteArray(destinationPath), "image/png",screenshotName);
			FileInputStream is= new FileInputStream(destinationPath);
			byte[] imageBytes=IOUtils.toByteArray(is);
			String base64=Base64.encodeBase64String(imageBytes);
			//Reporter.addScreenCaptureFromPath("data:image/png;base64,"+base64);
			//Reporter.addScreenCaptureFromPath(projectPath+"/ExecutionReports/screenshots/"+screenshotName+".jpg");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
//	long difference=(ScenarioTimeEnd.getTime()- ScenarioTimeStart.getTime())/1000;
//	String formattedStatus="";
//	if(scenario.getStatus().toString().equalsIgnoreCase("passed"))
//		formattedStatus="<b>font style='color:green'>PASSED</font></b>";
//	else
//		formattedStatus="<b>font style='color:red'>FAILED</font></b>";
//	tableDataOutput+=
//	"          <tr>\r\n"+
//	"          <td>"+scenario.getSourceTagNames()+"</td>\r\n"+
//	"          <td>"+scenario.getName()+"</td>\r\n"+
//	"          <td>"+formattedStatus+"</td>\r\n"+
//	"          <td>"+dateformat.format(ScenarioTimeStart)+"</td>\r\n"+
//	"          <td>"+dateformat.format(ScenarioTimeEnd)+"</td>\r\n"+
//	"          <td>"+difference+" s</td>\r\n"+
//	"          </tr>\r\n";
	
					
}
}
