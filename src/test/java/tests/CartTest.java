package tests;

import io.qameta.allure.TmsLink;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j2(topic = "cart test log")
public class CartTest extends BaseTest {
    @Test(description = "Check names and prices of items in cart")
    @TmsLink("AP-8")
    public void cartCheckItems() {
        try {
            loginPage.openPage();
            loginPage.login("oberyn.martell@dorne.wst", "unbent111");
            shopPage.goToShop();
            shopPage.addToCart("Blouse");
            shopPage.waitUntilCanBuy();
            shopPage.continueShopping();
            shopPage.addToCart("Printed Chiffon Dress");
            shopPage.waitUntilCanBuy();
            shopPage.proceedToCheckout();
            cartPage.waitUntilCanProceed();
            cartPage.validateItemsInCart("Blouse", "$27.00");
            cartPage.validateItemsInCart("Printed Chiffon Dress", "$16.40");
            cartPage.validateOrderDetails(2, "45.40");
            log.info("Test \"Check names and prices of items in cart\"  has been completed successfully");
        } catch (Exception e) {
            log.error("An exception has occured", e.getMessage());
            Assert.fail();
        }
    }
}