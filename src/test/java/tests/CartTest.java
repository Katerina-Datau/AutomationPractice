package tests;

import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    @Test(description = "Check names and prices of items in cart")
    @TmsLink("AP-8")
    public void cartCheckItems() {
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
    }
}