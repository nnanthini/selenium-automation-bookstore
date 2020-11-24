package bookstore;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Util;

public class TestBookStore {
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
	  
	  String url = Util.URL_BOOKSTORE;
	  
	  driver.get(url);
	  driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
	  expected = "Book Store";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Title mismatch"); 
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
}
