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
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.Util;

public class ValidateLoginForm {
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
 
  }
  
  @Test
  public void validateHeader() {
	  Reporter.log("Validate main heading of page", 1);
	  expected = "Login";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Page heading mismatch");
	  
  }
  
  @Test
  public void validateHeading() {
	  Reporter.log("Validate login heading string", 1, true);
	  expected = "Welcome,Login in Book Store";
	  actual = driver.findElement(By.tagName("h2")).getText() + driver.findElement(By.tagName("h5")).getText();
	  Assert.assertEquals(actual, expected, "Login heading mismatch");
	  
  }
  
  @Test
  public void validateUserNameAndPasswordFields() throws InterruptedException {
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
	  
	  Color userOriginal, userFinal, passwordOriginal, passwordFinal;
	  userOriginal = Color.fromString(userInput.getCssValue("border-top-color"));	  
	  userInput.click();
	  Thread.sleep(1000);
	  userFinal = Color.fromString(userInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(userOriginal, userFinal, "Border color should change on click");
	  
	  
	  passwordOriginal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  passwordInput.click();
	  Thread.sleep(1000);
	  passwordFinal = Color.fromString(passwordInput.getCssValue("border-top-color"));	  
	  sAssert.assertNotEquals(passwordOriginal, passwordFinal, "Border color should change on click");
	  
	  sAssert.assertAll();
	  
  }
  
  @Test
  public void validateLoginPageButtons() throws InterruptedException {
	  WebElement loginButton, newUserButton;
	  SoftAssert sAssert = new SoftAssert();
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  loginButton = driver.findElement(By.id("login"));
	  newUserButton = driver.findElement(By.id("newUser"));
	  
	  sAssert.assertTrue(loginButton.isDisplayed(), "Login button should be displayed");
	  sAssert.assertTrue(loginButton.isEnabled(), "Login button should be enabled");
	  sAssert.assertFalse(loginButton.isSelected(), "Login button should not be selected");
	  
	  sAssert.assertTrue(newUserButton.isDisplayed(), "New user button should be displayed");
	  sAssert.assertTrue(newUserButton.isEnabled(), "New user button should be enabled");
	  sAssert.assertFalse(newUserButton.isSelected(), "New user button should not be selected");
	  
	  Color loginOriginal, loginFinal, newUserOriginal, newUserFinal;
	  
	  loginOriginal = Color.fromString(loginButton.getCssValue("background-color"));
      Actions actions = new Actions(driver);
      js.executeScript("arguments[0].scrollIntoView()", loginButton);
	  actions.moveToElement(loginButton).perform();
	  Thread.sleep(1000);
      loginFinal = Color.fromString(loginButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(loginOriginal, loginFinal, "Background color should change on cursor hover");
	  
	  newUserOriginal = Color.fromString(newUserButton.getCssValue("background-color"));
	  actions.moveToElement(newUserButton).perform();
      js.executeScript("arguments[0].scrollIntoView()", newUserButton);
	  Thread.sleep(1000);
      newUserFinal = Color.fromString(newUserButton.getCssValue("background-color"));
	  sAssert.assertNotEquals(newUserOriginal, newUserFinal, "Background color should change on cursor hover");
		  
	  sAssert.assertAll();
  }
  
  @DataProvider (name = "name-provider")
  public Object[][] nameProvider() {
	  return new Object[][] {{"abcxyz"},{"123"},{""},{null},{"A123abc"},{"aAz$"},{" iop8"},{"#123abc"},{"AAaa12$#"}};
  }
  
  @Test (dataProvider = "name-provider")
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
