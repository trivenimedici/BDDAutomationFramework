package com.bdd.utils;
import static com.bdd.utils.PropertiesImpl.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static com.bdd.variables.GlobalVariables.*;

/**
 * This Class contains methods written which would be specific to the driver
 * operations
 * 
 * 
 */
public class WebDriverUtils  {
	private static String browser = null;
	private static boolean isActive = false;

	public WebDriverUtils(String browser) {
		this.browser = browser;
	}

	public static void initializeDriver() throws Exception {
		browser=getPropertyValue("browser");
		getDriver(browser);
	}

	public static String getBrowser() {
		return browser;
	}

	public static RemoteWebDriver getDriver(String browser) {

		String br = browser.toUpperCase();
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.prompt_for_download", "false");
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("profile.content_settings.pattern pairs.*.multiple-automatic-downloads", 1);
		prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
		prefs.put("credentials_enable_service", false);
		try {
			switch (br) {
			case "CHROME":
				ChromeOptions chroptions = new ChromeOptions();
				chroptions.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking",
						"--disable-infobars");
				chroptions.setExperimentalOption("prefs", prefs);
				chroptions.setExperimentalOption("useAutomationExtension", false);
				setDriverSystemProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
				driver = new ChromeDriver(chroptions);
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				break;
			case "FIREFOX":
				FirefoxOptions frxoptions = new FirefoxOptions();
				frxoptions.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking",
						"--disable-infobars");
				setDriverSystemProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
				driver = new FirefoxDriver(frxoptions);
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				break;
			case "IE":
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.requireWindowFocus();
				ieOptions.introduceFlakinessByIgnoringSecurityDomains();
				setDriverSystemProperty("webdriver.ie.driver", IE_DRIVER_PATH);
				driver = new InternetExplorerDriver(ieOptions);
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				break;
			default:
				Log.error("Browser not supported");
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return driver;
	}

	private static void setDriverSystemProperty(String driver, String propertyName) {
		System.setProperty(driver, propertyName);
		return;
	}
	
	public static void quitDriver() {
		if(driver!=null) {
			driver.quit();
			Log.info("The driver is closed");
			driver = null;
		}
		isActive = false;
	}
	public static boolean isActive() {
		return isActive;
	}
}
