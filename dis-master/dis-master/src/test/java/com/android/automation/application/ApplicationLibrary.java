package com.android.automation.application;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ApplicationLibrary {

	private static final Logger LOGGER = Logger.getLogger(ApplicationLibrary.class.getName());
	private AppiumDriver<MobileElement> driver;

	public ApplicationLibrary(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public WebElement verifyElement(By locator, String sRepoText) {
		WebElement element = null;
		try {
			List<MobileElement> elements = driver.findElements(locator);
			Boolean eleExist = elements.size() > 0 ? true : false;
			if (eleExist == true) {
				element = elements.get(0);
				if (!element.isEnabled()) {
					System.out.println(sRepoText + " is not enabled");
				}
				if (!element.isDisplayed()) {
					System.out.println(sRepoText + " is not displayed");
				}
			} else {
				System.out.println(sRepoText + " is not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception in verifyElement()" + e);
			System.out.println("Element is not present");
		}
		return element;
	}

	public boolean verifyElementExist(By byVal) {
		boolean isExist = false;
		try {
			if (driver.findElements(byVal).size() != 0) {
				isExist = true;
			}
		} catch (Exception e) {
			LOGGER.info("Exception in verifyElementExist()" + e);
			System.out.println("Element is not present");
		}
		return isExist;
	}

	public void delay(int iSecs) {
		try {
			if (iSecs == 0) {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
			Thread.sleep(iSecs);
		} catch (Exception e) {
			LOGGER.info("Exception in delay()" + e);
		}
	}

	public void waitForElementVisibility(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			Thread.sleep(5);
		} catch (Exception e) {
			LOGGER.info("Exception in waitForElement()" + e);
		}
	}
}