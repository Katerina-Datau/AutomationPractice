package Tests;

import Tests.BaseTest;
import org.testng.annotations.Test;

public class AutomationPracticeShopTest extends BaseTest {

    //TODO тест на проверку правильности вычисления скидки?
    //TODO подключить рандомизатор имейла-пароля

    //  @Test
    //public void authtest() {
    //     createAccountPage.openPage();
    //      createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
//
    //}


    @Test
    public void simpleTest() {

        loginPage.openPage();
        loginPage.login("camine.kerfi.5f@bookarest.site", "camine.kerfi.5f@bookarest.site");

        //shopPageWomen.browseShop();
        shopWomenPage.openShopPage();
        shopWomenPage.addToCart("Blouse");


        cartPage.openPage();
        //TODO здесь нужно перемотать страницу
        cartPage.validateNumberOfItemsInCart(1);
        cartPage.validateOrderDetails("Blouse", 1, "$29.00");

    }

    @Test
    public void arr() {
        loginPage.openPage();
        loginPage.login("camine.kerfi.5f@bookarest.site", "camine.kerfi.5f@bookarest.site");

        myAccountPage.openMyAccountPage();
        myAccountPage.acc();
    }

    //тест валидный имейл:
    //  @Test
    // public void dsj() {
    //      createAccountPage.openPage();
    //       createAccountPage.submitValidEmail(createAccountPage.createValidEmail());
    //      createAccountPage.createValidAccount();
    //       //assertequals
    //  }

    @Test
    public void bjh() {

        createAccountPage.createValidEmail();
    }


}
