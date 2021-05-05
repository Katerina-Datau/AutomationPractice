import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    WebDriver driver;
    LoginPage loginPage;
    ShopWomenPage shopWomenPage;
    CartPage cartPage;
    CreateAccountPage createAccountPage;
    MyAccountPage myAccountPage;
    AddressesCheckoutPage addressesCheckoutPage;
    PaymentMethodSelectionPage paymentMethodSelectionPage;
    PayByWirePage payByWirePage;
    PayByCheckPage payByCheckPage;
    ShippingConfirmationPage shippingConfirmationPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //fullscreen:
        driver.manage().window().maximize();

        createAccountPage = new CreateAccountPage(driver);
        loginPage = new LoginPage(driver);
        shopWomenPage = new ShopWomenPage(driver);
        cartPage = new CartPage(driver);
        myAccountPage = new MyAccountPage(driver);
        addressesCheckoutPage = new AddressesCheckoutPage(driver);
        paymentMethodSelectionPage = new PaymentMethodSelectionPage(driver);
        payByWirePage = new PayByWirePage(driver);
        payByCheckPage = new PayByCheckPage(driver);
        shippingConfirmationPage = new ShippingConfirmationPage(driver);

    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }
}



