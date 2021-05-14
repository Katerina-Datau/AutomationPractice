package Tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateAccountTest extends BaseTest {

    /**
     * 1. Email validation page
     */
    @Test(description = "Input valid email address")
    public void validEmail() {
        createAccountPage.openPage();
        Assert.assertTrue(createAccountPage.tryValidEmail(StringUtils.createValidEmail()));
    }

    @Test(description = "Input invalid email with '@@' in the middle")
    public void invalidEmailDoubleToad() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.createInvalidEmailDoubleToad()), "Invalid email address.");
    }

    @Test(description = "Input invalid email with a dot in the end")
    public void invalidEmailEndsWithDot() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.createInvalidEmailEndsWithDot()), "Invalid email address.");
    }

    @Test(description = "Input invalid email beginning with a dot")
    public void invalidEmailStartsWithDot() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.createInvalidEmailStartsWithDot()), "Invalid email address.");
    }

    @Test(description = "Input invalid email lacking both '.' and '@'")
    public void invalidEmailNoSyntax() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.createInvalidEmailNoSyntax()), "Invalid email address.");
    }

    @Test(description = "Input invalid email which contains forbidden symbols in domain name")
    public void invalidEmailForbiddenDomainSymbols() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.createInvalidEmailForbiddenDomainSymbols('#')), "Invalid email address.");
    }

    @Test(description = "Input (null) email")
    public void invalidEmailNullEmail() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(""), "Invalid email address.");
    }

    @Test(description = "Input a valid email that's already been registered")
    public void emailAlreadyExists() {
        createAccountPage.openPage();
        Assert.assertEquals(createAccountPage.tryWrongEmail(StringUtils.emailAlreadyExists()),
                "An account using this email address has already been registered. Please enter a valid password or request a new one.");
    }

    /**
     * 2. Account creation
     */

    @Test(description = "Input valid data in all explicitly required fields")
    public void createValidAccountRequiredFieldsOnly() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                "",
                "",
                "",
                false,
                false,
                "",
                stringUtils.address().fullAddress(),
                "",
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                "",
                stringUtils.phoneNumber().cellPhone(),
                "");
        Assert.assertTrue(createAccountPage.isAccountCreated());
    }

    //TODO why don't the parameters show up properly

    @Test(description = "Input valid data in all the fields, explicitly required and otherwise")
    public void createValidAccountAllFields() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        Assert.assertTrue(createAccountPage.isAccountCreated());
    }

    //TODO email - method.clear(), alias.clear(), что с ними делать, когда они стринги?!
    @Test(description = "Leave all the fields (null)")
    public void createNullAccount() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                false,
                "",
                "",
                "",
                "",
                0,
                0,
                "",
                "",
                "",
                ""
        );
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
    }

    @Test(description = "Input numerical data in First name and Last name fields")
    public void numericalNames() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.createString("#######"),
                stringUtils.createString("#######"),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
    }

    @Test(description = "Input names containing forbidden symbols")
    public void forbiddenSymbolNames() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                "#%orge",
                "#%@!$()son",
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is invalid."));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is invalid."));
    }

    @Test(description = "Input names over character limit of 32 characters")
    public void namesOverCharacterLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.createAccount(
                "",
                stringUtils.createString("???????????????????????????????????"),
                stringUtils.createString("???????????????????????????????????"),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 2);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "lastname is too long. Maximum length: 32"));
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "firstname is too long. Maximum length: 32"));
    }

    @Test(description = "Input password under character limit of 5 characters")
    public void passwordTooShort() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.createString("#?#"),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "passwd is invalid."));
    }

    @Test(description = "Set birthday to non-existent date in the past")
    public void incorrectBirthday() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                "31",
                "2",
                "1980",
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
    }

    //TODO хрен знает, что с годом делать (в оригинале .selectByIndex(1) )
    @Test(description = "Set year of birth as (null)")
    public void nullYear() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                "0",
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "Invalid date of birth"));
    }

    @Test(description = "Input city with a name over limit of 64 characters")
    public void cityOverLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.createString("##################################################################################################################################################################"),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is too long. Maximum length: 64"));
    }

    @Test(description = "Input city with forbidden characters in the name")
    public void cityForbidden() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                "$ @ # % = +",
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "city is invalid."));
    }

    //TODO state - selectbyindex, хрен знает, что делать
    @Test(description = "Don't select a state while 'country' is set to 'USA'")
    public void noStateSelected() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                0,
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "This country requires you to choose a State."));
    }

    @Test(description = "Input zip which doesn't follow 00000 format")
    public void wrongZip() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####??"),
                stringUtils.createString("+###########"),
                stringUtils.phoneNumber().cellPhone(),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
    }

    @Test(description = "Input an invalid cellphone number (consists of letters a-z)")
    public void wrongCell() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.createString("???????????"),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone_mobile is invalid."));
    }

    @Test(description = "Input cellphone number over limit of 32 numbers")
    public void cellOverLimit() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                stringUtils.createString("######################################"),
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "phone_mobile is too long. Maximum length: 32"));
    }

    @Test(description = "Input non-required home phone number while leaving explicitly required cellphone number (null)", expectedExceptions = {AssertionError.class})
    public void isCellRequired() {
        createAccountPage.openPage();
        createAccountPage.submitValidEmail(StringUtils.createValidEmail());
        createAccountPage.waitUntilCanSubmit();
        createAccountPage.createAccount(
                "",
                stringUtils.name().firstName(),
                stringUtils.gameOfThrones().house(),
                stringUtils.internet().password(5, 32),
                String.format("%s", (int) (Math.random() * 28) + 1),
                String.format("%s", (int) (Math.random() * 12) + 1),
                String.format("%s", (int) (Math.random() * 122) + 1900),
                true,
                true,
                stringUtils.space().agency(),
                stringUtils.address().fullAddress(),
                stringUtils.lordOfTheRings().location(),
                stringUtils.gameOfThrones().city(),
                1,
                ((int) (Math.random() * 53 + 1)),
                stringUtils.createString("#####"),
                stringUtils.createString("+###########"),
                "",
                stringUtils.starTrek().character()
        );
        List<WebElement> actualErrorList = createAccountPage.checkErrors();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Assert.assertEquals(actualErrorList.size(), 1);
        Assert.assertTrue(createAccountPage.findText(actualErrorList, "You must register at least one phone number"), "Error expected");
    }
}