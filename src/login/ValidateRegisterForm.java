package login;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.Util;

public class ValidateRegisterForm {
	WebDriver driver = null;
	String actual, expected;
	
	
  @Test
  @Parameters ({"browser"})
  public void initialisePage(String browser) {
	  if(browser.equals("firefox")) {
		  System.setProperty("webdriver.gecko.driver", Util.FIREFOX_PATH);
		  driver = new FirefoxDriver();
	  } else {
		  System.setProperty("webdriver.chrome.driver", Util.CHROME_PATH);
		  driver = new ChromeDriver();
	  }	  
	  
	  String url = Util.URL;
	  
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
	 
	  Color firstNameOriginal, firstNameFinal;
	  firstNameOriginal = Color.fromString(firstNameInput.getCssValue("border-top-color"));	  
	  firstNameInput.click();
	  Thread.sleep(1000);
	  firstNameFinal = Color.fromString(firstNameInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(firstNameOriginal, firstNameFinal, "Border color should change on click");
	  
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateLastNameFields() throws InterruptedException {
	  WebElement lastNameInput;
	  SoftAssert sAssert = new SoftAssert();
	  expected = "Last Name :";
	  actual = driver.findElement(By.id("lastname-label")).getText();
	  sAssert.assertEquals(actual, expected, "Last name label mismatch");
   
	  lastNameInput = driver.findElement(By.id("lastname"));
	  expected = "Last Name";
	  actual = lastNameInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "Last name placeholder mismatch");
	 
	  Color lastNameOriginal, lastNameFinal;
	  lastNameOriginal = Color.fromString(lastNameInput.getCssValue("border-top-color"));	  
	  lastNameInput.click();
	  Thread.sleep(1000);
	  lastNameFinal = Color.fromString(lastNameInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(lastNameOriginal, lastNameFinal, "Border color should change on click");
	  
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateUserNameFields() throws InterruptedException {
	  WebElement userNameInput;
	  SoftAssert sAssert = new SoftAssert();
	  expected = "UserName :";
	  actual = driver.findElement(By.id("userName-label")).getText();
	  sAssert.assertEquals(actual, expected, "User name label mismatch");
   
	  userNameInput = driver.findElement(By.id("userName"));
	  expected = "UserName";
	  actual = userNameInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "User name placeholder mismatch");
	 
	  Color userNameOriginal, userNameFinal;
	  userNameOriginal = Color.fromString(userNameInput.getCssValue("border-top-color"));	  
	  userNameInput.click();
	  Thread.sleep(1000);
	  userNameFinal = Color.fromString(userNameInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(userNameOriginal, userNameFinal, "Border color should change on click");
	  
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validatePasswordFields() throws InterruptedException {
	  WebElement passwordInput;
	  SoftAssert sAssert = new SoftAssert();
	  	  
	  expected = "Password :";
	  actual = driver.findElement(By.id("password-label")).getText();
	  sAssert.assertEquals(actual, expected, "Password label mismatch");
	  
	  passwordInput = driver.findElement(By.id("password"));
	  expected = "Password";
	  actual = passwordInput.getAttribute("placeholder");
	  sAssert.assertEquals(actual, expected, "Password placeholder mismatch");
	  
	  Color passwordOriginal, passwordFinal;	  
	  
	  passwordOriginal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  passwordInput.click();
	  Thread.sleep(1000);
	  passwordFinal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(passwordOriginal, passwordFinal, "Border color should change on click");
	 
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateLoginPageButtons() throws InterruptedException {
	  WebElement goToLoginButton, registerButton;
	  SoftAssert sAssert = new SoftAssert();
	  JavascriptExecutor js = (JavascriptExecutor) driver;
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
      js.executeScript("arguments[0].scrollIntoView()", goToLoginButton);
	  actions.moveToElement(goToLoginButton).perform();
	  Thread.sleep(1000);
      loginFinal = Color.fromString(goToLoginButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(loginOriginal, loginFinal, "Background color should change on cursor hover");
	  
	  registerOriginal = Color.fromString(registerButton.getCssValue("background-color"));
	  actions.moveToElement(registerButton).perform();
      js.executeScript("arguments[0].scrollIntoView()", registerButton);
	  Thread.sleep(1000);
      registerFinal = Color.fromString(registerButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(registerOriginal, registerFinal, "Background color should change on cursor hover");
		  
	  sAssert.assertAll();
  }
  
  @Test (dataProvider = "name-provider", dataProviderClass = util.DataProviderClass.class)
  public void testUserNameCombos(String name) {
	  SoftAssert sAssert = new SoftAssert();
	  if(name != null) {
		  sAssert.assertFalse(name.isEmpty(), "Name "+name+" cant be empty");		  
		  sAssert.assertTrue(Pattern.matches("^[A-Za-z].*",name), "Name "+name+" should start with alphabets");
	  }
	  sAssert.assertAll();
  }
  
  
  @AfterTest
  public void afterTest() {	  
	  driver.quit();
  }
}
