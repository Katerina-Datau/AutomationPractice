package tests;

import io.qameta.allure.TmsLink;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Log4j2(topic = "login test log")
public class LoginTest extends BaseTest {

    @Test(description = "Log in with correct credentials")
    @TmsLink("AP-28")
    public void correctLogin() {
        try {
            loginPage.openPage();
            loginPage.login("oberyn.martell@dorne.wst", "unbent111");
            Assert.assertTrue(loginPage.ifLoggedIn());
        } catch (Exception e) {
            log.error("It happened again", e);
        }
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
    @TmsLink("AP-34")
    public void accountTest(String email, String password, int errors, String errorMessage) {
        try {
            loginPage.openPage();
            loginPage.login(email, password);
            Assert.assertFalse(loginPage.ifLoggedIn());
            List<WebElement> actualErrorList = loginPage.checkError();
            Assert.assertEquals(actualErrorList.size(), errors, "Error number doesn't match");
            Assert.assertTrue(createAccountPage.findText(actualErrorList, errorMessage), "Error not found");
        } catch (Exception e) {
            log.error("It happened again", e);
        }
    }

    @Test(description = "Password retrieval with correct credentials")
    @TmsLink("AP-35")
    public void retrievePasswordCorrect() {
        try {
            loginPage.openPage();
            loginPage.passwordRetrieval("oberyn.martell@dorne.wst");
            Assert.assertTrue(loginPage.ifRetrieved(), "Failed to retrieve password");
        } catch (Exception e) {
            log.error("It happened again", e);
        }
    }

    @DataProvider(name = "Retrieve with incorrect data")
    public Object[][] retrievalInput() {
        return new Object[][]{
                {"", 1, "Invalid email address."},
                {"oberyn.martell@@dorne.wst", 1, "Invalid email address."},
                {"quentyn@dorne.wst", 1, "There is no account registered for this email address."}
        };
    }

    @Test(description = "Password retrieval attempt with incorrect email", dataProvider = "Retrieve with incorrect data")
    @TmsLink("AP-38")
    public void retrievalTest(String email, int errors, String errorMessage) {
        try {
            loginPage.openPage();
            loginPage.passwordRetrieval(email);
            Assert.assertFalse(loginPage.ifRetrieved());
            List<WebElement> actualErrorList = loginPage.checkError();
            Assert.assertEquals(actualErrorList.size(), errors, "Error number doesn't match");
            Assert.assertTrue(createAccountPage.findText(actualErrorList, errorMessage), "Error not found");
        } catch (Exception e) {
            log.error("It happened again", e);
        }
    }

    @Test(description = "Logging out")
    @TmsLink("AP-27")
    public void logOutTest() {
        try {
            loginPage.openPage();
            loginPage.login("oberyn.martell@dorne.wst", "unbent111");
            loginPage.logOut();
            Assert.assertFalse(loginPage.ifLoggedIn());
        } catch (Exception e) {
            log.error("It happened again", e);
        }
    }
}