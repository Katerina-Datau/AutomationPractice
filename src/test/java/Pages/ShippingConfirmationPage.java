package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShippingConfirmationPage extends BasePage {
    public ShippingConfirmationPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlShippingConfirmation = "http://automationpractice.com/index.php?controller=order";
    private static final By chbTermsOfServiceConfirmation = By.id("cgv");
    private static final By btnConfirmCarrier = By.name("processCarrier");
}
