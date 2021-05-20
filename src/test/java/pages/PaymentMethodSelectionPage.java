package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentMethodSelectionPage extends BasePage {
    public PaymentMethodSelectionPage(WebDriver driver) {
        super(driver);
    }

    private static final By btnPayByBankWire = By.className("bankwire");
    private static final By btnOrderConfirmation = By.xpath("//*[@id=\"cart_navigation\"]/button");

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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderComplete));
        return driver.findElement(orderComplete).getText();
    }

    @Override
    public void openPage() {
    }
}