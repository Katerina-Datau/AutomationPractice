package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ShopWomenPage extends BasePage {

    //что за айди homefeatured? Выдается в составе xpath, если на странице магазина название продукта - ПКМ - copy xpath сделать

    public ShopWomenPage(WebDriver driver) {
        super(driver);
    }

    String productName;
    private static final String ShopPageWomenURL = "http://automationpractice.com/index.php?id_category=3&controller=category";

    //TODO не находит элементы по локатору!
    private static final String btnAddToCart = "//*[@id=\"center_column\"]/ul/../div/div[2]/div[2]/a[1]/span";

    private static final By btnContinueShopping = By.xpath("//*[@title='Continue shopping']");
    private static final By btnProceedToCheckout = By.xpath("//*[@title='Proceed to checkout']");

    public void openShopPage() {
        driver.get(ShopPageWomenURL);
    }




    public void addToCart(String ProductName) {


        Actions actions = new Actions(driver);

        WebElement locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + ProductName + "']"));

        //скролл вниз через Javascript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locAreaOfDetection);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        actions.moveToElement(locAreaOfDetection).build().perform();

        locAreaOfDetection = driver.findElement(By.cssSelector("[alt='" + ProductName + "']"));

        //тут всё плохо, выходит только отельно на каждый вид одежды:
         WebElement btnAddToCart = locAreaOfDetection.findElement(By.xpath("//*[@title='Blouse']/ancestor::li[1]//*[@title='Add to cart']"));
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



