package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    /**
     * 1.1 Input correct credentials
     */
    @Test
    public void correctLogin() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        Assert.assertTrue(loginPage.ifLoggedIn());
    }

    //TODO cases 1.2 - 1.6
    //TODO password retrieval 1-4
}
