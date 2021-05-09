package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String urlHomePage = "http://automationpractice.com/index.php";
    private static final By txtSearchBar = By.id("search_query_top");
    private static final By btnSearchButton = By.name("submit_search");
    private static final By txtSearchResultsCount = By.className("heading-counter");
    private static final By txtErrorAlert = By.className("alert-warning");
    private static final By searchResultList = By.cssSelector("ul.product_list img.replace-2x");
    private static final By ddAutoCompleteResults = By.cssSelector(".ac_results .ac_even");
    private static final By autoCompleteResultList = By.cssSelector(".ac_results li");

    public void openPage() {
        driver.get(urlHomePage);
    }

    public void searchForText(String query) {
        driver.findElement(txtSearchBar).sendKeys(query);
    }

    public void submitSearch() {
        driver.findElement(btnSearchButton).click();
    }

    public String getError() {
        return driver.findElement(txtErrorAlert).getText();
    }

    public String getResultCount() {
        return driver.findElement(txtSearchResultsCount).getText();
    }

    public List<String> findItem() {
        List<WebElement> searchResult = driver.findElements(searchResultList);
        List<String> urlList = new ArrayList<String>();
        for (WebElement e : searchResult) {
            urlList.add(e.getAttribute("src"));
        }
        return urlList;
    }

    public void ifACIsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(ddAutoCompleteResults));
    }

    public boolean acVisible() {
        return driver.findElement(ddAutoCompleteResults).isDisplayed();
    }

    public List<String> findACResult() {
        List<WebElement> acResult = driver.findElements(autoCompleteResultList);
        List<String> urlList = new ArrayList<String>();
        for (WebElement e : acResult) {
            urlList.add(e.getText());
        }
        return urlList;
    }

}
