package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ShopWomenPage extends BasePage {

    public ShopWomenPage(WebDriver driver) {
        super(driver);
    }

    String productName;
    private static final String urlShopPageWomen = "http://automationpractice.com/index.php?id_category=3&controller=category";

    private static final By btnContinueShopping = By.xpath("//*[@title='Continue shopping']");
    private static final By btnProceedToCheckout = By.xpath("//*[@title='Proceed to checkout']");

    //? локатор!
    private static final By btnOpenCart = By.className("cart_block");



    //Для элемента Blouse:
    public static final By btnAddToWishlist = By.className("addToWishlist");

    //или wishlistProd_1

    public void openPage() {
        driver.get(urlShopPageWomen);
    }

    public void addToCart(String ProductName) {
        Actions actions = new Actions(driver);

        WebElement locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + ProductName + "']"));

        //скролл вниз через Javascript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locAreaOfDetection);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        actions.moveToElement(locAreaOfDetection).build().perform();

        locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + ProductName + "']"));

        //локатор по конкретному предмету одежды:
        WebElement btnAddToCart = locAreaOfDetection.findElement(By.xpath(String.format("//*[@title='%s']/ancestor::li[1]//*[@title='Add to cart']", ProductName)));
        btnAddToCart.click();
    }

    //String.valueOf(txtProductQuantity))).getText() -?
    public void continueShopping() {
        driver.findElement(btnContinueShopping).click();
    }

    public void proceedToCheckout() {
        driver.findElement(btnProceedToCheckout).click();
    }

}



