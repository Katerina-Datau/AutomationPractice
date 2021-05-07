package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAccountTest extends BaseTest {

    /**
     * 1. Email validation
     * 1.1 Valid email
     */
    @Test
    public void validEmail() {
        createAccountPage.openPage();
        Assert.assertTrue(createAccountPage.tryValidEmail(createAccountPage.createValidEmail()));
    }

    /**
     * 1.2 Invalid email - @@
     */
    @Test
    public void invalidEmailDoubleToad() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.createInvalidEmailDoubleToad()), "Invalid email address.");

    }

    /**
     * 1.3. Invalid email - (text)@(text).(text).
     */
    @Test
    public void invalidEmailEndsWithDot() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.createInvalidEmailEndsWithDot()), "Invalid email address.");
    }

    /**
     * 1.4 Invalid email - .(text)@(text).(text)
     */
    @Test
    public void invalidEmailStartsWithDot() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.createInvalidEmailStartsWithDot()), "Invalid email address.");
    }

    /**
     * 1.5 Invalid email - no @, no .
     */
    @Test
    public void invalidEmailNoSyntax() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.createInvalidEmailNoSyntax()), "Invalid email address.");
    }

    /**
     * 1.6 Invalid email - forbidden symbols in domain name
     */
    @Test
    public void invalidEmailForbiddenDomainSymbols() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.createInvalidEmailForbiddenDomainSymbols('#')), "Invalid email address.");
    }

    /**
     * 1.7 (null) email
     */
    @Test
    public void invalidEmailNullEmail() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(""), "Invalid email address.");
    }

    /**
     * 1.8 Email valid, but an account using this email address has already been registered
     */
    @Test
    public void emailAlreadyExists() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(createAccountPage.emailAlreadyExists()),
                "An account using this email address has already been registered. Please enter a valid password or request a new one.");
    }

    /**
     * 2. Account creation
     */


}
