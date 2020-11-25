package bookstore;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
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
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
	  expected = "Book Store";
	  actual = driver.findElement(By.className("main-header")).getText();
	  Assert.assertEquals(actual, expected, "Title mismatch"); 
  }
  
  @Test
  public void testAllLinks() throws IOException {
	  WebElement table = driver.findElement(By.className("rt-table"));
	  List<WebElement> a = driver.findElements(By.xpath("//div[@class='rt-tr-group']/div/div[2]/div/span/a"));
	  Iterator<WebElement> loop = a.iterator();
	  
	  WebElement link;
	  while (loop.hasNext()) {
		  link = loop.next();
		  String url = link.getAttribute("href");
		  Reporter.log("Link is "+url);
		  Assert.assertFalse(url.isEmpty(), "Link URL should not be empty");
		  
		  if(!url.isEmpty()) {
		  
			  actual = VerifyURLConnection(url);
			  expected = "OK";
			  Assert.assertEquals(actual, expected, "Link is invalid");
			  
		  }
		  
	  }
  }
  
  public String VerifyURLConnection(String url) throws MalformedURLException {
	  URL urlLink = new URL(url);
	  try {
		  HttpURLConnection connection = (HttpURLConnection) urlLink.openConnection();
		  connection.connect();
		  String response = connection.getResponseMessage();
		  connection.disconnect();
		  return response;
	  }
	  catch (Exception e) {
		  return e.toString();
	  }
	  
	  
  }
  
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
}
