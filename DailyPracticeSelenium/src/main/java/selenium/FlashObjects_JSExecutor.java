package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlashObjects_JSExecutor {

	static WebDriver driver;
	static JavascriptExecutor js;

	public static void main(String[] args) {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("https://www.pnc.com");

		WebElement pncLogo = driver.findElement(By.xpath("//img[@alt='PNC']"));
		flashObject(pncLogo, driver);

		driver.quit();

	}

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

}
