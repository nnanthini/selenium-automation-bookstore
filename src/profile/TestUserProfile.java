package profile;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.Util;

public class TestUserProfile {
	WebDriver driver = null;
	String actual, expected;
	
	
  @Test
  @Parameters ({"browser"})
  public void initialisePage(String browser) {
	  if(browser.equals("firefox")) {
		  System.setProperty("webdriver.gecko.driver", Util.FIREFOX_PATH);
		  driver = new FirefoxDriver();
		  Reporter.log("Firefox Browser");
	  } else {
		  System.setProperty("webdriver.chrome.driver", Util.CHROME_PATH);
		  driver = new ChromeDriver();
		  Reporter.log("Chrome Browser");
	  }	  
	  
	  String url = Util.URL;
	  
	  driver.get(url);
	  Reporter.log("Page URL is "+url);
	  driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
	  
	  driver.findElement(By.id("userName")).sendKeys(Util.USER_NAME);
	  driver.findElement(By.id("password")).sendKeys(Util.USER_PASSWORD);
	  driver.findElement(By.id("login")).click();
	  Reporter.log("User login done");
	  
	  WebDriverWait wait = new WebDriverWait(driver, 10);
	  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("searchBox"))));
	  
	  actual = driver.findElement(By.id("userName-value")).getText();
	  expected = Util.USER_NAME;
	  Assert.assertEquals(actual, expected, "Mismatch in logged in user name");
  }
  
  @Test (dependsOnMethods = "initialisePage")
  public void validateHeader() {
	  Reporter.log("Validate main heading of page", 1);
	  expected = "Profile";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Page heading mismatch");
	  
  }
  
  @Test (dependsOnMethods = "validateHeader")
  public void validateProfilePageButtons() throws InterruptedException {
	  
	  SoftAssert sAssert = new SoftAssert();
	  Actions actions = new Actions(driver);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  
	  Reporter.log("Validate buttons", 0);
	  
	  List<WebElement> submitButtons = driver.findElements(By.id("submit"));
	  Iterator<WebElement> iterator = submitButtons.iterator();
	  while (iterator.hasNext()) {
		  WebElement button = iterator.next();
		  if (button.isDisplayed()) {
			  Reporter.log("Current button is "+button.getText());
			  sAssert.assertFalse(button.isSelected(), "Button should not be selected");
			  
			  js.executeScript("arguments[0].scrollIntoView()", button);
			  
			  Color buttonOriginal, buttonFinal;	  
			  buttonOriginal = Color.fromString(button.getCssValue("background-color"));
		      
			  actions.moveToElement(button).perform();
			  Thread.sleep(1000);
		      
			  buttonFinal = Color.fromString(button.getCssValue("background-color"));
			  sAssert.assertNotEquals(buttonOriginal, buttonFinal, "Background color should change on cursor hover");
		  }
	  }
	  
	  WebElement gotoStoreButton = driver.findElement(By.id("gotoStore"));
	  Reporter.log("Current button is "+gotoStoreButton.getText());
	  sAssert.assertTrue(gotoStoreButton.isDisplayed(), "Goto Store button should be displayed");
	  sAssert.assertTrue(gotoStoreButton.isEnabled(), "Goto Store button should be enabled");
	  sAssert.assertFalse(gotoStoreButton.isSelected(), "Goto Store button should not be selected");
	  Color gotoStoreOriginal, gotoStoreFinal;	  
	  gotoStoreOriginal = Color.fromString(gotoStoreButton.getCssValue("background-color"));
	  
	  actions.moveToElement(gotoStoreButton).perform();
	  Thread.sleep(2000);
      gotoStoreFinal = Color.fromString(gotoStoreButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(gotoStoreOriginal, gotoStoreFinal, "Background color should change on cursor hover");
	  
	  sAssert.assertAll();
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
  
}
