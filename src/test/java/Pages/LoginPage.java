package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final String LoginPageURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

    private static final By txtEmail = By.id("email");
    private static final By txtPassword = By.id("passwd");
    private static final By btnLogin = By.id("SubmitLogin");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        driver.get(LoginPageURL);
    }

    public void login(String emailAddress, String password) {
        driver.findElement(txtEmail).sendKeys(emailAddress);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }


}
