import Pages.BasePage;
import Pages.LoginPage;
import Pages.ShopPageWomen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    WebDriver driver;
    LoginPage loginPage;
    ShopPageWomen shopPageWomen;
    BasePage.CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //TODO возможно, лучше вместо этого поставить explicitWait на отдельные релевантные элементы?
        //TODO Но тогда надо BeforeMethod на каждый тест вешать отдельно
        loginPage = new LoginPage(driver);
        shopPageWomen = new ShopPageWomen(driver);
        cartPage = new BasePage.CartPage(driver);
    }

    @AfterMethod(alwaysRun=true)
    public void closeBrowser() {
        driver.quit();
    }
    }



