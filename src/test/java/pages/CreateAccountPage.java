package pages;

import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
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

    @FindBy(id = "firstname")
    WebElement txtAddressFirstName;

    @FindBy(id = "lastname")
    WebElement txtAddressLastName;

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
    //TODO генерить фейкеры в test (создать параметры в методах)

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

    public void waitUntilCanSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(btnRegisterButton));
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
    @Data
    @Builder
    @Value
    public void createAccount (){
        @Default
        private String gender = cbFemale.click();;
        @Default
        private String firstName = txtCustomerFirstName.sendKeys(firstName);
        @Default

        String lastName = txtCustomerLastName.sendKeys(lastName);
        @Default
        String password = txtCreatePassword.sendKeys(password);
        @Default
        String birthDay;
        @Default
        String birthMonth;
        @Default
        String birthYear;
        @Default
        boolean subscribe = false;
        @Default
        boolean getOffers = false;
        @Default
        String companyName;
        @Default String address1 = txtAddressLine1.sendKeys(address1);
        @Default
        String add


    }



    @Step("Creating an account")
    public void createAccount(

            String companyName, String address1, String address2, String city, int country,
            int state, String zip, String homePhone, String mobilePhone, String alias) {


        //TODO gender - rewrite!!!



        new Select(sddBirthDay).selectByValue(birthDay);
        new Select(sddBirthMonth).selectByValue(birthMonth);
        new Select(sddBirthYear).selectByValue(birthYear);


        if (subscribe) {
            cbNewsletterSignUp.click();
        }
        if (getOffers) {
            cbSpecialOffers.click();
        }


        txtAddressCompanyName.sendKeys(companyName);



        if (txtAddressLine2.isDisplayed()) {
            txtAddressLine2.sendKeys(address2);
        }
        txtAddressCity.sendKeys(city);
        new Select(sddAddressCountry).selectByIndex(country);
        waitUntilStateSelectable();
        Select stateOfUsa = new Select(sddAddressState);
        stateOfUsa.selectByIndex(state);
        txtAddressZip.sendKeys(zip);
        txtHomePhone.sendKeys(homePhone);
        txtMobilePhone.sendKeys(mobilePhone);
        btnRegisterButton.click();
        txtAddressAlias.sendKeys(alias);
    }

    //TODO something's wrong, account created but status is (false)? / test is flaky
    @Step("Checking if account has been created successfully")
    public boolean isAccountCreated() {
        return statusLoggedIn.isDisplayed();
    }

    //не факт,что так найдет; был List<WebElement> allErrors = driver.findElements(errCreateAccountError);
    //TODO also maybe optimize
    @Step("Checking errors")
    public List<WebElement> checkErrors() {
        return errCreateAccountError;
    }

}








