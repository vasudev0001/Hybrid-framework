package pageObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import testBase.DriverFactory;
import testBase.ExtentFactory;
import testBase.TestBase;

public class LoginPageObjects extends TestBase {

	By userNameField = By.id("userEmail");
	By passwordtxt = By.id("userPassword");
	By loginBtn = By.id("login");
	By errorMessageLocator = By.cssSelector("#toast-container div[class*='toast-message']");
	By loginErrorMessage = By.cssSelector("div[class='invalid-feedback'] div");
	By loginPageHeading = By.cssSelector("div[class=\"login-wrapper my-auto p-5\"] h1");
	By ln_flashErrorMsg = By.cssSelector("#toast-container div[class*='toast-message']");

	public String loginFlashErrorMsg() {
		waitUntilWebElementDisp(ln_flashErrorMsg, 2, 200);
		WebElement ele = DriverFactory.getInstance().getDriver().findElement(ln_flashErrorMsg);
		String loginErrorMsg = ele.getText().trim();
		return loginErrorMsg;
	}

	public String loginHeading() {
		return DriverFactory.getInstance().getDriver().findElement(loginPageHeading).getText();
	}

	// login to application.
	public HomePageObjects login(String email, String password) {
		System.out.println("Before Test Thread ID: loginobect " + Thread.currentThread().getId());
		sendKeys_custom(DriverFactory.getInstance().getDriver().findElement(userNameField), "LoginEmailFIeld", email);
		sendKeys_custom(DriverFactory.getInstance().getDriver().findElement(passwordtxt), "LoginPasswordFIeld",
				password);
		click_custom(DriverFactory.getInstance().getDriver().findElement(loginBtn), "LoginButton");
		return new HomePageObjects();
	}

	public String[] loginErrorMessage() {
		waitUntilAllWebElementDisp(loginErrorMessage, 4, 200);
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(loginErrorMessage);
		if (ele.size() != 0) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "Retriver the error message.");
		} else
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "Dont fetch the error message.");

		Stream<String> c = ele.stream().map(s -> s.getText());
		String[] stringArray = c.toArray(size -> new String[size]);
		return stringArray;
	}

//------------------ login validation----------
	public boolean userNameEmpty(String un) {
		boolean unStatus = un.toString().isEmpty();
		if (unStatus == true) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "User name credential is empty");
		}
		return unStatus;
	}

	public boolean passwordEmpty(String pw) {
		boolean pwStatus = pw.toString().isEmpty();
		if (pwStatus == true) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "password credential is empty");
		}
		return pwStatus;
	}

	public void textAssertion(boolean credentialStatus, String actul_msg, String expected_msg) {
		if (credentialStatus == true)
			assertEquals(actul_msg, expected_msg);
	}

	public String getHeadingAfterLogin(HomePageObjects homeObject) {
		String heading = null;
		try {
			heading = loginHeading();

		} catch (Exception e) {
		}
		try {
			heading = homeObject.getHomePageHeading();
		} catch (Exception e) {
		}
		return heading;
	}

	public boolean verifyHeadingAfterLogin(String actualHeading, String expectedHeading, String loginPageHeading) {
		boolean loginSuccess = false;
		if (actualHeading.equalsIgnoreCase(expectedHeading.toString())) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					"Heading after clicking on login button: " + actualHeading);
			loginSuccess = true;
		}
		if (actualHeading.equalsIgnoreCase(loginPageHeading)) {
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Login failed for invalid login credentials.");
		} else
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Login success for valid login credentials.");
		return loginSuccess;
	}

	// --------reference for login validation-------
	public void loginValidation(Object un, Object pw, Object msg) {
		boolean unStatus = un.toString().isEmpty();
		boolean pwStatus = pw.toString().isEmpty();
		String loginHeading = loginHeading();
		HomePageObjects h = login(un.toString(), pw.toString());

		if (unStatus == true) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "user name is empty.");
		}
		if (pwStatus == true) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "password is empty.");
		}

		String actualHeading = null;
		try {
			actualHeading = loginHeading();
		} catch (Exception e) {
		}
		try {
			actualHeading = h.getHomePageHeading();
		} catch (Exception e) {
		}
		boolean status = false;
		if (actualHeading.equalsIgnoreCase(msg.toString())) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO,
					"Heading after clicking on login button: " + msg.toString());
			status = true;
		}
		if (actualHeading.equalsIgnoreCase(loginHeading)) {
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Login failed for invalid login credentials.");
		} else
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Login success for valid login credentials.");
		assertTrue(status, "Expected heading: " + msg.toString() + ". Actual heading: " + actualHeading);
	}
	// ----------- error messages for login------------

	public String getLoginFlashErrorMessage() {

		String invalidLogin = null;
		try {
			System.out.println("try block");
			invalidLogin = loginFlashErrorMsg();
			System.out.println("-->" + invalidLogin);
			// get actual error msg.
		} catch (Exception e) {
		}
		return invalidLogin;
	}

	public String[] getLoginErrorMsg() {
		String shortcredentials[] = null;
		try {
			shortcredentials = loginErrorMessage();
		} catch (Exception ee) {

		}
		return shortcredentials;
	}

	public String[] splitString(String msg, String bysymbol) {
		String expErrMag[] = msg.toString().split(",");
		return expErrMag;
	}

	public boolean un_containsGmail(String un, String shortcredentials[]) {
		boolean b = false;
		if (!(un.toString().contains("@gmail"))) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "user name error message: " + shortcredentials[0]);
			b = true;
		}
		return b;
	}

	public boolean flashLoginErrorMsg(String invalidLogin) {
		boolean b = false;
		if (invalidLogin != null) {
			System.out.println("invalidLogin not null");
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "user name error message: " + invalidLogin);
			b = true;
		}
		return b;
	}

	public boolean check_UnIsEmpty(String userName, String shortcredentials[], String expErrMag[]) {
		boolean unStatus = userName.isEmpty();
		String un_expectedErrMsg = null;
		String un_actErrMsg = null;
		boolean b = false;
		if (unStatus == true) {
			un_actErrMsg = shortcredentials[0];
			un_expectedErrMsg = expErrMag[0];
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "user name error message: " + un_actErrMsg);
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "user name is empty.");
			if (un_expectedErrMsg.equalsIgnoreCase(un_actErrMsg)) {
				b = true;
			}
		}
		return b;
	}

	public boolean check_pwIsEmpty(String password, String shortcredentials[], String expErrMag[]) {
		boolean pwStatus = password.isEmpty();
		String pw_expectedErrMsg = null;
		String pw_actErrMsg = null;
		boolean b = false;
		if (pwStatus == true) {
			pw_actErrMsg = shortcredentials[1];
			pw_expectedErrMsg = expErrMag[1];
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "password error message: " + pw_actErrMsg);
			ExtentFactory.getInstance().getExtent().log(Status.INFO, "Password is empty.");
			if (pw_expectedErrMsg.equalsIgnoreCase(pw_actErrMsg)) {
				b = true;
			}
		}
		return b;
	}

}
