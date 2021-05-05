package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddressesCheckoutPage extends BasePage {
    public AddressesCheckoutPage(WebDriver driver) {
        super(driver);
    }

    private static final String shippingAddresses = "http://automationpractice.com/index.php?controller=order&step=1&multi-shipping=0";
    private static final By btnConfirmAddress = By.name("processAddress");

}
