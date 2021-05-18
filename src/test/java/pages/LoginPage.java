package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {
    private static final String urlLoginPage = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
    private static final String urlLoggedPage = "http://automationpractice.com/index.php?controller=my-account";

    private static final By txtEmail = By.id("email");
    private static final By txtPassword = By.id("passwd");
    private static final By btnLogin = By.id("SubmitLogin");
    private static final By btnForgotPassword = By.cssSelector(".lost_password a");
    private static final By btnRetrievePassword = By.xpath("//*[@id=\"form_forgotpassword\"]/fieldset/p/button");

    private static final By btnLogOut = By.className("logout");
    private static final By btnLogIn = By.className("login");

    /**
     * status locators:
     */
    private static final By statusLoggedIn = By.cssSelector("a[title='View my customer account'] span");
    private static final By statusPasswordRetrieved = By.cssSelector(".alert-success");

    /**
     * errors:
     */
    private static final By errLoginError = By.cssSelector(".alert-danger ol");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening Log In page")
    public void openPage() {
        driver.get(urlLoginPage);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Step("Submitting credentials: email '{emailAddress}' and password '{password}'")
    public void login(String emailAddress, String password) {
        driver.findElement(txtEmail).sendKeys(emailAddress);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Step("Checking if login attempt has been successful")
    public boolean ifLoggedIn() {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return driver.findElement(statusLoggedIn).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Checking which login page error is displayed")
    public List<WebElement> checkError() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        return driver.findElements(errLoginError);
    }

    @Step("Trying to retrieve forgotten password for '{emailAddress}'")
    public void passwordRetrieval(String email) {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(btnForgotPassword).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(txtEmail).sendKeys(email);
        driver.findElement(btnRetrievePassword).click();
    }

    @Step("Checking if password retrieval has been successful")
    public boolean ifRetrieved() {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return driver.findElement(statusPasswordRetrieved).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Logging out")
    public void logOut() {
        driver.findElement(btnLogOut).click();
    }

}