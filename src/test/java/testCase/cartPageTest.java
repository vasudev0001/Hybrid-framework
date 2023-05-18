package testCase;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.CartPageObject;
import pageObjects.CheckoutObjectPage;
import pageObjects.HomePageObjects;
import pageObjects.LoginPageObjects;
import pageObjects.OrderSuccessPage;
import reusableComponents.PropertiesOperations;
import reusableComponents.ReadExcelDataHashMap;
import testBase.TestBase;

public class cartPageTest extends TestBase {

	LoginPageObjects loginPage = new LoginPageObjects();
	@Test(dataProvider = "addProduct")
	public void TestCase1(HashMap<String, Object> a) throws Throwable {
		
		String userName = PropertiesOperations.getPropertyValueByKey("userid");
		String password = PropertiesOperations.getPropertyValueByKey("password");
		HomePageObjects homePage = loginPage.login(userName, password);
		String homePageHeading = homePage.getHomePageHeading();
		assertEquals(homePageHeading, a.get("Home page heading"));
		homePage.addProductToCart(a.get("product name").toString());
		boolean animationStatus = homePage.productAddAnimatioDisplayed();
		homePage.animationInvisible();
		assertTrue(animationStatus);
		String toastMessage = homePage.getproductAddToastMessage();
		assertEquals(toastMessage, a.get("Toast message").toString());
		CartPageObject cartPage = clickONCart();
		String cartpageHeading = cartPage.cartPageHeading();
		assertEquals(cartpageHeading, a.get("cart page heading").toString());
		boolean productStatus = cartPage.desiredCartProduct(a.get("product name").toString());
		assertTrue(productStatus);
		CheckoutObjectPage checkout = cartPage.clickOnCheckoutBtn();
		boolean paymentTitle = checkout.getPaymentTitle(a.get("payment title").toString());
		assertTrue(paymentTitle);
		checkout.sendCountry(a.get("country name").toString());
		String selectedCountry = checkout.getOrderedcountry();
		assertEquals(a.get("country name").toString(), selectedCountry);
		OrderSuccessPage successPage = checkout.clickOnPlaceOrderBtn();
		boolean orderSuccessStatus=successPage.getSuccessMsg(a.get("order success message").toString());
		assertTrue(orderSuccessStatus);
	}

	ReadExcelDataHashMap excel = new ReadExcelDataHashMap();

	@DataProvider(name = "addProduct", parallel = true)
	public synchronized Object[] run() throws IOException {
		Object[] a = excel.readExcel("WebtestData.xlsx", "Sheet1");
		System.out.println(a.length);
		return a;
	}
}
