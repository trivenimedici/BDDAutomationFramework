package com.bdd.pages;
import static com.bdd.impl.AppAccess.*;
import static com.bdd.utils.SeleniumSupportFunctions.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
public class Chapter1 {
	
	private static final String Link1 = "//a[text()='Chapter1']";
	@FindBy(xpath = Link1)
	private WebElement link1;

public static void accessAppUrl(String appUrl){
	accessAPP(appUrl);
}

public static void clickonLink1(String xpath) {
	clickByXpath(Link1);
}
}
