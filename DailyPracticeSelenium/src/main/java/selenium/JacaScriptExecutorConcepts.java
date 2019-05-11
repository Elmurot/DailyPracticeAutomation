package selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class JacaScriptExecutorConcepts {

	static WebDriver driver;
	static JavascriptExecutor js;

	public static void main(String[] args) throws IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.pnc.com");

		WebElement pncLogo = driver.findElement(By.xpath("//img[@alt='PNC']"));
		WebElement aboutUsLink = driver.findElement(By.xpath("//span[contains(text(),'About Us')]"));

		flashObject(pncLogo, driver); // highlight WebElement
		drawBorder(pncLogo, driver); // draw a border around the WebElement
		takeScreenshot("pncLogo", ".jpg"); // take a screenshot
//		generateAlert(driver, "There is an issue with pncLogo on Home page");
		clickElementByJS(aboutUsLink, driver); // click on element by using JS executor

//		refresh the browser by selenium:
//		driver.navigate().refresh(); //  -> 1st way
		refreshBrowserByJS(driver); // this method refresh the browser -> 2nd way
		System.out.println(getTitleByJS(driver)); // get title of the page using JS
		System.out.println(getInnerTextOfThePage(driver)); // get the page text
//		scrollPageDown(driver); // scrolling down of the page 
//		WebElement joinUsLink = driver.findElement(By.xpath("//h4[contains(text(),'Join Us')]"));
//		scrollIntoView(joinUsLink, driver);
		scrollPageByPixel(0, 150, driver);

//		driver.quit();
	}

//	 ************** flashObject() method is used to flash WebElement **********************

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

//	 ************** drawBorder() method is used to draw border for WebElement ****************

	public static void drawBorder(WebElement element, WebDriver driver) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border = '3px solid red'", element);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public static void takeScreenshot(String fileName, String extension) throws IOException {

		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src,
				new File("/Users/elmurotyangiboev/git/CucumberBDDFramework/CucumberBDDFramework/src/Screenshots/"
						+ fileName + " " + timestamp + extension));
	}

	public static void generateAlert(WebDriver driver, String message) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("alert('" + message + "')");
	}

	public static void clickElementByJS(WebElement element, WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}

	public static void refreshBrowserByJS(WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("history.go(0)");
	}

	public static String getTitleByJS(WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		String title = js.executeScript("return document.title;").toString();
		return title;
	}

	public static String getInnerTextOfThePage(WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		String entireTextOfThePage = js.executeScript("return document.documentElement.innerText;").toString();
		return entireTextOfThePage;
	}

	public static void scrollPageDown(WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollIntoView(WebElement element, WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollPageByPixel(int startingPoint, int endingPoint, WebDriver driver) {
		js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(" + startingPoint + "," + endingPoint + ")");
	}
}
