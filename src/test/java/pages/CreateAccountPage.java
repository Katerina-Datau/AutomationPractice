package pages;

import io.qameta.allure.Step;
import model.Account;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    @FindBy(id = "email_create")
    WebElement txtCreateByEmail;

    @FindBy(id = "SubmitCreate")
    WebElement btnCreateAccount;

    @FindBy(id = "id_gender1")
    WebElement cbMale;

    @FindBy(id = "id_gender2")
    WebElement cbFemale;

    @FindBy(id = "customer_firstname")
    WebElement txtCustomerFirstName;

    @FindBy(id = "customer_lastname")
    WebElement txtCustomerLastName;

    @FindBy(id = "email")
    WebElement txtCreateEmail;

    @FindBy(id = "passwd")
    WebElement txtCreatePassword;

    @FindBy(id = "days")
    WebElement sddBirthDay;

    @FindBy(id = "months")
    WebElement sddBirthMonth;

    @FindBy(id = "years")
    WebElement sddBirthYear;

    @FindBy(id = "newsletter")
    WebElement cbNewsletterSignUp;

    @FindBy(id = "optin")
    WebElement cbSpecialOffers;

    @FindBy(id = "company")
    WebElement txtAddressCompanyName;

    @FindBy(id = "address1")
    WebElement txtAddressLine1;

    @FindBy(id = "address2")
    WebElement txtAddressLine2;

    @FindBy(id = "city")
    WebElement txtAddressCity;

    @FindBy(id = "id_state")
    WebElement sddAddressState;

    @FindBy(css = "#id_state option")
    WebElement sddAddressStateOption;

    @FindBy(id = "postcode")
    WebElement txtAddressZip;

    @FindBy(id = "id_country")
    WebElement sddAddressCountry;

    @FindBy(id = "other")
    WebElement txtAdditionalInfo;

    @FindBy(id = "phone")
    WebElement txtHomePhone;

    @FindBy(id = "alias")
    WebElement txtAddressAlias;

    @FindBy(id = "phone_mobile")
    WebElement txtMobilePhone;

    @FindBy(id = "submitAccount")
    WebElement btnRegisterButton;

    /**
     * errors:
     */
    @FindBy(xpath = "//*[@id='create_account_error']/ol/li")
    WebElement errCreateEmailError;

    @FindBy(xpath = "//*[@id='center_column']/div/ol/li")
    List<WebElement> errCreateAccountError;

    /**
     * status locators:
     */
    @FindBy(css = "a[title='View my customer account'] span")
    WebElement statusLoggedIn;


    @Step("Opening account creation page")
    public void openPage() {
        driver.get(urlCreateAccount);
        PageFactory.initElements(driver, this);
    }

    //TODO StringUtils, tryEmail туда

    @Step("Putting a valid email '{emailAddress}' into email field")
    public boolean tryValidEmail(String emailAddress) {
        submitEmail(emailAddress);
        return cbMale.isDisplayed();
    }

    @Step("Putting an invalid email '{emailAddress}' into email field")
    public String tryWrongEmail(String emailAddress) {
        submitEmail(emailAddress);
        return errCreateEmailError.getText();
    }

    @Step("Putting an email '{}' into field")
    public void submitEmail(String emailAddress) {
        txtCreateByEmail.sendKeys(emailAddress);
        btnCreateAccount.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void waitUntilStateSelectable() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(sddAddressStateOption));
    }

    @Step("Submitting a valid email")
    public void submitValidEmail(String createValidEmail) {
        txtCreateByEmail.sendKeys(createValidEmail);
        btnCreateAccount.click();
    }

    @Step("Creating an account")
    public void createAccount(Account account) {
        switch (account.getGender()) {
            case Mr:
                cbMale.click();
                break;
            case Mrs:
                cbFemale.click();
                break;
        }
        txtCustomerFirstName.sendKeys(account.getFirstName());
        txtCustomerLastName.sendKeys(account.getLastName());
        txtCreatePassword.sendKeys(account.getPassword());
        new Select(sddBirthDay).selectByValue(account.getBirthDay());
        new Select(sddBirthMonth).selectByValue(account.getBirthMonth());
        new Select(sddBirthYear).selectByValue(account.getBirthYear());
        if (account.isSubscribe()) {
            cbNewsletterSignUp.click();
        }
        if (account.isGetOffers()) {
            cbSpecialOffers.click();
        }
        txtAddressCompanyName.sendKeys(account.getCompanyName());
        txtAddressLine1.sendKeys(account.getAddress1());
        if (txtAddressLine2.isDisplayed()) {
            txtAddressLine2.sendKeys(account.getAddress2());
        }
        txtAddressCity.sendKeys(account.getCity());
        new Select(sddAddressCountry).selectByValue(account.getCountry());
        if (!account.getCountry().equals("")) {
            waitUntilStateSelectable();
            new Select(sddAddressState).selectByIndex(account.getState());
            txtAddressZip.sendKeys(account.getZip());
        }
        txtHomePhone.sendKeys(account.getHomePhone());
        txtMobilePhone.sendKeys(account.getMobilePhone());
        txtAddressAlias.sendKeys(account.getAlias());
        btnRegisterButton.click();
    }

    public void clearAll() {
        btnRegisterButton.isDisplayed();
        txtCreateEmail.clear();
        new Select(sddAddressCountry).selectByIndex(0);
        txtAddressAlias.clear();
    }

    @Step("Checking if account has been created successfully")
    public boolean isAccountCreated() {
        return statusLoggedIn.isDisplayed();
    }

    //TODO also maybe optimize
    @Step("Checking errors")
    public List<WebElement> checkErrors() {
        return errCreateAccountError;
    }

}








