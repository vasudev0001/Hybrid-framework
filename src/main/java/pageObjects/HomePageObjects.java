package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class HomePageObjects extends TestBase {

	By homePageHeading = By.xpath("//div[@class='left mt-1']/h3");
	By productAdd_Animation = By.xpath("//div[contains(@class,'la-ball-scale-multiple')]");
	By productAdd_tostMassage = By.xpath("//div[@id='toast-container']//div[contains(@class,'toast-message')]");
	By productNameloc = By.xpath("//h5/b");
	By addToCartBtn = By.xpath(".//div[@class='card-body']//button/i[@class='fa fa-shopping-cart']");

	By products = By.xpath("//div[@class='container']/div[@class='row']");

	public String getHomePageHeading() {
		WebElement s = waitUntilWebElementDisp(homePageHeading, 4, 250);
		String homePage_heading = s.getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Home page heading: " + homePage_heading);
		return homePage_heading;
	}

	public boolean productAddAnimatioDisplayed() {
		return waitUntilWebElementDispByEle(productAdd_Animation, 2, 200).isDisplayed();
	}

	public void animationInvisible() {
		waitUntilWebElementInvisible(productAdd_Animation, 4, 250);
	}

	public String getproductAddToastMessage() {
		WebElement toastWebElement = waitUntilWebElementDisp(productAdd_tostMassage, 4, 250);
		String toastMsg = toastWebElement.getText();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "Toast message: " + toastMsg);
		return toastMsg;
	}

	private void waitUntilProductsDisplayed() {
		waitUntilWebElementDispByEle(products, 4, 500);
	}

	public void addProductToCart(String productName) {
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "search for product: " + productName);
		waitUntilProductsDisplayed();
		List<WebElement> itemList = DriverFactory.getInstance().getDriver().findElements(productNameloc);
		System.out.println("product size: " + itemList.size());
		for (WebElement e : itemList) {
			boolean desiredProduct = e.getText().trim().equalsIgnoreCase(productName.trim());
			if (desiredProduct == true) {
				ExtentFactory.getInstance().getExtent().log(Status.INFO, "Identified product name: " + e.getText());
				e.findElement(By.xpath(".//../following-sibling::button/i[@class='fa fa-shopping-cart']")).click();
				ExtentFactory.getInstance().getExtent().log(Status.INFO,
						"clicked on add to cart button: " + e.getText());
				break;
			}
		}
	}
}
