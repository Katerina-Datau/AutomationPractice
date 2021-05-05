package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

//TODO WebDriverManager.chromedriver().setup - добавить бонигарсию, удалить из ресурсов хромдрайвер

public class CartPage extends BasePage {


    private static final String cartPageURL = "http://automationpractice.com/index.php?controller=order";

    private static final By txtProductQuantity = By.id("summary_products_quantity");
    private static final By txtPriceSansShipping = By.id("total_product");
    private static final By txtShippingCost = By.id("total_shipping");
    private static final By txtTotalPrice = By.id("total_price");
    private static final By txtItemInCart = By.className("cart_item");

    //TODO цены товаров, +/- количество - на конкретные товары в тестах делать надо?
    //id начинается на total_product_price:
    //private static final By txtIndividualItemInCartPrice = By.cssSelector("*[contains(@id,'total_product_price_')]");

    private static final By btnProceedToCheckout = By.className("standard-checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        driver.get(cartPageURL);
    }

    public void validateNumberOfItemsInCart(int itemsInCart) {
        Assert.assertEquals(driver.findElements(txtItemInCart).size(), itemsInCart, "Item quantity doesn't match");
    }

    public void validateOrderDetails(String productName, int quantity, String price) {
        String currentQuantity = driver.findElement(By.id(String.valueOf(txtProductQuantity))).getText();
        Assert.assertEquals(currentQuantity, String.valueOf(quantity), "Product quantity doesn't match");

        String currentTotalPrice = driver.findElement(By.id(String.valueOf(txtTotalPrice))).getText();
        Assert.assertEquals(currentTotalPrice, String.valueOf(price), "Total price doesn't match");


    }
}
