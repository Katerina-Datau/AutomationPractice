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
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.createNullAccount();
        Assert.assertEquals(actualErrorList.size(), 11);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "You must register at least one phone number."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "email is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "passwd is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "id_country is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "alias is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "address1 is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is required."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Country cannot be loaded with address->id_country"));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Country is invalid"));
    }

    /**
     * 2.4 Numbers in name fields
     */
    @Test
    public void numericalNames() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.numericalNames();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
    }

    /**
     * 2.5 Forbidden symbols in names
     */
    @Test
    public void forbiddenSymbolNames() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.forbiddenSymbolNames();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
    }

    /**
     * 2.6 Names over character limit
     */
    @Test
    public void namesOverCharacterLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.namesOverLimit();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is too long. Maximum length: 32"));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is too long. Maximum length: 32"));
    }

    /**
     * 2.7 Password less than 5 characters
     */
    @Test
    public void passwordTooShort() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.shortPassword();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "passwd is invalid."));
    }

    /**
     * 2.8 Incorrect birthday
     */
    @Test
    public void incorrectBirthday() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.invalidDate();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
    }

    /**
     * 2.9 Null year of birth
     */
    @Test
    public void nullYear() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.nullYear();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
    }

    /**
     * 2.10 City name over limit
     */
    @Test
    public void cityOverLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.cityOverLimit();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is too long. Maximum length: 64"));
    }

    /**
     * 2.11 Forbidden symbols in city name
     */
    @Test
    public void cityForbidden() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.cityForbidden();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is invalid."));
    }

    /**
     * 2.12 No state selected
     */
    @Test
    public void noStateSelected() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.noState();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "This country requires you to choose a State."));
    }

    /**
     * 2.13 Invalid Zip
     */
    @Test
    public void wrongZip() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.wrongZip();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
    }

    /**
     * 2.14 Wrong cellphone number
     */
    @Test
    public void wrongCell() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.alphabetPhone();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone is invalid."));
    }

    /**
     * 2.15 Phone number over limit
     */
    @Test
    public void cellOverLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.cellOverLimit();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone_mobile is too long. Maximum length: 32"));
    }

    /**
     * 2.16 Check if cellphone is truly required, throws AssertionError as home phone is enough
     */
    @Test
    public void isCellRequired() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        List<WebElement> actualErrorList = createAccountPage.isCellRequired();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "You must register at least one phone number"));
    }

}
