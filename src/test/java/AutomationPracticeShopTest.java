import org.testng.annotations.Test;

public class AutomationPracticeShopTest extends BaseTest {

    //TODO тест на регистрацию тоже сделать?
    //TODO тест на проверку правильности вычисления скидки?
    //TODO подключить рандомизатор имейла-пароля

    @Test
    public void simpleTest() {

       loginPage.openLoginPage();
        loginPage.login("camine.kerfi.5f@bookarest.site", "camine.kerfi.5f@bookarest.site");

        //shopPageWomen.browseShop();
        shopWomenPage.openShopPage();
        shopWomenPage.addToCart("Blouse");
        shopWomenPage.continueShopping();

        //cartPage.openCartPage();
        //cartPage.validateNumberOfItemsInCart(1);
       // cartPage.validateOrderDetails("Printed Summer Dress", 1, "$30.98");

    }


}
