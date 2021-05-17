package tests;

import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShopTest extends BaseTest {
    @Test(description = "Buying one item, paying by bank wire and confirming the order")
    @TmsLink("AP-9")
    public void buyOneItem() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        shopPage.goToShop();
        shopPage.addToCart("Blouse");
        shopPage.waitUntilCanBuy();
        shopPage.proceedToCheckout();
        cartPage.proceedToCheckout();
        addressesCheckoutPage.proceedToCheckout();
        shippingConfirmationPage.agreeToTos();
        shippingConfirmationPage.proceedToCheckout();
        paymentMethodSelectionPage.payByWire();
        paymentMethodSelectionPage.confirmOrder();
        Assert.assertEquals(paymentMethodSelectionPage.isOrderComplete(), "Your order on My Store is complete.");
    }

}
