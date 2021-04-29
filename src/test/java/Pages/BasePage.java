package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver=driver;
    }


    public static class CartPage extends BasePage {


    private static final String CartPageURL = "http://automationpractice.com/index.php?controller=order";

        private static final By ProductQuantityLocator=By.id("summary_products_quantity");
        private static final By PriceLocatorSansShipping = By.id("total_product");
        private static final By ShippingCostLocator = By.id("total_shipping");
        private static final By TotalPriceLocator = By.id("total_price");
        private static final By ItemInCartLocator = By.className("cart_item");
        //id начинается на total_product_price:
        private static final By IndividualItemInCartPrice = By.cssSelector("*[contains(@id,'total_product_price_')]");

        public CartPage(WebDriver driver) {
            super(driver);
        }
        public void openCartPage() {
            driver.get(CartPageURL);
        }

        public void validateNumberOfItemsInCart (int ItemsInCart) {
            Assert.assertEquals(driver.findElements(ItemInCartLocator).size(), ItemsInCart, "Item quantity doesn't match");
        }

        public void validateOrderDetails (String productName, int quantity, String price) {
        String currentQuantity = driver.findElement(By.id(String.valueOf(ProductQuantityLocator))).getText();
        Assert.assertEquals(currentQuantity, String.valueOf(quantity), "Product quantity doesn't match");

        String currentTotalPrice = driver.findElement(By.id(String.valueOf(TotalPriceLocator))).getText();
        Assert.assertEquals(currentTotalPrice, String.valueOf(price), "Total price doesn't match");


        }
    }
}
