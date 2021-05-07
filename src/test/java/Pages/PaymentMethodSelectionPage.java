package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentMethodSelectionPage extends BasePage {
    public PaymentMethodSelectionPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlSelectPaymentMethod = "http://automationpractice.com/index.php?controller=order&multi-shipping=";
    private static final By btnPayByBankWire = By.className("bankwire");
    private static final By btnPayByCheck = By.className("cheque");
}
