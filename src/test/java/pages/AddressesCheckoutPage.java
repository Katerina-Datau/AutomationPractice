package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddressesCheckoutPage extends BasePage {
    public AddressesCheckoutPage(WebDriver driver) {
        super(driver);
    }

    private static final By btnConfirmAddress = By.name("processAddress");

    @Step("Confirming delivery address and proceeding to checkout")
    public void proceedToCheckout() {
        driver.findElement(btnConfirmAddress).click();
    }

    @Override
    public void openPage() {
    }
}