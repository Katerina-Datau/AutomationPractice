package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShippingConfirmationPage extends BasePage {
    public ShippingConfirmationPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlShippingConfirmation = "http://automationpractice.com/index.php?controller=order";
    private static final By chTermsOfServiceConfirmation = By.id("cgv");
    private static final By btnConfirmCarrier = By.cssSelector("button[name='processCarrier']");
    //   name("processCarrier");

    @Step("Agreeing to Terms and conditions of service")
    public void agreeToTos() {
        driver.findElement(chTermsOfServiceConfirmation).click();
    }

    @Step("Proceeding to checkout")
    public void proceedToCheckout() {
        driver.findElement(btnConfirmCarrier).click();
    }

}