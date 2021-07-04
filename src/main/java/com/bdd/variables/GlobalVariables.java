package com.bdd.variables;

import java.sql.Connection;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.LinkedList;

import io.restassured.response.Response;

/**
 * This Class contains all the global variables set for the framework
 * 
 * 
 */
public class GlobalVariables {

	public static final String FIREFOX_DRIVER_PATH="src/test/resources/drivers/geckodriver.exe";
	public static final String CHROME_DRIVER_PATH="src/test/resources/drivers/chromedriver.exe";
	public static final String IE_DRIVER_PATH="src/test/resources/drivers/IEDriverServer.exe";
	public static RemoteWebDriver driver = null;
	public static final DesiredCapabilities capability = null;
	public static String featureName,currentScenarioName,ScenarioName,logFolderName,logFolder,logFileName;
    public static boolean skipStatusFlag=false;
    
    //api 
    public static Response responseValue;
    public static String uri,finalURI;
    
    //db
    public static Connection getdbConnection=null;
    public static Connection con;
    public static LinkedHashMap<String,LinkedList<String>> resultvalues;
}
