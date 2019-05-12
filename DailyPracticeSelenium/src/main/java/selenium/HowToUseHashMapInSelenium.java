package selenium;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HowToUseHashMapInSelenium {
	static WebDriver driver;

	@BeforeMethod
	private static void setUp() {
		driver = new ChromeDriver();
		driver.get("https://classic.crmpro.com");
	}

	@Test
	public static void loginWithAdminTest() throws InterruptedException {
		String adminCredentials = getUserLogInfo().get("admin");
		String adminInfo[] = adminCredentials.split("_");

		driver.findElement(By.name("username")).sendKeys(adminInfo[0]);
		driver.findElement(By.name("password")).sendKeys(adminInfo[1]);

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@type='submit']")));

		Thread.sleep(3000);

		driver.switchTo().frame("mainpanel");
		Select select = new Select(driver.findElement(By.name("slctMonth")));
		select.selectByVisibleText(monthMap().get(10));
	}

	@AfterMethod
	public static void tearDown() {
		driver.quit();
	}

	public static HashMap<String, String> getUserLogInfo() {
		HashMap<String, String> userMap = new HashMap<String, String>();
		userMap.put("admin", "KemalNasafi_freecrm123");
		userMap.put("customer", "KemalNasaf_freecrm123");
		return userMap;
	}

	public static HashMap<Integer, String> monthMap() {
		HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
		monthMap.put(1, "January");
		monthMap.put(2, "February");
		monthMap.put(3, "March");
		monthMap.put(4, "April");
		monthMap.put(5, "May");
		monthMap.put(6, "June");
		monthMap.put(7, "July");
		monthMap.put(8, "August");
		monthMap.put(9, "September");
		monthMap.put(10, "October");
		monthMap.put(11, "November");
		monthMap.put(12, "December");
		return monthMap;
	}
}
