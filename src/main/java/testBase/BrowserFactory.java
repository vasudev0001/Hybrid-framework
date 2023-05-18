package testBase;

//import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import reusableComponents.PropertiesOperations;

public class BrowserFactory {

	public WebDriver createBrowserInstance() throws Exception {

		WebDriver driver = null;
		String browser = System.getProperty("browser") != null ? System.getProperty("browser")
				: PropertiesOperations.getPropertyValueByKey("browser");
		if (browser.contains("chrome")) {

			WebDriverManager.chromedriver().setup();
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			if (browser.contains("chromeHeadless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
//			driver.manage().window().setSize(new Dimension(1440,900));

		} else if (browser.equalsIgnoreCase("firefox")) {

			//WebDriverManager.firefoxdriver().setup();
			System.setProperty("webdriver.gecko.driver","C:\\selenium\\geckodriver-v0.32.0-win64\\geckodriver.exe");
			FirefoxOptions foptions = new FirefoxOptions();
			foptions.addArguments("-private");
			driver= new FirefoxDriver(foptions);

		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			// System.setProperty("webdriver.edge.driver","C:\\selenium\\edgedriver_win64\\msedgedriver.exe");
			EdgeOptions options = new EdgeOptions();
			 options.addArguments("--no-sandbox");
			options.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
	        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
	        options.addArguments("disable-infobars"); // disabling infobars
	        options.addArguments("--disable-extensions"); // disabling extensions
	        options.addArguments("--disable-gpu"); // applicable to windows os only
	        options.addArguments("--disable-dev-shm-usage");
			driver = new EdgeDriver(options);
		}
		driver.manage().window().maximize();
		return driver;
	}
}
