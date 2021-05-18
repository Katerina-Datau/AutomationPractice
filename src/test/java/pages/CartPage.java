package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;

public class CartPage extends BasePage {

    private static final String urlCartPage = "http://automationpractice.com/index.php?controller=order";

    private static final By txtProductQuantity = By.cssSelector("span#summary_products_quantity");
    private static final By txtTotalPrice = By.id("total_price");
    private static final By txtItemInCart = By.className("cart_item");
    private static final By txtItemPrice = By.cssSelector(".cart_total span");

    private static final By btnProceedToCheckout = By.className("standard-checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening shopping cart")
    public void openPage() {
        driver.get(urlCartPage);
    }

    @Step("Proceeding to checkout")
    public void proceedToCheckout() {
        driver.findElement(btnProceedToCheckout).click();
    }

    public void waitUntilCanProceed() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(btnProceedToCheckout));
    }

    public WebElement findItemByName(String productName) {
        List<WebElement> elements = driver.findElements(txtItemInCart);
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).findElement(By.cssSelector(".product-name")).getText().equals(productName)) {
                return elements.get(i);
            }
        }
        throw new NoSuchElementException();
    }

    @Step("Validating name and price of {productName} in cart")
    public void validateItemsInCart(String productName, String price) {
        WebElement element = findItemByName(productName);
        Assert.assertEquals(price, element.findElement(txtItemPrice).getText());
    }

    @Step("Validating total price and quantity of items in cart")
    public void validateOrderDetails(int quantity, String price) {
        String currentQuantity = driver.findElement(txtProductQuantity).getText().replaceAll("\\D", "");
        Assert.assertEquals(currentQuantity, String.valueOf(quantity), "Product quantity doesn't match");

        String currentTotalPrice = driver.findElement(txtTotalPrice).getText().replace("$", "");
        Assert.assertEquals(currentTotalPrice, price, "Total price doesn't match");
    }
}
