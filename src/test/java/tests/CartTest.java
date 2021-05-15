package tests;

import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    @Test(description = "Check names and prices of items in cart")
    @TmsLink("AP-8")
    public void cartCheckItems() {
        loginPage.openPage();
        loginPage.login("oberyn.martell@dorne.wst", "unbent111");
        shopWomenPage.goToShop();
        shopWomenPage.addToCart("Blouse");
        shopWomenPage.waitUntilCanBuy();
        shopWomenPage.continueShopping();
        shopWomenPage.addToCart("Printed Chiffon Dress");
        shopWomenPage.waitUntilCanBuy();
        shopWomenPage.proceedToCheckout();
        cartPage.waitUntilCanProceed();
        cartPage.validateItemsInCart("Blouse", "$27.00");
        cartPage.validateItemsInCart("Printed Chiffon Dress", "$16.40");
        cartPage.validateOrderDetails(2, "45.40");
    }

}