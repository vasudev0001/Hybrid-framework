package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class CartPageObject extends TestBase {
	By cartPageHeading = By.xpath("//div[@class='heading cf']/h1");
	By cartProducts = By.xpath("//div[@class='cart']");
	By cartProductChild = By.xpath("./ul/li//div[@class='cartSection']/h3");
	By checkoutBtn = By.xpath("//li[@class='totalRow']/button[contains(@class,'btn')]");

	public String cartPageHeading() {
		waitUntilWebElementDispByEle(cartPageHeading, 2, 200);
		String cartHeading = DriverFactory.getInstance().getDriver().findElement(cartPageHeading).getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Cart page heading: " + cartHeading);
		return cartHeading;
	}

	public List<WebElement> waitUntilCartProductDisplayed() {
		List<WebElement> cartProductList = waitUntilChildElementsVisible(cartProducts, cartProductChild, 2, 500);
		return cartProductList;
	}

	public boolean desiredCartProduct(String productName) {
		List<WebElement> cartProductList = waitUntilCartProductDisplayed();
		int i = 1;
		boolean cartproductStatus = false;
		for (WebElement e : cartProductList) {
			boolean productStatus = e.getText().equalsIgnoreCase(productName);
			if (productStatus == true) {
				ExtentFactory.getInstance().getExtent().log(Status.INFO, "Product in cart page: " + e.getText());
				cartproductStatus = true;
				break;
			} else if (cartProductList.size() == i) {
				ExtentFactory.getInstance().getExtent().log(Status.INFO, "Desired product not in cart: " + productName);
			}
			i++;
		}
		return cartproductStatus;
	}

	public CheckoutObjectPage clickOnCheckoutBtn() {
		String btn = scrollIntoView(checkoutBtn);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DriverFactory.getInstance().getDriver().findElement(checkoutBtn).click();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Clicked on " + btn + " button.");
		return new CheckoutObjectPage();
	}
}