package Tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * 2.1 Only required fields are filled with valid data
     */
    @Test
    public void createValidAccountRequiredFieldsOnly() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        Assert.assertTrue(createAccountPage.createValidAccountOnlyRequiredFields());
    }

    /**
     * 2.2 All the fields filled with valid data, explicitly required and otherwise
     */
    @Test
    public void createValidAccountAllFields() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        Assert.assertTrue(createAccountPage.createValidAccountAllTheFields());
    }

    /**
     * 2.3. All the fields null
     */
    @Test
    public void createNullAccount() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        List<WebElement> actualErrorList = createAccountPage.createNullAccount();
        Assert.assertEquals(actualErrorList.size(), 11);
        Assert.assertTrue(createAccountPage.findError(actualErrorList, "You must register at least one phone number."));
        Assert.assertTrue(createAccountPage.findError(actualErrorList, "lastname is required."));
        Assert.assertTrue(createAccountPage.findError(actualErrorList, "firstname is required."));

    }


}
