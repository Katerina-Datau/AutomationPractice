package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class ShippingConfirmationPage extends BasePage {
    public ShippingConfirmationPage(WebDriver driver) {
        super(driver);
    }

    private static final By chTermsOfServiceConfirmation = By.id("cgv");
    private static final By btnConfirmCarrier = By.cssSelector("button[name='processCarrier']");

    @Step("Agreeing to Terms and conditions of service")
    public void agreeToTos() {
        driver.findElement(chTermsOfServiceConfirmation).click();
    }

    @Step("Proceeding to checkout")
    public void proceedToCheckout() {
        driver.findElement(btnConfirmCarrier).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    public void openPage() {
    }

}