package login;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Util;

public class LoginUser {
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
  
  @Test (dependsOnMethods = {"initialisePage"})
  public void clickLogin() {
	  
	  driver.findElement(By.id("userName")).sendKeys(Util.USER_NAME);
	  driver.findElement(By.id("password")).sendKeys(Util.USER_PASSWORD);
	  driver.findElement(By.id("login")).click();
	  
	  WebDriverWait wait = new WebDriverWait(driver,10);
	  wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit")));
	  
	  actual = driver.findElement(By.className("main-header")).getText();
	  expected = "Profile";
	  Assert.assertEquals(actual, expected, "Page header mismatch");
  }
  
  @Test (dependsOnMethods = "clickLogin") 
  public void clickLogout() {
	  WebElement logoutButton;
	  logoutButton = driver.findElement(By.id("submit"));
	  
	  actual = logoutButton.getText();
	  expected = "Log out";
	  Assert.assertEquals(actual, expected, "Mismatch in logout button text");
	  
	  logoutButton.click();
	  WebDriverWait wait = new WebDriverWait(driver,10);
	  wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newUser")));
	  
	  actual = driver.findElement(By.className("main-header")).getText();
	  expected = "Login";
	  Assert.assertEquals(actual, expected, "Page header mismatch");
  
  }
  
  @AfterTest
  public void afterTest() {	  
	  driver.quit();
  }
}
