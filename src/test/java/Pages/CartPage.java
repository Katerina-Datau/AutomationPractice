package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

//TODO WebDriverManager.chromedriver().setup - добавить бонигарсию, удалить из ресурсов хромдрайвер

public class CartPage extends BasePage {
    private static final String CartPageURL = "http://automationpractice.com/index.php?controller=order";
    private static final By ProductQuantityLocator = By.id("summary_products_quantity");
    private static final By PriceLocatorSansShipping = By.id("total_product");
    private static final By ShippingCostLocator = By.id("total_shipping");
    private static final By TotalPriceLocator = By.id("total_price");
    private static final By ItemInCartLocator = By.className("cart_item");
    private static final By IndividualItemInCartPrice = By.cssSelector("*[contains(@id,'total_product_price_')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void openCartPage() {
        this.driver.get("http://automationpractice.com/index.php?controller=order");
    }

    public void validateNumberOfItemsInCart(int ItemsInCart) {
        Assert.assertEquals(this.driver.findElements(ItemInCartLocator).size(), ItemsInCart, "Item quantity doesn't match");
    }

    public void validateOrderDetails(String productName, int quantity, String price) {
        String currentQuantity = this.driver.findElement(By.id(String.valueOf(ProductQuantityLocator))).getText();
        Assert.assertEquals(currentQuantity, String.valueOf(quantity), "Product quantity doesn't match");
        String currentTotalPrice = this.driver.findElement(By.id(String.valueOf(TotalPriceLocator))).getText();
        Assert.assertEquals(currentTotalPrice, String.valueOf(price), "Total price doesn't match");
    }
}
