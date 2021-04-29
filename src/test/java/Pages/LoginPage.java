package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final String LoginPageURL="http://automationpractice.com/index.php?controller=authentication&back=my-account";

    private static final By EmailAddressLocator=By.id("email");
    private static final By PasswordLocator=By.id("passwd");
    private static final By LoginButton=By.id("SubmitLogin");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        driver.get(LoginPageURL);
    }

    public void login (String emailAddress, String password) {
        driver.findElement(EmailAddressLocator).sendKeys(emailAddress);
        driver.findElement(PasswordLocator).sendKeys(password);
        driver.findElement(LoginButton).click();
    }


}
