package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class loginTests {
	private WebDriver driver;
	
	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun=true)
	private void setUp(@Optional("chrome") String browser) {
		
		switch (browser) {
		case "chrome":
			//create driver
		    //System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			break;
			
		case "firefox":
			//create driver
		    System.setProperty("webDriver.gecko.driver", "geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("do not know how to start "+browser + " starting chrome instead");
			//System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		
		//System.out.println("Test Started");
		
		System.out.println("Browser Started");
		sleep(1);
		driver.manage().window().maximize();
	}

	@Test(priority=1,groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {
		
		//open test page
		String url="https://the-internet.herokuapp.com/login";
		driver.get(url);
		
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
		
	}
	
	@Parameters({ "username", "password", "expectedMessage" })	
	@Test(priority=2,groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String uname, String pwd, String msg) {
		
		System.out.println("Sarting negative test with "+ uname+ " and " +pwd);
		
		driver.get("https://the-internet.herokuapp.com/login");
		
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.name("password")).sendKeys(pwd);
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	
		sleep(3); //refactor generate extract method
		
		String actualMessage=driver.findElement(By.xpath("//div[@id='flash']")).getText();
		Assert.assertTrue(actualMessage.contains(msg),"Actual message doesn't contains expected message");
		
		
		
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
	
	@AfterMethod(alwaysRun=true)
	private void tearDown() {
		//close browser
		driver.close();
		System.out.println("Test Finished");
	}
}
