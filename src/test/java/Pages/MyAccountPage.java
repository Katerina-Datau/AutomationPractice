package Pages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage extends BasePage {
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    private static final String urlMyAccountPage = "http://automationpractice.com/index.php?controller=my-account";

    private static final By btnOrderHistoryAndDetails = By.className("icon-list-ol");
    private static final By btnCreditSlips = By.className("icon-ban-circle");
    private static final By btnMyAddresses = By.className("icon-building");
    private static final By btnPersonalInfo = By.className("icon-user");
    private static final By btnWishlist = By.className("icon-heart");

    /**
     * bjdfdfjgidj
     */
    public void openMyAccountPage() {
        driver.get(urlMyAccountPage);
    }

    public void acc() {

        driver.findElement(btnOrderHistoryAndDetails).click();
    }
}
