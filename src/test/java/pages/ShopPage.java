package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ShopPage extends BasePage {

    public static final By btnAddToWishlist = By.className("addToWishlist");
    private static final String urlShopPageWomen = "http://automationpractice.com/index.php?id_category=3&controller=category";
    private static final By btnShopWomen = By.cssSelector("li a[title='Women']");
    private static final By btnContinueShopping = By.xpath("//*[@title='Continue shopping']");
    private static final By btnProceedToCheckout = By.xpath("//*[@title='Proceed to checkout']");
    private static final By btnOpenCart = By.className("cart_block");

    public ShopPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening the 'Women' Shop category")
    public void openPage() {
        driver.get(urlShopPageWomen);
    }

    @Step("Opening the 'Women' Shop category")
    public void goToShop() {
        driver.findElement(btnShopWomen).click();
    }

    public void waitUntilCanBuy() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(btnContinueShopping));
    }

    @Step("Looking for a {productName} in Shop and adding it to the shopping cart")
    public void addToCart(String productName) {
        Actions actions = new Actions(driver);
        WebElement locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + productName + "']"));
        //скролл вниз через Javascript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locAreaOfDetection);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        actions.moveToElement(locAreaOfDetection).build().perform();
        locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + productName + "']"));
        //локатор по конкретному предмету одежды:
        WebElement btnAddToCart = locAreaOfDetection.findElement(By.xpath(String.format("//*[@title='%s']/ancestor::li[1]//*[@title='Add to cart']", productName)));
        btnAddToCart.click();
    }

    @Step("Continuing shopping")
    public void continueShopping() {
        driver.findElement(btnContinueShopping).click();
    }

    @Step("Proceeding to shopping cart")
    public void proceedToCheckout() {
        driver.findElement(btnProceedToCheckout).click();
    }

}


