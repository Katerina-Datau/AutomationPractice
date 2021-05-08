package Pages;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreateAccountPage extends BasePage {

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    //TODO подключить рандомные номера и имейлы со сторонних сайтов?

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
    //дропдауны через option value == PROFIT?
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
    //ZIP - 5 numbers
    private static final By txtAddressZip = By.id("postcode");
    private static final By sddAddressCountry = By.id("id_country");
    private static final By txtAdditionalInfo = By.id("other");
    private static final By txtHomePhone = By.id("phone");
    private static final By txtAddressAlias = By.id("alias");
    private static final By txtMobilePhone = By.id("phone_mobile");
    private static final By btnRegisterButton = By.id("submitAccount");

    //private static final By locAccountCreationForm = By.id("account-creation_form");

    //error locators:
    private static final By errCreateEmailError = By.xpath("//*[@id='create_account_error']/ol/li");
    private static final By errCreateAccountError = By.xpath("//*[@id='center_column']/div/ol/li");


    //status locators:
    private static final By statusLoggedIn = By.cssSelector("a[title='View my customer account'] span");

//TODO: no email, invalid email, existing email, incorrect ZIP


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

    //генератор правильного имейла:

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


    //1.@@
    //2. . в конце
    //3. без собаки и точки
    //4. неправильная раскладка
    //5. null email field

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

    //TODO nullaccount!!!
    public List<WebElement> createNullAccount() {
        WebElement toClear = driver.findElement(txtCreateEmail);
        //скролл вниз через Javascript Executor
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toClear);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", toClear);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Select(driver.findElement(sddAddressCountry)).selectByIndex(0);
        driver.findElement(txtAddressAlias).clear();
        driver.findElement(btnRegisterButton).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

    public boolean findError(List<WebElement> allErrors, String findText) {
        for (int i = 0; i < allErrors.size(); i++) {
            if (allErrors.get(i).getText().equals(findText)) {
                return true;
            }
        }
        return false;
    }


}