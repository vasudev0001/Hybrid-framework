package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class CheckoutObjectPage extends TestBase {
	By countryEditBox = By.xpath("//div[@class='form-group']/input");
	By autoSuggestionTable = By.xpath("//section[contains(@class,'ta-results list-group')]/button//span");
	By paymentTitle = By.xpath("//div[@class='payment']/div[@class='payment__title']");
	By placeorderBtn = By.xpath("//div[@class='actions']/a");

	public boolean getPaymentTitle(String expectedTitle) {
		waitUntilWebElementDispByEle(paymentTitle, 2, 250);
		String payment_title = DriverFactory.getInstance().getDriver().findElement(paymentTitle).getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Checkout page payment title: " + payment_title);
		return payment_title.equalsIgnoreCase(expectedTitle);
	}

	public void sendCountry(String country) {
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Country to be selected: "+ country);
		DriverFactory.getInstance().getDriver().findElement(countryEditBox).sendKeys(country);
		waitUntilWebElementDisp(autoSuggestionTable, 4, 250);
		List<WebElement> suggestionList = DriverFactory.getInstance().getDriver().findElements(autoSuggestionTable);
		System.out.println("-->" + suggestionList.size());
		for (WebElement element : suggestionList) {
			boolean b = element.getText().equalsIgnoreCase(country);
			System.out.println(element.getText());
			if (b == true) {
				element.click();
				break;
			}
		}
	}

	public String getOrderedcountry() {
		String country = DriverFactory.getInstance().getDriver().findElement(countryEditBox).getAttribute("value");
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Selected country name: " + country);
		return country;
	}
	
	public OrderSuccessPage clickOnPlaceOrderBtn() {
		waitUntilWebElementDispByEle(placeorderBtn, 2, 250).click();
	//	DriverFactory.getInstance().getDriver().findElement(placeorderBtn).click();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Clicked on order placed button.");
		return new OrderSuccessPage();
	}

}
