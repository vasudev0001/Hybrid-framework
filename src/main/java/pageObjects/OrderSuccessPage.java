package pageObjects;

import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class OrderSuccessPage extends TestBase {
	By orderSuccessMsg = By.xpath("//div[contains(@class,'toast-title')]");

	public boolean getSuccessMsg(String OrdersuccessMessage) {
		waitUntilWebElementDisp(orderSuccessMsg, 2, 100);
		String SuccessMsg=DriverFactory.getInstance().getDriver().findElement(orderSuccessMsg).getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Order success message: "+SuccessMsg);
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Order successfully placed.");
		return SuccessMsg.equalsIgnoreCase(OrdersuccessMessage);
	}

}
