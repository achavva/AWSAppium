package com.android.automation.DisneyLand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.android.automation.application.ApplicationLibrary;

public class TS_VerifyHomeScreen {
	private static final Logger LOGGER = Logger
			.getLogger(TS_VerifyHomeScreen.class.getName());
	private AppiumDriver<WebElement> driver;
	private AppiumDriverLocalService appiumService;
	private ApplicationLibrary appLib;

	public TS_VerifyHomeScreen() {
		appLib = new ApplicationLibrary(driver);
	}


	@Test
	public void verifyHomeScreen() {

		appLib.delay(20000);
		appLib.waitForElementVisibility(By.id("com.disney.wdpro.dlr:id/pulldown_image"));
		Dimension size = driver.manage().window().getSize();

		int x = (int) (size.width * 0.50);
		int startY = (int) (size.height * 0.05);
		int endY = (int) (size.height * 0.75);

		driver.swipe(x, startY, x, endY, 2000);

		appLib.delay(1000);

		WebElement avatar = driver.findElement(By.id("com.disney.wdpro.dlr:id/img_avatar"));
		avatar.click();

		String homeScreenTitle = driver.findElement(By.id("section_text_title")).getText();

		// Verify 'Park Info & Entry' screen is displayed
		Assert.assertEquals("Park Info & Entry", homeScreenTitle);

		List<WebElement> parkInfoSection = driver.findElements(By.id("text_view_cta_button"));
		WebElement todaysShowtimes = parkInfoSection.get(0);
		WebElement parkHours = parkInfoSection.get(1);
		WebElement myTickets = parkInfoSection.get(2);
		WebElement BuyTickets = parkInfoSection.get(3);



		/**
		 * Navigate to "My Tickets" screen
		 */
		myTickets.click();

		// Verify screen title
		Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Sign In to Your Account']")).isDisplayed(),"'Sign In to Your Account' screen is not displayed");

		// adding Email and Password 
		WebElement email = driver.findElement(By.id("txt_username"));
		email.click();
		email.clear();
		email.sendKeys("pharsha@test.com");

		WebElement password = driver.findElement(By.id("txt_password"));
		password.click();
		password.clear();
		password.sendKeys("test123!");

		WebElement signin = driver.findElement(By.id("com.disney.wdpro.dlr:id/btn_login"));
		signin.click();
		driver.navigate().back();


		// Navigate to back to home screen
		driver.navigate().back();
	}

	//		
	@AfterTest
	public void teardown() {
		driver.quit();
		appiumService.stop();
		CommandLine command = new CommandLine("cmd");
		command.addArgument("/c");
		command.addArgument("Taskkill /F /IM node.exe");
	}
}
