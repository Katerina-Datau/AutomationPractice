package tests;

import io.qameta.allure.TmsLink;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Log4j2(topic = "shop test log")
public class ShopTest extends BaseTest {
    @Test(description = "Buying one item, paying by bank wire and confirming the order")
    @TmsLink("AP-9")
    public void buyOneItem() {
        try {
            loginPage.openPage();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
            Assert.assertEquals(paymentMethodSelectionPage.isOrderComplete(),
                    "Your order on My Store is complete.",
                    "Something went wrong");
            log.info("Test \"Buying one item, paying by bank wire and confirming the order\" has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occurred" + e.getMessage());
            Assert.fail();
        }
    }
}
