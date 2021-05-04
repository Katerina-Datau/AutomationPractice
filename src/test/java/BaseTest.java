import Pages.BasePage;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.ShopWomenPage;
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

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //fullscreen:
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        shopWomenPage = new ShopWomenPage(driver);
        cartPage = new CartPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }
}



