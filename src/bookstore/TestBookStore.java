package bookstore;

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
  
  @Test (dependsOnMethods = "initialisePage")
  public void testSelectDropDown() {
	  int actualCount, expectedCount;
	  Select select = new Select(driver.findElement(By.xpath("//select[@aria-label='rows per page']")));
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("arguments[0].scrollIntoView()", select);
	  Assert.assertFalse(select.isMultiple(), "Rows per page is single select");
	  expectedCount = 6;
	  actualCount = select.getOptions().size();
	  Assert.assertEquals(actualCount, expectedCount, "Mismatch in number of options in select");
	  
	  int count = 0;
	  while (count < 6) {
		  select.selectByIndex(count);
		  ValidatePageInput();
		  ++count;
	  }
  }
  
  public void ValidatePageInput() {
	  WebElement pgNoInput = driver.findElement(By.xpath("//input[@aria-label='jump to page']"));
	  String totalPages = driver.findElement(By.xpath("//span[@class='-totalPages']")).getText();
	  Actions actions = new Actions(driver);
	  int i = 1;
	  while (i <= Integer.parseInt(totalPages)) {
		  actions.sendKeys(pgNoInput, Integer.toString(i)).sendKeys(Keys.ENTER).build().perform();
		  i++;
		  //Assert.assertTrue(i<=Integer.parseInt(totalPages), "");
	  }
	  actions.sendKeys(pgNoInput, Integer.toString(i)).sendKeys(Keys.ENTER).perform();
	  //String o = pgNoInput.getText();
	  String finalInputPgNo = pgNoInput.getAttribute("value");
	  Assert.assertFalse(finalInputPgNo.equals(i), "Higher cap should not be able to input");
	  Assert.assertEquals(finalInputPgNo, totalPages, "Input page is more than total number of pages");
	  
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
}
