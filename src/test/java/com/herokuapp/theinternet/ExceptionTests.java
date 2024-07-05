package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionTests {
	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {

		switch (browser) {
		case "chrome":
			// create driver
			// System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "firefox":
			// create driver
			System.setProperty("webDriver.gecko.driver", "geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("do not know how to start " + browser + " starting chrome instead");
			// System.setProperty("webDriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();
	}

	// https://practicetestautomation.com/practice-test-exceptions/
	@Test
	public void noSuchElementExceptionTests() {
		// Test case 1: NoSuchElementException
		System.out.println("Test Started");
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		driver.findElement(By.xpath("//button[@id='add_btn']")).click();

		// Implicit Wait - it will search the element till 10 seconds if it finds before
		// the time it will continue.
		// this line should be in setup method. this wait will applicable for all
		// loading element
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Explicit Wait
		// https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// visibilityOfElementLocated : method return the element once it visible
		WebElement inputRow2 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']//input[@type='text']")));

		Assert.assertTrue(inputRow2.isDisplayed(), "row2 is not visible");

	}

	@Test
	public void elementNotInteractableExceptionTest() {

		// Test case 2: ElementNotInteractableException

		// Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");

		// Click Add button
		driver.findElement(By.xpath("//button[@id='add_btn']")).click();

		// Wait for the second row to load
		// Explicit Wait
		// https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// visibilityOfElementLocated : method return the element once it visible
		WebElement inputRow2 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']//input[@type='text']")));
		Assert.assertTrue(inputRow2.isDisplayed(), "row2 is not visible");

		// Type text into the second input field
		inputRow2.sendKeys("Burger");

		// Push Save button using locator By.name(“Save”)
		// driver.findElement(By.name("Save")).click(); - through
		// ElementNotInteractableException
		driver.findElement(By.xpath("//div[@id='row2']//button[@id='save_btn']"));

		// Verify text saved
		WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
		String expectedText = "Row 2 was added";
		String actualText = confirmationMessage.getText();
		Assert.assertEquals(actualText, expectedText, "expected and actual test doesn't match");

	}

	@Test
	public void invalidElementStateExceptionTest() {
		// Test case 3: InvalidElementStateException

		// Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");

		// Clear input field
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement inputRow1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']//input[@type='text']")));
		driver.findElement(By.xpath("//div[@id='row1']//button[@id='edit_btn']")).click();

		wait.until(ExpectedConditions.elementToBeClickable(inputRow1));
		inputRow1.clear();

		// Type text into the input field
		inputRow1.sendKeys("Burger");
		driver.findElement(By.xpath("//div[@id='row1']//button[@id='save_btn']")).click();

		// Verify text changed
		String value = inputRow1.getAttribute("value");
		Assert.assertEquals(value, "Burger", "doesn't match");

		// Verify text saved
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
		String expectedText = "Row 1 was saved";
		String actualText = confirmationMessage.getText();
		Assert.assertEquals(actualText, expectedText, "expected and actual test doesn't match");

	}
	
	@Test
	public void staleElementReferenceExceptionTest() {
		
		//Test case 4: StaleElementReferenceException
		
		//Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		
		//Find the instructions text element
		//WebElement instructionMessage=driver.findElement(By.xpath("//p[@id='instructions']"));
		
		//Push add button
		driver.findElement(By.xpath("//button[@id='add_btn']")).click();
		
		//Verify instruction text element is no longer displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[@id='instructions']"))), "msg is displayed");
		///Assert.assertFalse(instructionMessage.isDisplayed(), "msg is displayed");
	}
	
	@Test
	public void timeoutException() {
		// Test case 5: TimeoutException
		//Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		
		//Click Add button
		driver.findElement(By.xpath("//button[@id='add_btn']")).click();
		
		//Wait for 3 seconds for the second input field to be displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		WebElement inputRow2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']//input[@type='text']")));

		//Verify second input field is displayed
		Assert.assertTrue(inputRow2.isDisplayed(), "row2 is not visible");

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close browser
		driver.close();
		System.out.println("Test Finished");
	}

}
