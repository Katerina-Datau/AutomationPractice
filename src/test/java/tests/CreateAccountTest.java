package tests;

import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import lombok.extern.log4j.Log4j2;
import model.Account;
import model.Gender;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2(topic = "account creation test log")
public class CreateAccountTest extends BaseTest {

    /**
     * 1. Email validation page
     */

    @Test(description = "Input valid email address")
    @TmsLink("AP-37")
    public void validEmail() {
        try {
            createAccountPage.openPage();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Assert.assertTrue(createAccountPage.tryValidEmail(StringUtils.createValidEmail()),
                    "Email expected to match");
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @DataProvider(name = "Input incorrect data")
    public Object[][] emailInput() {
        return new Object[][]{
                {StringUtils.createInvalidEmailDoubleToad(), "Invalid email address."},
                {StringUtils.createInvalidEmailEndsWithDot(), "Invalid email address."},
                {StringUtils.createInvalidEmailStartsWithDot(), "Invalid email address."},
                {StringUtils.createInvalidEmailNoSyntax(), "Invalid email address."},
                {StringUtils.createInvalidEmailForbiddenDomainSymbols('#'), "Invalid email address."},
                {StringUtils.createInvalidEmailNull(), "Invalid email address."},
                {StringUtils.emailAlreadyExists(), "An account using this email address has already been registered. Please enter a valid password or request a new one."},
        };
    }

    @Test(description = "Input incorrect email", dataProvider = "Input incorrect data")
    @TmsLink("AP-1")
    public void accountTest(String emailInput, String errorMessage) {
        try {
            createAccountPage.openPage();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Assert.assertEquals(createAccountPage.tryWrongEmail(emailInput),
                    errorMessage, "Message doesn't match");
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    /**
     * 2. Account creation
     */
    @Test(description = "Input valid data in all explicitly required fields")
    public void createValidAccountRequiredFieldsOnly() {
        try {
            Account account = Account.builder()
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            Assert.assertTrue(createAccountPage.isAccountCreated());
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input valid data in all the fields, explicitly required and otherwise")
    public void createValidAccountAllFields() {
        try {
            createAccountPage.openPage();
            Account account = Account.builder()
                    .gender(Gender.MRS)
                    .birthDay(String.format("%s", (int) (Math.random() * 28) + 1))
                    .birthMonth(String.format("%s", (int) (Math.random() * 12) + 1))
                    .birthYear(String.format("%s", (int) (Math.random() * 122) + 1900))
                    .subscribe(true)
                    .getOffers(true)
                    .companyName(new StringUtils().space().agency())
                    .address2(new StringUtils().lordOfTheRings().location())
                    .other(new StringUtils().harryPotter().quote())
                    .homePhone(StringUtils.createBothifiedString("+###########"))
                    .build();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            Assert.assertTrue(createAccountPage.isAccountCreated());
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Leave all the fields (null)")
    public void createNullAccount() {
        try {
            createAccountPage.openPage();
            Account account = Account.builder()
                    .firstName("")
                    .lastName("")
                    .password("")
                    .address1("")
                    .city("")
                    .country("")
                    .zip("")
                    .mobilePhone("")
                    .build();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            createAccountPage.clearAll();
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
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
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input numerical data in First name and Last name fields")
    public void numericalNames() {
        try {
            Account account = Account.builder()
                    .firstName(StringUtils.createBothifiedString("#######"))
                    .lastName(StringUtils.createBothifiedString("#######"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 2);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input names containing forbidden symbols")
    public void forbiddenSymbolNames() {
        try {
            Account account = Account.builder()
                    .firstName("#%orge")
                    .lastName("#%@!$()son")
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 2);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input names over character limit of 32 characters")
    public void namesOverCharacterLimit() {
        try {
            Account account = Account.builder()
                    .firstName(StringUtils.createBothifiedString("???????????????????????????????????"))
                    .lastName(StringUtils.createBothifiedString("???????????????????????????????????"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 2);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is too long. Maximum length: 32"));
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is too long. Maximum length: 32"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input password under character limit of 5 characters")
    public void passwordTooShort() {
        try {
            Account account = Account.builder()
                    .password(StringUtils.createBothifiedString("#?#"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "passwd is invalid."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Set birthday to non-existent date in the past")
    public void incorrectBirthday() {
        try {
            Account account = Account.builder()
                    .birthDay("31")
                    .birthMonth("2")
                    .birthYear("1980")
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Set year of birth as (null)")
    public void nullYear() {
        try {
            Account account = Account.builder()
                    .birthDay(String.format("%s", (int) (Math.random() * 28) + 1))
                    .birthMonth(String.format("%s", (int) (Math.random() * 12) + 1))
                    .birthYear("")
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input city with a name over limit of 64 characters")
    public void cityOverLimit() {
        try {
            Account account = Account.builder()
                    .city(StringUtils.stringOver64())
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is too long. Maximum length: 64"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input city with forbidden characters in the name")
    public void cityForbidden() {
        try {
            Account account = Account.builder()
                    .city("$ @ # % = +")
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is invalid."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Don't select a state while 'country' is set to 'USA'")
    public void noStateSelected() {
        try {
            Account account = Account.builder()
                    .state(0)
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "This country requires you to choose a State."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input zip which doesn't follow 00000 format")
    public void wrongZip() {
        try {
            Account account = Account.builder()
                    .zip(StringUtils.createBothifiedString("#####??"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input an invalid cellphone number (consists of letters a-z)")
    public void wrongCell() {
        try {
            Account account = Account.builder()
                    .mobilePhone(StringUtils.createBothifiedString("???????????"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone_mobile is invalid."));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input cellphone number over limit of 32 numbers")
    public void cellOverLimit() {
        try {
            Account account = Account.builder()
                    .mobilePhone(StringUtils.stringOver64())
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone_mobile is too long. Maximum length: 32"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(description = "Input non-required home phone number while leaving cellphone number (null)", expectedExceptions = {AssertionError.class})
    @Issue("Assertion Error")
    public void isCellRequired() {
        try {
            Account account = Account.builder()
                    .mobilePhone("")
                    .homePhone(StringUtils.createBothifiedString("+###########"))
                    .build();
            createAccountPage.openPage();
            createAccountPage.submitValidEmail(StringUtils.createValidEmail());
            createAccountPage.createAccount(account);
            List<WebElement> actualErrorList = createAccountPage.checkErrors();
            Assert.assertEquals(actualErrorList.size(), 1);
            Assert.assertTrue(createAccountPage.findText(actualErrorList, "You must register at least one phone number"));
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }
}
