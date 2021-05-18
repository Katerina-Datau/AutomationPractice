package tests;

import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ShopTest extends BaseTest {
    @Test(description = "Buying one item, paying by bank wire and confirming the order")
    @TmsLink("AP-9")
    public void buyOneItem() {
        loginPage.openPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        shopPage.goToShop();
        shopPage.addToCart("Blouse");
        shopPage.waitUntilCanBuy();
        shopPage.proceedToCheckout();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        cartPage.proceedToCheckout();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        addressesCheckoutPage.proceedToCheckout();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        shippingConfirmationPage.agreeToTos();
        shippingConfirmationPage.proceedToCheckout();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        paymentMethodSelectionPage.payByWire();
        paymentMethodSelectionPage.confirmOrder();
        Assert.assertEquals(paymentMethodSelectionPage.isOrderComplete(), "Your order on My Store is complete.");
    }

}
