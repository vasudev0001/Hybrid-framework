package testCase;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePageObjects;
import pageObjects.LoginPageObjects;
import reusableComponents.ReadExcelDataHashMap;
import testBase.TestBase;

public class LoginValidation extends TestBase {
	LoginPageObjects ln = new LoginPageObjects();

	@Test(dataProvider = "loginValidation")
	public void getPageTest(HashMap<String, Object> a) throws InterruptedException {

		String userName = a.get("User Name").toString();
		String pwd = a.get("Password").toString();
		String expectedHeadingAfterLogin=a.get("page heading").toString();
		ln.userNameEmpty(userName);
		ln.passwordEmpty(pwd);
		String loginPageHeading = ln.loginHeading();
		HomePageObjects h = ln.login(userName, pwd);
		String headingAfterLogin = ln.getHeadingAfterLogin(h);
		boolean loginStatus = ln.verifyHeadingAfterLogin(headingAfterLogin, expectedHeadingAfterLogin, loginPageHeading);
		assertTrue(loginStatus);
	}

	ReadExcelDataHashMap excel = new ReadExcelDataHashMap();

	@DataProvider(name = "loginValidation",parallel=true)
	public Object[] run() throws IOException {
		Object[] a = excel.readExcel("WebtestData.xlsx", "Login Validation");
		System.out.println(a.length);
		return a;
	}
}
