package reusableComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import pageObjects.CartPageObject;
import testBase.DriverFactory;
import testBase.ExtentFactory;

public class ActionEngine {
	By cartBtn = By.xpath("//button[@routerlink='/dashboard/cart']");

	public List<WebElement> waitUntilAllWebElementDisp(By ele, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		List<WebElement> list = wait.until(
				ExpectedConditions.visibilityOfAllElements(DriverFactory.getInstance().getDriver().findElements(ele)));
		return list;
	}

	public WebElement waitUntilWebElementDisp(By ele, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		WebElement ele1 = wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
		return ele1;
	}

	public WebElement waitUntilWebElementDispByEle(By ele, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		WebElement ele1 = wait
				.until(ExpectedConditions.visibilityOf(DriverFactory.getInstance().getDriver().findElement(ele)));
		return ele1;
	}
	
	public WebElement waitUntilElementClickable(By ele, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		WebElement ele1 = wait
				.until(ExpectedConditions.elementToBeClickable(DriverFactory.getInstance().getDriver().findElement(ele)));
		return ele1;
	}

	public void waitUntilWebElementInvisible(By ele, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ele));
	}

	public List<WebElement> waitUntilChildElementsVisible(By parentEle, By childEle, int waitTime, int poolingTime) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Duration.ofSeconds(waitTime),
				Duration.ofMillis(poolingTime));
		List<WebElement> childElements = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
				DriverFactory.getInstance().getDriver().findElement(parentEle), childEle));
		return childElements;
	}

	public String scrollIntoView(By element) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
		WebElement webEle = DriverFactory.getInstance().getDriver().findElement(element);
		executor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", webEle);
		return webEle.getText();
	}

	// Customized sendkeys method-> To log sendkeys message for every occ.
	public void sendKeys_custom(WebElement element, String fieldName, String valueToBeSent) {
		try {
			element.sendKeys(valueToBeSent);
			// log success message in exgent report
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					fieldName + "==> Ented value as: " + valueToBeSent);
		} catch (Exception e) {
			// log failure in extent
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					"Value enter in field: " + fieldName + " is failed due to exception: " + e);
		}
	}

	// custom click method to log evey click action in to extent report
	public void click_custom(WebElement element, String fieldName) {
		try {
			element.click();
			// log success message in exgent report
			ExtentFactory.getInstance().getExtent().log(Status.INFO, fieldName + "==> Clicked Successfully! ");
		} catch (Exception e) {
			// log failure in extent
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Unable to click on field: " + fieldName + " due to exception: " + e);
		}
	}

	// clear data from field
	public void clear_custom(WebElement element, String fieldName) {
		try {
			element.clear();
			Thread.sleep(250);
			ExtentFactory.getInstance().getExtent().log(Status.INFO, fieldName + "==> Data Cleared Successfully! ");
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Unable to clear Data on field: " + fieldName + " due to exception: " + e);

		}
	}

	// custom mouseHover
	public void moveToElement_custom(WebElement element, String fieldName) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(DriverFactory.getInstance().getDriver());
			actions.moveToElement(element).build().perform();
			ExtentFactory.getInstance().getExtent().log(Status.INFO, fieldName + "==> Mouse hovered Successfully! ");
			Thread.sleep(1000);
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Unable to hover mouse on field: " + fieldName + " due to exception: " + e);

		}
	}

	// check if element is Present
	public boolean isElementPresent_custom(WebElement element, String fieldName) {
		boolean flag = false;
		try {
			flag = element.isDisplayed();
			ExtentFactory.getInstance().getExtent().log(Status.INFO, fieldName + "==> Presence of field is: " + flag);
			return flag;
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Checking for presence of field: " + fieldName + " not tested due to exception: " + e);
			return flag;
		}
	}

	// Select dropdown value value by visibleText
	public void selectDropDownByVisibleText_custom(WebElement element, String fieldName, String ddVisibleText)
			throws Throwable {
		try {
			Select s = new Select(element);
			s.selectByVisibleText(ddVisibleText);
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					fieldName + "==> Dropdown Value Selected by visible text: " + ddVisibleText);
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Dropdown value not selected for field: " + fieldName + "  due to exception: " + e);
		}
	}

	// Select dropdown value by value
	public void selectDropDownByValue_custom(WebElement element, String fieldName, String ddValue) throws Throwable {
		try {
			Select s = new Select(element);
			s.selectByValue(ddValue);
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					fieldName + "==> Dropdown Value Selected by visible text: " + ddValue);
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					"Dropdown value not selected for field: " + fieldName + "  due to exception: " + e);
		}
	}

	// String Asserts
	public void assertEqualsString_custom(String expvalue, String actualValue, String locatorName) throws Throwable {
		try {
			Assert.assertEquals(expvalue, actualValue);
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "String Assertion is successful on field "
					+ locatorName + " Expected value was: " + expvalue + " actual value is: " + actualValue);
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL, "String Assertion FAILED on field " + locatorName
					+ " Expected value was: " + expvalue + " actual value is: " + actualValue);

		}
	}

	// Get text from webelement
	public String getText_custom(WebElement element, String fieldName) {
		String text = "";
		try {
			text = element.getText();
			ExtentFactory.getInstance().getExtent().log(Status.INFO, fieldName + "==> Text retried is: " + text);
			return text;
		} catch (Exception e) {
			ExtentFactory.getInstance().getExtent().log(Status.FAIL,
					fieldName + "==> Text not retried due to exception: " + e);

		}
		return text;
	}

	// ---------------
	public CartPageObject clickONCart() {
		waitUntilWebElementDisp(cartBtn, 4, 200);
		DriverFactory.getInstance().getDriver().findElement(cartBtn).click();
		ExtentFactory.getInstance().getExtent().log(Status.INFO, "clicked on cart button.");
		return new CartPageObject();
	}

}
