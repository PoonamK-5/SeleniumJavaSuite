package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {
	
	@Test
	public void loginTest() {
		
		System.out.println("Test Started");
		//create driver
		WebDriver driver = new ChromeDriver();
		System.out.println("Browser Started");
		sleep(1);
		
		//open test page
		String url="https://the-internet.herokuapp.com/login";
		driver.get(url);
		sleep(1);
		
		driver.manage().window().maximize();
		System.out.println("Page is opened");
		sleep(1);
		
		//enter username
		WebElement username=driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");
		sleep(1);
		
		//enter password
		WebElement password=driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");
		sleep(3);
		
		//click login button
		WebElement logInButton=driver.findElement(By.tagName("button"));
		logInButton.click();
		sleep(5);
		
		//Verification
		//new url
		String expectedUrl="https://the-internet.herokuapp.com/secure";
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl,"Actual page url is not the same as expected");
		
		//logout button
		WebElement logOutButton=driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "logout button is not visible");
		
		//successful login message
		WebElement successMessage=driver.findElement(By.cssSelector("#flash"));
		String expectedMessage="You logged into a secure area!";
		String actualMessage=successMessage.getText();	
		//Assert.assertEquals(actualMessage, expectedMessage,"Actual message is not the same as expected");
		Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message dosen't contains expected message.\nActual Message: "+actualMessage+"\nExpected Message: "+expectedMessage);
		
		driver.close();
		System.out.println("Test Finished");
		
		
		//section 2-4 mevan installation
	}

	/**
	 * stop execution for the given amount of time
	 * @param seconds
	 */
	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//to run in cmd
	//go to prject path open cmd and use one of the below cmd
	// mvn clean test 
	// mvn -Dtest=PositiveTests clean test

}
