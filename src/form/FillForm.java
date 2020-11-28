package form;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FillForm {
	WebDriver driver = null;
	String actual, expected;
	
  @Test
  public void testFileUpload() throws AWTException, InterruptedException {
	  WebElement fileUploadButton = driver.findElement(By.id("uploadPicture"));
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("arguments[0].scrollIntoView()",	fileUploadButton);
	  Actions actions = new Actions(driver);
	  actions.moveToElement(fileUploadButton).click().perform();
	  Thread.sleep(2000);
	  
	  Robot robot = new Robot();
	  robot.keyPress(KeyEvent.VK_A);
	  robot.keyPress(KeyEvent.VK_B);
	  robot.keyPress(KeyEvent.VK_1);
	  robot.keyPress(KeyEvent.VK_PERIOD);
	  robot.keyPress(KeyEvent.VK_B);
	  robot.keyPress(KeyEvent.VK_M); 
	  robot.keyPress(KeyEvent.VK_P);
	  robot.keyPress(KeyEvent.VK_ENTER);
	  Thread.sleep(2000);
	  
	  driver.quit();
  }
  
  @Test
  public void initialiseForm() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\ezhil\\Desktop\\FrontEndNanodegree\\0_Selenium_Tutorial\\Selenium Downloads\\chromedriver_win32\\chromedriver.exe");
	  driver = new ChromeDriver();
	  
	  driver.get("https://demoqa.com/automation-practice-form");
	  driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  actual = driver.getTitle();
	  expected = "ToolsQA";
	  Assert.assertEquals(actual, expected, "Title mismatch");
  }
}
