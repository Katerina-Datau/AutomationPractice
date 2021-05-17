package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentMethodSelectionPage extends BasePage {
    public PaymentMethodSelectionPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlSelectPaymentMethod = "http://automationpractice.com/index.php?controller=order&multi-shipping=";
    private static final String urlPayByWire = "http://automationpractice.com/index.php?fc=module&module=bankwire&controller=payment";
    private static final String urlPayByCheck = "http://automationpractice.com/index.php?fc=module&module=cheque&controller=payment";

    private static final By btnPayByBankWire = By.className("bankwire");
    private static final By btnPayByCheck = By.className("cheque");

    private static final By btnOrderConfirmation = By.xpath("//*[@id=\"cart_navigation\"]/button");
    private static final By btnOtherPaymentMethod = By.xpath("//*[@id=\"cart_navigation\"]/a");
    private static final By txtTotalPrice = By.id("amount");
    private static final By txtCurrency = By.className("currency_payement");

    /**
     * Status locator
     */
    private static final By orderComplete = By.cssSelector("div.box p.cheque-indent strong");

    @Step("Choosing wire transfer payment method")
    public void payByWire() {
        driver.findElement(btnPayByBankWire).click();
    }

    @Step("Confirming the order")
    public void confirmOrder() {
        driver.findElement(btnOrderConfirmation).click();
    }

    @Step("Checking if order has been completed successfully")
    public String isOrderComplete() {
        return driver.findElement(orderComplete).getText();
    }

}