package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.PortUnreachableException;
import java.util.Random;

public class CreateAccountPage extends BasePage {

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    //TODO подключить рандомные номера и имейлы со сторонних сайтов?
    private static final String createAccountURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
    private static final String createAccountDirectURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation";
    private static final By txtCreateByEmail = By.id("email_create");
    private static final By btnCreateAccount = By.id("SubmitCreate");
    private static final By chbMale = By.id("id_gender1");
    private static final By chbFemale = By.id("id_gender2");
    private static final By txtCustomerLastName = By.id("customer_lastname");
    private static final By txtCreateEmail = By.id("email");
    // - переносится из предыдущей стрраниицы, нужно ли?
    private static final By txtCreatePassword = By.id("passwd");
    //дропдауны через option value == PROFIT?
    private static final By sddBirthDay = By.id("days");
    private static final By sddBirtMonth = By.id("months");
    private static final By sddBirthYear = By.id("years");
    private static final By chbNewsletterSignUp = By.id("newsletter");
    private static final By chbSpecialOffers = By.id("optin");
    private static final By txtAddressFirstName = By.id("firstname");
    private static final By txtAddressLastName = By.id("lastname");
    private static final By txtAddressCompanyName = By.id("company");
    private static final By txtAddressLine1 = By.id("address1");
    private static final By txtAddressLine2 = By.id("address2");
    private static final By txtAddressCity = By.id("city");
    private static final By sddAddressState = By.id("id_state");
    //ZIP - 5 numbers
    private static final By txtAddressZip = By.id("postcode");
    private static final By sddAddressCountry = By.id("id_country");
    private static final By txtAdditionalInfo = By.id("other");
    private static final By txtHomePhone = By.id("phone");
    private static final By txtAddressAlias = By.id("alias");
    private static final By txtHomeMobile = By.id("phone_mobile");
    private static final By btnRegisterButton = By.id("submitAccount");

//TODO: no email, invalid email, existing email, incorrect ZIP


    public void openPage() {
        driver.get(createAccountURL);
    }

    //рандомная строка из количества букв:
    public String createString (int length) {
        StringBuilder bldrSymbols = new StringBuilder();
        for (int i = 0, n=length; i < n; i++) {
            bldrSymbols.append('a'+Math.random()*26);
        }
        return bldrSymbols.toString();
    }

    //1-5 букв+@+1-5 букв+.+1-5 букв
    public String createValidEmail () {
        StringBuilder bldrValidEmail = new StringBuilder();
        Random rndValidEmail = new Random();
        bldrValidEmail.append(createString(rndValidEmail.nextInt(5)+1));
        bldrValidEmail.append('@');
        bldrValidEmail.append(createString(rndValidEmail.nextInt(5)+1));
        bldrValidEmail.append('.');
        bldrValidEmail.append(createString(rndValidEmail.nextInt(5)+1));
        return bldrValidEmail.toString();
    }


    //TODO String createinvalidemail

public void submitValidEmail (String createValidEmail) {
        driver.findElement(txtCreateByEmail).sendKeys(createValidEmail);
        driver.findElement(btnCreateAccount).click();
    }
    //TODO c инвалидными имейлами







}