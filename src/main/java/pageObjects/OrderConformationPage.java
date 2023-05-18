package pageObjects;

import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class OrderConformationPage extends TestBase{

	By conformationMsgloc = By.xpath("//div[@aria-label='Order Placed Successfully']");

	public String getOrderSuccessMsg() {
		waitUntilWebElementDisp(conformationMsgloc,4,250);
		String conformationMsg = DriverFactory.getInstance().getDriver().findElement(conformationMsgloc).getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Product order success message: "+conformationMsg);
		return conformationMsg;
	}
}
