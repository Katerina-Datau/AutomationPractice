package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {

    //TODO find a way to merge correct and incorrect login into one method OR roll back to prevoius tests

    @Test(description = "Log in with correct credentials")
    public void correctLogin() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        Assert.assertTrue(loginPage.ifLoggedIn());
    }

    @DataProvider(name = "Input incorrect data")
    public Object[][] accountInput() {
        return new Object[][]{
                {"oberyn.martell@dorne.wst", "wrongpass", 1, "Authentication failed."},
                {"quentyn@dorne.wst", "12345", 1, "Authentication failed."},
                {"oberyn.martell@@dorne.wst", "unbent111", 1, "Invalid email address."},
                {"oberyn.martell@dorne.wst", "", 1, "Password is required."},
                {"", "", 1, "An email address required."}
        };
    }

    @Test(description = "Login attempt with incorrect credentials", dataProvider = "Input incorrect data")
    public void accountTest(String email, String password, int errors, String errorMessage) {
        loginPage.openPage();
        loginPage.login(email, password);
        Assert.assertFalse(loginPage.ifLoggedIn());
        List<WebElement> actualErrorList = loginPage.checkError();
        Assert.assertEquals(actualErrorList.size(), errors, "Error number doesn't match");
        Assert.assertTrue(createAccountPage.findText(actualErrorList, errorMessage), "Error not found");
    }

    @Test(description = "Password retrieval with correct credentials")
    public void retrievePasswordCorrect() {
        loginPage.openPage();
        loginPage.passwordRetrieval("oberyn.martell@dorne.wst");
        Assert.assertTrue(loginPage.ifRetrieved(), "Failed to retrieve password");
    }

    @Test(description = "Password retrieval with (null) credentials")
    public void nullRetrieval() {
        loginPage.openPage();
        loginPage.passwordRetrieval("");
        Assert.assertFalse(loginPage.ifRetrieved());
        List<WebElement> actualErrorList = loginPage.checkError();
        Assert.assertEquals(actualErrorList.size(), 1, "Error number doesn't match");
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid email address."), "Error not found");
    }
}