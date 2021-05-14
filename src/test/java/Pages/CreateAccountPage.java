package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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
    private static final By sddAddressStateOption = By.cssSelector("#id_state option");
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

    @Step("Opening account creation page")
    public void openPage() {
        driver.get(urlCreateAccount);
    }

    //TODO StringUtils, tryEmail туда
    //TODO генерить фейкеры в test (создать параметры в методах)

    @Step("Putting a valid email '{emailAddress}' into email field")
    public boolean tryValidEmail(String emailAddress) {
        submitEmail(emailAddress);
        return driver.findElement(cbMale).isDisplayed();
    }

    @Step("Putting an invalid email '{emailAddress}' into email field")
    public String tryWrongEmail(String emailAddress) {
        submitEmail(emailAddress);
        return driver.findElement(errCreateEmailError).getText();
    }

    @Step("Putting an email '{}' into field")
    public void submitEmail(String emailAddress) {
        driver.findElement(txtCreateByEmail).sendKeys(emailAddress);
        driver.findElement(btnCreateAccount).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void waitUntilCanSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(btnRegisterButton));
    }

    public void waitUntilStateSelectable() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sddAddressStateOption));
    }

    @Step("Submitting a valid email")
    public void submitValidEmail(String createValidEmail) {
        driver.findElement(txtCreateByEmail).sendKeys(createValidEmail);
        driver.findElement(btnCreateAccount).click();
    }

    @Step("Creating an account")
    public void createAccount(String gender, String firstName, String lastName, String password,
                              String birthDay, String birthMonth, String birthYear,
                              boolean subscribe, boolean getOffers,
                              String companyName, String address1, String address2, String city, int country,
                              int state, String zip, String homePhone, String mobilePhone, String alias) {

        driver.findElement(cbFemale).click();
        driver.findElement(txtCustomerFirstName).sendKeys(firstName);
        driver.findElement(txtCustomerLastName).sendKeys(lastName);
        driver.findElement(txtCreatePassword).sendKeys(password);
        new Select(driver.findElement(sddBirthDay)).selectByValue(birthDay);
        new Select(driver.findElement(sddBirthMonth)).selectByValue(birthMonth);
        new Select(driver.findElement(sddBirthYear)).selectByValue(birthYear);
        if (subscribe) {
            driver.findElement(cbNewsletterSignUp).click();
        }
        if (getOffers) {
            driver.findElement(cbSpecialOffers).click();
        }
        driver.findElement(txtAddressCompanyName).sendKeys(companyName);
        driver.findElement(txtAddressLine1).sendKeys(address1);
        WebElement addressLine2 = driver.findElement(txtAddressLine2);
        if (addressLine2.isDisplayed()) {
            addressLine2.sendKeys(address2);
        }
        driver.findElement(txtAddressCity).sendKeys(city);
        new Select(driver.findElement(sddAddressCountry)).selectByIndex(country);
        waitUntilStateSelectable();
        Select stateOfUsa = new Select(driver.findElement(sddAddressState));
        stateOfUsa.selectByIndex(state);
        driver.findElement(txtAddressZip).sendKeys(zip);
        driver.findElement(txtHomePhone).sendKeys(homePhone);
        driver.findElement(txtMobilePhone).sendKeys(mobilePhone);
        driver.findElement(btnRegisterButton).click();
        driver.findElement(txtAddressAlias).sendKeys(alias);
    }

    //TODO something's wrong, account created but status is (false)?
    @Step("Checking if account has been created successfully")
    public boolean isAccountCreated() {
        return driver.findElement(statusLoggedIn).isDisplayed();
    }

    @Step("Checking errors")
    public List<WebElement> checkErrors() {
        List<WebElement> allErrors = driver.findElements(errCreateAccountError);
        return allErrors;
    }

}










