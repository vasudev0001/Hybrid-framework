package testCase;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;
import pageObjects.LoginPageObjects;
import reusableComponents.ReadExcelDataHashMap;
import testBase.ExtentFactory;
import testBase.TestBase;

public class LoginPageErrorMessage extends TestBase {
	LoginPageObjects ln = new LoginPageObjects();
	SoftAssert softassert = new SoftAssert();

	public boolean checkFlashMsg(String flashErrMsg, String loginErrorMsg) {
		boolean flag = false;
		if (flashErrMsg !=null) {
			ExtentFactory.getInstance().getExtent().log(Status.INFO,"Flash message for invalid credentials: "+flashErrMsg);
			flag = true;
		}
		return flag;
	}

	public void assertion(boolean status, SoftAssert softassert) {
		if (status = true) {
			softassert.assertTrue(status);
		}
	}

	@Test(dataProvider = "hello")
	public void verifyLoginMessage(HashMap<String, Object> a) {
		String userName = a.get("User Name").toString();
		String password = a.get("Password").toString();
		String exp_loginErrorMsg = a.get("page heading").toString();
		ln.login(userName, password);
		String flashErrorMessage = ln.getLoginFlashErrorMessage();
		String[] actual_ErrorMsg = ln.getLoginErrorMsg();
		String expect_ErrMsg[] = ln.splitString(exp_loginErrorMsg, ",");
		
		boolean flashErrMsg = checkFlashMsg(flashErrorMessage, exp_loginErrorMsg);
		assertion(flashErrMsg, softassert);
		boolean b = ln.un_containsGmail(userName, actual_ErrorMsg);
		assertion(b, softassert);
		boolean un_empty = ln.check_UnIsEmpty(userName, actual_ErrorMsg, expect_ErrMsg);
		assertion(un_empty, softassert);
		boolean pw_empty = ln.check_pwIsEmpty(password, actual_ErrorMsg, expect_ErrMsg);
		assertion(pw_empty, softassert);
	}

	ReadExcelDataHashMap excel = new ReadExcelDataHashMap();

	@DataProvider(name = "hello",parallel=true)
	public Object[] run() throws IOException {
		Object[] a = excel.readExcel("WebtestData.xlsx", "login error message");
		System.out.println(a.length);
		return a;
	}
}
