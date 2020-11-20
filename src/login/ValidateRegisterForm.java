package login;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.Util;

public class ValidateRegisterForm {
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
	  
	  driver.findElement(By.id("newUser")).click();
  }
  
  @Test
  public void validateHeader() {
	  expected = "Register";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Page heading mismatch");
	  
  }
  
  @Test
  public void validateHeading() {
	  expected = "Register to Book Store";
	  actual = driver.findElement(By.tagName("h4")).getText();
	  Assert.assertEquals(actual, expected, "Login heading mismatch");
	  
  }
  
  @Test
  public void validateFirstNameFields() throws InterruptedException {
	  WebElement firstNameInput;
	  SoftAssert sAssert = new SoftAssert();
	  expected = "First Name :";
	  actual = driver.findElement(By.id("firstname-label")).getText();
	  sAssert.assertEquals(actual, expected, "First name label mismatch");
	  
	  
	  firstNameInput = driver.findElement(By.id("firstname"));
	  expected = "First Name";
	  actual = firstNameInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "First name placeholder mismatch");
	  
	  /*
	  expected = "Password :";
	  actual = driver.findElement(By.id("password-label")).getText();
	  sAssert.assertEquals(actual, expected, "Password label mismatch");
	  
	  passwordInput = driver.findElement(By.id("password"));
	  expected = "Password";
	  actual = passwordInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "Password placeholder mismatch");
	  */
	  Color firstNameOriginal, firstNameFinal;
	  firstNameOriginal = Color.fromString(firstNameInput.getCssValue("border-top-color"));	  
	  firstNameInput.click();
	  Thread.sleep(2000);
	  firstNameFinal = Color.fromString(firstNameInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(firstNameOriginal, firstNameFinal, "Border color should change on click");
	  
	  /*
	  passwordOriginal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  passwordInput.click();
	  Thread.sleep(2000);
	  passwordFinal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(passwordOriginal, passwordFinal, "Border color should change on click");
	  */
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateLoginPageButtons() throws InterruptedException {
	  WebElement goToLoginButton, registerButton;
	  SoftAssert sAssert = new SoftAssert();
	  goToLoginButton = driver.findElement(By.id("gotologin"));
	  registerButton = driver.findElement(By.id("register"));
	  
	  sAssert.assertTrue(goToLoginButton.isDisplayed(), "Login button should be displayed");
	  sAssert.assertTrue(goToLoginButton.isEnabled(), "Login button should be enabled");
	  sAssert.assertFalse(goToLoginButton.isSelected(), "Login button should not be selected");
	  
	  sAssert.assertTrue(registerButton.isDisplayed(), "New user button should be displayed");
	  sAssert.assertTrue(registerButton.isEnabled(), "New user button should be enabled");
	  sAssert.assertFalse(registerButton.isSelected(), "New user button should not be selected");
	  
	  Color loginOriginal, loginFinal, registerOriginal, registerFinal;
	  
	  loginOriginal = Color.fromString(goToLoginButton.getCssValue("background-color"));
      Actions actions = new Actions(driver);
	  actions.moveToElement(goToLoginButton).perform();
	  Thread.sleep(2000);
      loginFinal = Color.fromString(goToLoginButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(loginOriginal, loginFinal, "Background color should change on cursor hover");
	  
	  registerOriginal = Color.fromString(registerButton.getCssValue("background-color"));
	  actions.moveToElement(registerButton).perform();
	  Thread.sleep(2000);
      registerFinal = Color.fromString(registerButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(registerOriginal, registerFinal, "Background color should change on cursor hover");
		  
	  sAssert.assertAll();
  }
  
  
  @AfterTest
  public void afterTest() {	  
	  driver.quit();
  }
}
