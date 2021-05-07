package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CreateAccountTest extends BaseTest {

    /**1. Email validation
     * 1.1 Valid email*/
    @Test
    public void validEmail() {
        createAccountPage.openPage();
        Assert.assertTrue(createAccountPage.validateEmail(createAccountPage.createValidEmail()));
    }



}
