package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final String urlLoginPage = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
    private static final String urlLoggedPage = "http://automationpractice.com/index.php?controller=my-account";

    private static final By txtEmail = By.id("email");
    private static final By txtPassword = By.id("passwd");
    private static final By btnLogin = By.id("SubmitLogin");
    private static final By btnForgotPassword = By.className("lost_password");

    private static final By btnLogOut = By.className("logout");
    private static final By btnLogIn = By.className("login");

    private static final By errLoginError = By.className("alert alert-danger").tagName("ol");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        driver.get(urlLoginPage);
    }

    public void login(String emailAddress, String password) {
        driver.findElement(txtEmail).sendKeys(emailAddress);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }
//TODO: no details, no email, no password, incorrect details, correct details
    //TODO: retrieve password: no email, incorrect email, correct email

}
