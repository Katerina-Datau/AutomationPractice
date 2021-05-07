package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String urlHomePage = "http://automationpractice.com/index.php";
    private static final By txtSearchBar = By.id("search_query_top");
    private static final By btnSearchButton = By.name("submit_search");
}
