package com.bdd.impl;
import static com.bdd.utils.WebDriverUtils.*;
import static com.bdd.variables.GlobalVariables.*;
import static com.bdd.utils.SeleniumSupportFunctions.*;
import org.openqa.selenium.JavascriptExecutor;
import static com.bdd.utils.PropertiesImpl.*;
import org.openqa.selenium.WebElement;
public class AppAccess {
public static void accessAPP(String url) {
	try {
		if(driver==null || driver.toString().contains("null")) {
			initializeDriver();
			openUrl(url);
		}
	}catch(Throwable e) {
		e.printStackTrace();
	}
}

public static void openUrl(String url) {
	try{
		driver.get("chrome://settings/clearBrowserData");
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement cleardata=(WebElement)js.executeScript("return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')");
	cleardata.click();
	Thread.sleep(5000);
	driver.get(getPropertyValue(url));
	waitForPageToLoad();
	}catch(Throwable e) {
		e.printStackTrace();
	}
}
}
