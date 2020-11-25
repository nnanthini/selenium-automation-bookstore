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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
  
  @Test (enabled = false)
  public void testAllLinks() throws IOException {
	  int total = driver.findElements(By.xpath("//div[@class='rt-tr-group']/div/div[2]/div/span/a")).size();
	  int count = 0;
	  WebElement link;
	  while (count < total) {
		  link = driver.findElements(By.xpath("//div[@class='rt-tr-group']/div/div[2]/div/span/a")).get(count);
		  String url = link.getAttribute("href");
		  String title = link.getText();
		  Reporter.log("Link is "+url);
		  Assert.assertFalse(url.isEmpty(), "Link URL should not be empty");
		  
		  if(!url.isEmpty()) {
		  
			  actual = VerifyURLConnection(url);
			  expected = "OK";
			  Assert.assertEquals(actual, expected, "Link is invalid");
			  
			  if (actual.equals(expected)) {
				  try {
					testClickLink(url, title);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			  }
		  }
		  ++count;
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
  
  public void testClickLink(String url, String title) throws InterruptedException {
	  driver.navigate().to(url);
	  WebElement titleText = driver.findElement(By.xpath("//div[@id='title-wrapper']/div[2]/label"));
	  
	  actual = titleText.getText();
	  expected = title;
	  Assert.assertEquals(actual, expected, "Navigation to wrong link");
	  driver.navigate().back();
}
  
  @Test
  public void testTableHeading() throws InterruptedException {
	  Actions actions = new Actions(driver);
	  List<WebElement> tHeader = driver.findElements(By.xpath("//div[@class='rt-resizable-header-content']"));
	  Assert.assertTrue(tHeader.size() == 4, "No of column heading mismatch");
	  String[] titles = {"Image", "Title", "Author", "Publisher"};
	  int count = 0;
	  while (count < tHeader.size()) {
		  expected = tHeader.get(count).getText();
		  actual = titles[count];
		  Assert.assertEquals(actual, expected, "Title of table mismatch");
		  actions.moveToElement(tHeader.get(count)).perform();
		  actual = tHeader.get(count).getCssValue("cursor");
		  expected = "pointer";
		  Assert.assertEquals(actual, expected, "Cursor type on hover over table title mismatch");
		  ++count;
	  }
  }
  
  @Test
  public void testImgSrc() {
	  List<WebElement> imgs = driver.findElements(By.xpath("//div[@class='rt-tr-group']/div/div[1]/img"));
	  Iterator<WebElement> imgIterator = imgs.iterator();
	  Assert.assertEquals(imgs.size(), 8, "Mismatch in number of images in table");
	  WebElement img;
	  while (imgIterator.hasNext()) {
		  img = imgIterator.next();
		  String imgSrc = img.getAttribute("src");
		  
		  Assert.assertFalse(imgSrc.isEmpty(), "Image source cant be empty");
	  }
  }
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
}
