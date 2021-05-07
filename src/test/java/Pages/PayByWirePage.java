package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PayByWirePage extends BasePage {
    public PayByWirePage(WebDriver driver) {
        super(driver);
    }

    private static final String urlPayByWire = "http://automationpractice.com/index.php?fc=module&module=bankwire&controller=payment";

    private static final By btnOrderConfirmation = By.xpath("//*[@id=\"cart_navigation\"]/button");
    private static final By btnOtherPaymentMethod = By.xpath("//*[@id=\"cart_navigation\"]/a");
    private static final By txtTotalPrice = By.id("amount");
    private static final By txtCurrency = By.className("currency_payement");
}
