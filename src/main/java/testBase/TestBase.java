package testBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import reusableComponents.ActionEngine;
import reusableComponents.PropertiesOperations;

public class TestBase extends ActionEngine{
	BrowserFactory bf = new BrowserFactory();
	
	@BeforeMethod  // It is executed before execute method that is annotated with @Test.
	public void LaunchApplication() throws Exception {
		System.out.println("Test started");
	//	String browser = PropertiesOperations.getPropertyValueByKey("browser");
		String url = PropertiesOperations.getPropertyValueByKey("url");
		WebDriver driver=bf.createBrowserInstance();
		DriverFactory.getInstance().setDriver(driver);
		System.out.println("Before Test Thread ID: testbase "+Thread.currentThread().getId());
		//DriverFactory.getInstance().getDriver().manage().window().maximize();
		DriverFactory.getInstance().getDriver().navigate().to(url);
	}
	

	@AfterMethod
	public void tearDown() {
		// DriverFactory.getInstance().closeBrowser();
	}
}
