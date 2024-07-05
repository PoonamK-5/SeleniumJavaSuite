package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
	
	@Parameters({ "username", "password", "expectedMessage" })	
	@Test(priority=1,groups = { "negativeTests", "smokeTests" })
	public void negativeUsernameTest(String uname, String pwd, String msg) {
		
		System.out.println("Sarting negative test with "+ uname+ " and " +pwd);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://the-internet.herokuapp.com/login");
		
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.name("password")).sendKeys(pwd);
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	
		sleep(3); //refactor generate extract method
		
		String actualMessage=driver.findElement(By.xpath("//div[@id='flash']")).getText();
		Assert.assertTrue(actualMessage.contains(msg),"Actual message doesn't contains expected message");
		
		driver.close();
		System.out.println("Test Finished");
		
	}
	
	//if don't mention priority test will be executed in alphabetical order
	//https://testng.org/#_testng_documentation
	
	/*
	 * @Test(priority=1,groups = { "negativeTests", "smokeTests" }) public void
	 * negativeUsernameTest() {
	 * 
	 * WebDriver driver = new ChromeDriver(); driver.manage().window().maximize();
	 * 
	 * driver.get("https://the-internet.herokuapp.com/login");
	 * 
	 * driver.findElement(By.id("username")).sendKeys("incorrect");
	 * driver.findElement(By.name("password")).sendKeys("SuperSecretPassword!");
	 * 
	 * driver.findElement(By.xpath("//button[@type='submit']")).click();
	 * 
	 * sleep(3); //refactor generate extract method
	 * 
	 * String
	 * actualMessage=driver.findElement(By.xpath("//div[@id='flash']")).getText();
	 * String expectedMessage="Your username is invalid!";
	 * Assert.assertTrue(actualMessage.contains(expectedMessage)
	 * ,"Actual message doesn't contains expected message");
	 * 
	 * driver.close();
	 * 
	 * }
	 */
	
	/*
	 * @Test(priority=2,groups = { "negativeTests" }) //,enabled=false to disable
	 * the method public void negativePasswordTest() {
	 * 
	 * WebDriver driver = new ChromeDriver(); driver.manage().window().maximize();
	 * 
	 * driver.get("https://the-internet.herokuapp.com/login");
	 * 
	 * driver.findElement(By.id("username")).sendKeys("tomsmith");
	 * driver.findElement(By.name("password")).sendKeys("Superword!");
	 * 
	 * driver.findElement(By.xpath("//button[@type='submit']")).click();
	 * 
	 * sleep(3); //refactor generate extract method
	 * 
	 * String
	 * actualMessage=driver.findElement(By.xpath("//div[@id='flash']")).getText();
	 * String expectedMessage="Your password is invalid!";
	 * Assert.assertTrue(actualMessage.contains(expectedMessage)
	 * ,"Actual message doesn't contains expected message");
	 * 
	 * driver.close();
	 * 
	 * }
	 */

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
