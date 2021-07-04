package com.bdd.utils;

import static com.bdd.utils.PropertiesImpl.*;
import static com.bdd.utils.WebDriverUtils.*;
import static com.bdd.variables.GlobalVariables.*;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;


public class SeleniumSupportFunctions {
	private static int maxLoadTime = 50;
	private static final int BUFFER_SIZE = 4096;

	public static boolean waitForElementToLoad(WebElement webElement, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public static WebDriver waitForPageToLoad() {
		try {
			new WebDriverWait(driver, Integer.parseInt(getPropertyValue("timeout")))
					.until(new Function<WebDriver, Object>() {
						public Object apply(WebDriver webdriver) {
							return ((JavascriptExecutor) webdriver).executeScript("return document.readyState")
									.equals("complete");
						}
					});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return driver;
	}

	public static boolean waitForElement(WebElement webElement, int timeout) {
		// Assert.assertTrue(waitForElementToLoad(webElement, driver, timeout),
		// "failed to load: " + webElement);
		Assert.assertTrue(waitForElementToLoad(webElement, timeout));
		return true;
	}

	public static boolean waitForElementToLoad(WebElement webElement) {
		return waitForElementToLoad(webElement, maxLoadTime);
	}

	public static boolean waitForElementClickable(WebElement webElement, int timeToWait) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, timeToWait);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public static boolean waitForElementClickablebyXpath(String xpath, int timeToWait) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, timeToWait);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xpath))));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public static boolean isListSortedInAscendingOrder(List<String> list) {

		boolean isInAscendingOrder = false;
		String prev = list.get(0);
		String current = list.get(1);
		for (int i = 0; i < list.size(); i++) {

			if (prev.compareToIgnoreCase(current) <= 0) {
				prev = current;
				if (i != list.size() - 1) {
					current = list.get(i + 1);
				}

				isInAscendingOrder = true;
				continue;
			} else {
				isInAscendingOrder = false;
				break;
			}

		}

		return isInAscendingOrder;

	}

	public static boolean isListSortedInDescendingOrder(List<String> list) {

		boolean isInDescendingOrder = false;

		String prev = list.get(0);
		String current = list.get(1);
		for (int i = 0; i < list.size(); i++) {

			if (prev.compareToIgnoreCase(current) >= 0) {
				prev = current;
				if (i != list.size() - 1) {
					current = list.get(i + 1);
				}

				isInDescendingOrder = true;
				continue;
			} else {
				isInDescendingOrder = false;
				break;
			}

		}

		return isInDescendingOrder;

	}

	public static boolean isElementPresent(WebElement webElement) {
		try {
			webElement.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementPresent(WebElement webElement, int timeOut) {
		try {
			webElement.getText();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isElementNotPresent(WebElement webElement) {
		try {
			webElement.isDisplayed();
			throw new RuntimeException();
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	public static String getAlertText() throws InterruptedException {
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		return alert.getText();

	}

	public static void clickAlert() throws InterruptedException {
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static void alertDismiss() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public static void mouseOver(WebElement element) {
		JavascriptExecutor js = null;
		if (driver instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) driver;

			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			js.executeScript(mouseOverScript, element);
		} else {
			Log.error("Not able to do mouse over action");
		}

	}

	public static void doubleClick(WebElement element) {
		JavascriptExecutor js = null;
		if (driver instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) driver;

			String doubleClick = "if(document.createEvent){var evtObj = document.createEvent('MouseEvents');evtObj.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null); arguments[0].dispatchEvent(evtObj);}";
			js.executeScript(doubleClick, element);
		} else {
			Log.error("Not able to do double click action");
		}

	}

	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	public static ArrayList<String> getFileNames(String folderpath, ArrayList<String> filenames) {
		File foldername = new File(folderpath);
		if (foldername.exists()) {
			File[] files = foldername.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					getFileNames(files[i].getAbsolutePath(), filenames);
				} else {
					filenames.add(files[i].getName());
				}
			}
		}

		return (filenames);

	}

	public static boolean isStringContainsAllExpectedValues(String rbody, String[] expectedArray) {
		boolean flag = false;

		for (int i = 0; i < expectedArray.length; i++) {

			if (rbody.contains(expectedArray[i])) {
				flag = true;
				continue;
			} else {
				flag = false;
				break;
			}

		}
		return flag;
	}

	public static ArrayList<String> getListOfFolderNames(String folderpath, ArrayList<String> foldernames) {
		File foldername = new File(folderpath);

		if (foldername.exists()) {
			File[] files = foldername.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					foldernames.add(files[i].getName());
				} else {
					continue;
				}
			}
		}

		return (foldernames);

	}

	public static void dragAndDrop(WebElement source) {
		JavascriptExecutor js = null;

		if (driver instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) driver;

			js.executeScript(
					"function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; "
							+ "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
					source, 500, 500);
		} else {
			Log.error("Not able to do drag and drop");
		}
	}

	private static WebElement waitForCondition(int secondsToWait, By condition) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(condition));
		return webElement;
	}

	public static boolean waitForButtonToClick(int secondsToWait, By condition, String message) {
		boolean buttonFoundAndClicked = false;
		try {
			WebElement button = waitForCondition(secondsToWait, condition);
			if (button != null) {
				button.click();
				if (message != null) {
					buttonFoundAndClicked = true;
				}
			}
		} catch (Throwable t) {

			Log.debug("Proceeding after failing condition: " + condition);

		}
		if (!buttonFoundAndClicked) {
			Log.info(message);
		}
		return buttonFoundAndClicked;
	}

	public static void waitForText(String regExText, int secondsToWait) {
		Pattern p = Pattern.compile(regExText);
		Calendar future = Calendar.getInstance();
		future.add(Calendar.SECOND, secondsToWait);
		while (true) {
			Calendar now = Calendar.getInstance();
			if (now.getTime().getTime() < future.getTime().getTime()) {
				Matcher m = p.matcher(driver.getPageSource());
				if (m.find()) {
					return;
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// ignore
					}
				}
			} else {
				throw new RuntimeException("text: \"" + regExText + "\" was not found");
			}
		}
	}

	public static void submitForm(int secondsToWait) {
		try {
			waitForCondition(secondsToWait, By.tagName("form")).submit();
		} catch (Throwable e) {
			Log.warn("Could not find form", e);
		}
	}

	

	public static void takeScreenshot(String savePath) {
		try {
			if (driver != null) {
				if (driver instanceof TakesScreenshot) {
					File saveDirectory = new File(savePath).getParentFile();
					if (!saveDirectory.isDirectory() && !saveDirectory.mkdirs()) {
						Assert.assertTrue(saveDirectory.mkdirs(),
								"Cannot use directory: " + saveDirectory.getAbsolutePath());
					}
					File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					File saveFile = new File(savePath);
					if (saveFile.exists()) {
						saveFile.delete();
					}
					FileUtils.moveFile(screenShot, new File(savePath));
					Log.info("Screen shot saved to: " + screenShot.getAbsolutePath());
				} else {
					Log.info("Driver: " + driver + " does not support taking screenshots");
					;
				}
			}
		} catch (Throwable e) {
			Log.error(e);
		}
	}

	public static String readFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fin = new FileInputStream(filePath);
			InputStreamReader in = new InputStreamReader(fin);
			BufferedReader bufferedReader = new BufferedReader(in);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			bufferedReader.close();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return sb.toString();
	}

	public static int generateRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((3 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	public static boolean switchToWindow(String title) {

		String parentWindowhandler = null;
		try {
			parentWindowhandler = driver.getWindowHandle();
		} catch (NoSuchWindowException e) {
			Log.error("There is no window available to switch");
		}
		boolean childWindowFlag = false;
		for (String Child_Window : driver.getWindowHandles()) {
			driver.switchTo().window(Child_Window);
			if (driver.getTitle().toString().isEmpty()) {
				childWindowFlag = true;
				break;
			}
			if (driver.getTitle().contains(title)) {
				childWindowFlag = true;
				break;
			}
		}
		if (!childWindowFlag) {
			driver.switchTo().window(parentWindowhandler);
			Log.error("The window having title " + title + " is not available");
		}
		return childWindowFlag;
	}

	public static String getDownloadDirectoryPath() {
		String downloadDir = "/Downloads";
		if (downloadDir == null) {
			downloadDir = "target" + File.separator + "downloads" + File.separator;
		}
		File tmpDir = new File(downloadDir);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		return downloadDir;
	}

	public static String createFileWithContent(String filePath, String content) {
		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			out.write(content);
			out.close();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		return filePath;
	}

	public static boolean isTextPresentOnPage(String text, int waitSeconds) {
		try {
			waitForText(text, waitSeconds);
			return driver.findElement(By.xpath("//*[text()='" + text + "']")).isDisplayed();
		} catch (Throwable t) {
			return false;
		}
	}

	public static void refresh() {
		driver.navigate().refresh();
	}

	public static void waitAndClick(WebElement element) {
		waitAndClick(element, maxLoadTime);
	}

	public static void waitAndClick(WebElement element, int timeToWait) {
		try {
			waitForElementClickable(element, timeToWait);
			element.click();
		} catch (Exception e) {
			Log.error("Unable to click " + element);
			throw new RuntimeException(e);
		}
	}

	public static void verifySentValue(WebElement element, String value) {
		String actual = element.getAttribute("value");
		if (actual.equals(value)) {
			Log.debug(value + " Value sent in WebElement: " + element);

		} else {
			Log.error("Unable to send value: " + value + " in WebElement: " + element);
			throw new RuntimeException("Unable to send value: " + value + " in WebElement: " + element);
		}
	}

	public static void screenCapture(String fileName) {

		File snapshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = fileName;

		try {
			FileUtils.copyFile(snapshot, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void click(WebElement element) {
		waitForElementClickable(element, 30);
		element.click();
	}

	public static void clickByXpath(String xpath) {
		waitForElementClickablebyXpath(xpath, 30);
		driver.findElement(By.xpath(xpath)).click();
		waitForPageToLoad();
	}

}
