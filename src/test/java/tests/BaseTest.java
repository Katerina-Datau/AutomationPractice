package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.*;
import utils.TestListener;

import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)

public class BaseTest {

    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    ShopPage shopPage;
    CartPage cartPage;
    CreateAccountPage createAccountPage;
    MyAccountPage myAccountPage;
    AddressesCheckoutPage addressesCheckoutPage;
    PaymentMethodSelectionPage paymentMethodSelectionPage;
    ShippingConfirmationPage shippingConfirmationPage;

    @BeforeMethod(description = "Open browser and maximize the window")
    public void setUp(ITestContext context) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        context.setAttribute("driver", driver);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        createAccountPage = new CreateAccountPage(driver);
        loginPage = new LoginPage(driver);
        shopPage = new ShopPage(driver);
        cartPage = new CartPage(driver);
        myAccountPage = new MyAccountPage(driver);
        addressesCheckoutPage = new AddressesCheckoutPage(driver);
        paymentMethodSelectionPage = new PaymentMethodSelectionPage(driver);
        shippingConfirmationPage = new ShippingConfirmationPage(driver);
    }

    @AfterMethod(alwaysRun = true, description = "Close browser and delete all cookies")
    public void closeBrowser() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

}


