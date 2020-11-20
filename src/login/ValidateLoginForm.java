package login;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.Util;

public class ValidateLoginForm {
	WebDriver driver = null;
	String actual, expected;
	
	
  @Test 
  public void initialisePage() {
	  System.setProperty("webdriver.gecko.driver", Util.FIREFOX_PATH);
	  
	  String url = Util.URL;
	  driver = new FirefoxDriver();
	  driver.get(url);
	  driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
	  expected = "ToolsQA";
	  actual = driver.getTitle();
	  Assert.assertEquals(actual, expected, "Title mismatch");
 
  }
  
  @Test
  public void validateHeader() {
	  expected = "Login";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Page heading mismatch");
	  
  }
  
  @Test
  public void validateHeading() {
	  expected = "Welcome,Login in Book Store";
	  actual = driver.findElement(By.tagName("h2")).getText() + driver.findElement(By.tagName("h5")).getText();
	  Assert.assertEquals(actual, expected, "Login heading mismatch");
	  
  }
  
  @Test
  public void validateUserNameAndPasswordFields() {
	  WebElement userInput, passwordInput;
	  SoftAssert sAssert = new SoftAssert();
	  expected = "UserName :";
	  actual = driver.findElement(By.id("userName-label")).getText();
	  sAssert.assertEquals(actual, expected, "User name label mismatch");
	  
	  
	  userInput = driver.findElement(By.id("userName"));
	  expected = "UserName";
	  actual = userInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "User name placeholder mismatch");
	  
	  expected = "Password :";
	  actual = driver.findElement(By.id("password-label")).getText();
	  sAssert.assertEquals(actual, expected, "Password label mismatch");
	  
	  passwordInput = driver.findElement(By.id("password"));
	  expected = "Password";
	  actual = passwordInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "Password placeholder mismatch");
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateLoginPageButtons() {
	  WebElement loginButton, newUserButton;
	  SoftAssert sAssert = new SoftAssert();
	  loginButton = driver.findElement(By.id("login"));
	  newUserButton = driver.findElement(By.id("newUser"));
	  
	  sAssert.assertTrue(loginButton.isDisplayed(), "Login button should be displayed");
	  sAssert.assertTrue(loginButton.isEnabled(), "Login button should be enabled");
	  sAssert.assertFalse(loginButton.isSelected(), "Login button should not be selected");
	  
	  sAssert.assertTrue(newUserButton.isDisplayed(), "New user button should be displayed");
	  sAssert.assertTrue(newUserButton.isEnabled(), "New user button should be enabled");
	  sAssert.assertFalse(newUserButton.isSelected(), "New user button should not be selected");
	  	  
	  sAssert.assertAll();
  }
  
  @AfterTest
  public void afterTest() {	  
	  driver.quit();
  }
}
