package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShopPageWomen extends BasePage {


    public ShopPageWomen(WebDriver driver) {
        super(driver);
    }

    String productName;
    private static final String ShopPageWomenURL = "http://automationpractice.com/index.php?id_category=3&controller=category";

    //TODO не находит элементы по локатору!
    private static final String AddToCartLocator = "//*[@id=\"center_column\"]/ul/../div/div[2]/div[2]/a[1]/span";

    public void openShopPage() {
        driver.get(ShopPageWomenURL);
    }

    public void addToCart(String ProductName) {
        By addToCart = By.xpath(String.format(AddToCartLocator, productName));
        driver.findElement(addToCart).click();
    }

}
