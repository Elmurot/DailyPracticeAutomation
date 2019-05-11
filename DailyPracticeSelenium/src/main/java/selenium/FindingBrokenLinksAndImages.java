package selenium;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FindingBrokenLinksAndImages {

	static WebDriver driver;

	public static void main(String[] args) throws MalformedURLException, IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get("https://www.pnc.com");

		// 1. get the list of all links and images: 500
		List<WebElement> linkList = driver.findElements(By.tagName("a"));
		linkList.addAll(driver.findElements(By.tagName("img")));

		System.out.println("Total number of all links: " + linkList.size());

		List<WebElement> activeLinks = new ArrayList<WebElement>(); // 450
		// 2. iterate linkList : exclude all the links & images that does not have any
		// href attribute
		for (int i = 0; i < linkList.size(); i++) {
//			System.out.println(linkList.get(i).getAttribute("href"));
			if (linkList.get(i).getAttribute("href") != null && (!linkList.get(i).getAttribute("href").contains("javascript"))
				&& (!linkList.get(i).getAttribute("href").contains("tel"))) {
				System.out.println(linkList.get(i).getAttribute("href"));
				activeLinks.add(linkList.get(i));
			}
		}
		// get the size of active link list

		System.out.println("Total number of all active links: " + activeLinks.size());

		// 3. check the href with http connection
		// 200 --- ok
		// 404 --- not found
		// 500 --- internal error
		// 400 --- bad request

		for (int j = 0; j < activeLinks.size(); j++) {

			HttpURLConnection connection = (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			System.out.println(activeLinks.get(j).getAttribute("href") + " ---> " + response);
		}
	}
}
