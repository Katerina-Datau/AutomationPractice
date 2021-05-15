package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {

    /**
     * 1 Log in test
     */
    @Test(description = "Log in with correct credentials")
    public void correctLogin() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        Assert.assertTrue(loginPage.ifLoggedIn());
    }

    @Test(description = "Try logging in with correct email and incorrect password")
    public void wrongPassword() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "wrongpass");
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.loginError();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Authentication failed."));
    }

    @Test(description = "Try logging in with a valid, but non-registered email")
    public void notRegistered() {
        loginPage.openPage();
        loginPage.login("quentyn@dorne.wst", "12345");
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.loginError();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Authentication failed."));
    }

    @Test(description = "Try logging in with an invalid email")
    public void loginInvalidEmail() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@@dorne.wst", "unbent111");
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.loginError();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid email address."));
    }

    @Test(description = "Try logging in with a correct email and (null) password")
    public void nullLogin() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "");
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.loginError();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Password is required."));
    }

    @Test(description = "Try logging in with (null) credentials")
    public void nullPassword() {
        loginPage.openPage();
        loginPage.login("", "");
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.loginError();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "An email address required."));
    }

    //TODO check everything after CA has been sorted out
    @Test(description = "Password retrieval with correct credentials")
    public void retrievePasswordCorrect() {
        loginPage.openPage();
        loginPage.passwordRetrieval("oberyn.martell@dorne.wst");
        Assert.assertTrue(loginPage.ifRetrieved());
    }

    //TODO password retrieval 1-4
}