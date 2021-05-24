package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import model.Account;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2(topic = "creating account test log")
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
    private static final By txtCreatePassword = By.id("passwd");
    private static final By sddBirthDay = By.id("days");
    private static final By sddBirthMonth = By.id("months");
    private static final By sddBirthYear = By.id("years");
    private static final By cbNewsletterSignUp = By.id("newsletter");
    private static final By cbSpecialOffers = By.id("optin");
    private static final By txtAddressCompanyName = By.id("company");
    private static final By txtAddressLine1 = By.id("address1");
    private static final By txtAddressLine2 = By.id("address2");
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

    public void submitEmail(String emailAddress) {
        driver.findElement(txtCreateByEmail).sendKeys(emailAddress);
        driver.findElement(btnCreateAccount).click();
    }

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

    public void waitUntilStateSelectable() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(sddAddressStateOption)));
    }

    @Step("Submitting a valid email")
    public void submitValidEmail(String createValidEmail) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(txtCreateByEmail).sendKeys(createValidEmail);
        driver.findElement(btnCreateAccount).click();
    }

    @Step("Creating an account")
    public void createAccount(Account account) {
        try {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            switch (account.getGender()) {
                case MR:
                    driver.findElement(cbMale).click();
                    break;
                case MRS:
                    driver.findElement(cbFemale).click();
                    break;
            }
            driver.findElement(txtCustomerFirstName).sendKeys(account.getFirstName());
            driver.findElement(txtCustomerLastName).sendKeys(account.getLastName());
            driver.findElement(txtCreatePassword).sendKeys(account.getPassword());
            new Select(driver.findElement(sddBirthDay)).selectByValue(account.getBirthDay());
            new Select(driver.findElement(sddBirthMonth)).selectByValue(account.getBirthMonth());
            new Select(driver.findElement(sddBirthYear)).selectByValue(account.getBirthYear());
            if (account.isSubscribe()) {
                driver.findElement(cbNewsletterSignUp).click();
            }
            if (account.isGetOffers()) {
                driver.findElement(cbSpecialOffers).click();
            }
            driver.findElement(txtAddressCompanyName).sendKeys(account.getCompanyName());
            driver.findElement(txtAddressLine1).sendKeys(account.getAddress1());
            if (driver.findElement(txtAddressLine2).isDisplayed()) {
                driver.findElement(txtAddressLine2).sendKeys(account.getAddress2());
            }
            driver.findElement(txtAddressCity).sendKeys(account.getCity());
            new Select(driver.findElement(sddAddressCountry)).selectByValue(account.getCountry());
            if (!account.getCountry().equals("")) {
                waitUntilStateSelectable();
                new Select(driver.findElement(sddAddressState)).selectByIndex(account.getState());
                driver.findElement(txtAddressZip).sendKeys(account.getZip());
            }
            driver.findElement(txtAdditionalInfo).sendKeys(account.getOther());
            driver.findElement(txtHomePhone).sendKeys(account.getHomePhone());
            driver.findElement(txtMobilePhone).sendKeys(account.getMobilePhone());
            driver.findElement(txtAddressAlias).sendKeys(account.getAlias());
            driver.findElement(btnRegisterButton).click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("It happened again", e);
        }
    }

    @Step("Clearing all data automatically filled in by the system")
    public void clearAll() {
        driver.findElement(btnRegisterButton).isDisplayed();
        driver.findElement(txtCreateEmail).clear();
        new Select(driver.findElement(sddAddressCountry)).selectByIndex(0);
        driver.findElement(txtAddressAlias).clear();
    }

    @Step("Checking if account has been created successfully")
    public boolean isAccountCreated() {
        return driver.findElement(statusLoggedIn).isDisplayed();
    }

    @Step("Checking errors")
    public List<WebElement> checkErrors() {
        return driver.findElements(errCreateAccountError);
    }
}








