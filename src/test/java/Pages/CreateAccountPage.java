package Pages;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreateAccountPage extends BasePage {

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlCreateAccount = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation";
    private static final By txtCreateByEmail = By.id("email_create");
    private static final By btnCreateAccount = By.id("SubmitCreate");
    private static final By cbMale = By.id("id_gender1");
    private static final By cbFemale = By.id("id_gender2");
    private static final By txtCustomerFirstName = By.id("customer_firstname");
    private static final By txtCustomerLastName = By.id("customer_lastname");
    private static final By txtCreateEmail = By.id("email");
    //private static final By txtCreateEmail = By.cssSelector("input#email");
    // - переносится из предыдущей стрраниицы, нужно ли?
    private static final By txtCreatePassword = By.id("passwd");
    private static final By sddBirthDay = By.id("days");
    private static final By sddBirthMonth = By.id("months");
    private static final By sddBirthYear = By.id("years");
    private static final By cbNewsletterSignUp = By.id("newsletter");
    private static final By cbSpecialOffers = By.id("optin");
    private static final By txtAddressFirstName = By.id("firstname");
    private static final By txtAddressLastName = By.id("lastname");
    private static final By txtAddressCompanyName = By.id("company");
    private static final By txtAddressLine1 = By.id("address1");
    private static final By txtAddressLine2 = By.id("address2");
    //скрытый локатор второго адреса!
    private static final By txtAddressCity = By.id("city");
    private static final By sddAddressState = By.id("id_state");
    private static final By txtAddressZip = By.id("postcode");
    private static final By sddAddressCountry = By.id("id_country");
    private static final By txtAdditionalInfo = By.id("other");
    private static final By txtHomePhone = By.id("phone");
    private static final By txtAddressAlias = By.id("alias");
    private static final By txtMobilePhone = By.id("phone_mobile");
    private static final By btnRegisterButton = By.id("submitAccount");

    /**
     * errors:
     */
    private static final By errCreateEmailError = By.xpath("//*[@id='create_account_error']/ol/li");
    private static final By errCreateAccountError = By.xpath("//*[@id='center_column']/div/ol/li");

    /**
     * status locators:
     */
    private static final By statusLoggedIn = By.cssSelector("a[title='View my customer account'] span");

    public void openPage() {
        driver.get(urlCreateAccount);
    }

    public boolean tryValidEmail(String emailAddress) {
        driver.findElement(txtCreateByEmail).sendKeys(emailAddress);
        driver.findElement(btnCreateAccount).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.findElement(cbMale).isDisplayed();
    }

    public String tryWrongEmail(String emailAddress) {
        driver.findElement(txtCreateByEmail).sendKeys(emailAddress);
        driver.findElement(btnCreateAccount).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.findElement(errCreateEmailError).getText();
    }

    /**
     * String generator:
     *
     * @param generatedString
     * @return
     */

    public String createString(String generatedString) {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify(generatedString);
    }

    public String createValidEmail() {
        return createString("????##@##gmail.com");
    }

    public String createInvalidEmailDoubleToad() {
        return createString("????##@@##gmail.com");
    }

    public String createInvalidEmailEndsWithDot() {
        return createString("?????##@##gmail.com.");
    }

    public String createInvalidEmailStartsWithDot() {
        return createString(".????##@##gmail.com");
    }

    public String createInvalidEmailNoSyntax() {
        return createString("????####?????");
    }

    public String createInvalidEmailForbiddenDomainSymbols(char symbol) {
        return createString("????##@") + symbol + createString("???.???");
    }

    public String emailAlreadyExists() {
        return "oberyn.martell@dorne.wst";
    }

    public void submitValidEmail(String createValidEmail) {
        driver.findElement(txtCreateByEmail).sendKeys(createValidEmail);
        driver.findElement(btnCreateAccount).click();
    }

    public void waitUntilCanSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(btnRegisterButton));
    }

    public boolean createValidAccountOnlyRequiredFields() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        return driver.findElement(statusLoggedIn).isDisplayed();
    }

    public boolean createValidAccountAllTheFields() {
        Faker faker = new Faker();
        driver.findElement(cbFemale).click();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        new Select(driver.findElement(sddBirthDay)).selectByValue(String.format("%s", (int) (Math.random() * 28) + 1));
        new Select(driver.findElement(sddBirthMonth)).selectByValue(String.format("%s", (int) (Math.random() * 12) + 1));
        new Select(driver.findElement(sddBirthYear)).selectByValue(String.format("%s", (int) (Math.random() * 122) + 1900));
        driver.findElement(cbNewsletterSignUp).click();
        driver.findElement(cbSpecialOffers).click();
        driver.findElement(txtAddressCompanyName).sendKeys(faker.space().agency());
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        WebElement address2 = driver.findElement(txtAddressLine2);
        if (address2.isDisplayed()) {
            address2.sendKeys(faker.lordOfTheRings().location());
        }
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtHomePhone).sendKeys(createString("+###########"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        return driver.findElement(statusLoggedIn).isDisplayed();
    }

    public List<WebElement> createNullAccount() {
        driver.findElement(txtCreateEmail).clear();
        new Select(driver.findElement(sddAddressCountry)).selectByIndex(0);
        driver.findElement(txtAddressAlias).clear();
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> numericalNames() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(createString("#######"));
        driver.findElement(txtCustomerLastName).sendKeys(createString("#######"));
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> forbiddenSymbolNames() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys("#%orge");
        driver.findElement(txtCustomerLastName).sendKeys("#%@!$()son");
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> namesOverLimit() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(createString("???????????????????????????????????"));
        driver.findElement(txtCustomerLastName).sendKeys(createString("???????????????????????????????????"));
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> shortPassword() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(createString("#?#"));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> invalidDate() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        new Select(driver.findElement(sddBirthDay)).selectByValue("31");
        new Select(driver.findElement(sddBirthMonth)).selectByValue("2");
        new Select(driver.findElement(sddBirthYear)).selectByValue("1980");
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> nullYear() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        new Select(driver.findElement(sddBirthDay)).selectByValue(String.format("%s", (int) (Math.random() * 28) + 1));
        new Select(driver.findElement(sddBirthMonth)).selectByValue(String.format("%s", (int) (Math.random() * 12) + 1));
        new Select(driver.findElement(sddBirthYear)).selectByIndex(0);
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> cityOverLimit() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(createString("##################################################################################################################################################################"));
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> cityForbidden() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys("$ @ # % = +");
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> noState() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        new Select(driver.findElement(sddAddressState)).selectByIndex(0);
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> wrongZip() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####??"));
        driver.findElement(txtMobilePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> alphabetPhone() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(createString("###########"));
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> cellOverLimit() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtMobilePhone).sendKeys(createString("######################################"));
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public List<WebElement> isCellRequired() {
        Faker faker = new Faker();
        driver.findElement(txtCustomerFirstName).sendKeys(faker.name().firstName());
        driver.findElement(txtCustomerLastName).sendKeys(faker.gameOfThrones().house());
        driver.findElement(txtCreatePassword).sendKeys(faker.internet().password(5, 32));
        driver.findElement(txtAddressLine1).sendKeys(faker.address().fullAddress());
        driver.findElement(txtAddressCity).sendKeys(faker.gameOfThrones().city());
        new Select(driver.findElement(sddAddressCountry)).selectByValue("21");
        Select state = new Select(driver.findElement(sddAddressState));
        state.selectByIndex((int) (Math.random() * 53 + 1));
        driver.findElement(txtAddressZip).sendKeys(createString("#####"));
        driver.findElement(txtHomePhone).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }
}










