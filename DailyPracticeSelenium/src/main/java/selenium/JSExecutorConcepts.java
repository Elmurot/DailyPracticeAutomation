package selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class JSExecutorConcepts {

	static WebDriver driver;
	static JavascriptExecutor js;

	public static void main(String[] args) throws IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("https://www.pnc.com");

		WebElement pncLogo = driver.findElement(By.xpath("//img[@alt='PNC']"));
		
		flashObject(pncLogo, driver); // highlight WebElement
		drawBorder(pncLogo, driver); // draw a border around the WebElement
		takeScreenshot("pncLogo", ".jpg");
		driver.quit();

	}
//	/*
//	 ************** flash Objects method is used to flash Webelement **********************
	 
	public static void flashObject(WebElement element, WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor(element, "rgb(0,200,0)", driver);// 1
			changeColor(element, bgcolor, driver);// 2
		}
	}

	public static void changeColor(WebElement element, String color, WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
	}
	
//	 **************************************************************************************
//	 */

	public static void drawBorder(WebElement element, WebDriver driver) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border = '3px solid red'", element);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}
	
	public static void takeScreenshot(String fileName,String extension) throws IOException {
		
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File("/Users/elmurotyangiboev/git/CucumberBDDFramework/CucumberBDDFramework/src/Screenshots/" + fileName+" "+timestamp+extension));
		
	}
}
