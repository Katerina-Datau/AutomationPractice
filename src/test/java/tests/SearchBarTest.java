package tests;

import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchBarTest extends BaseTest {

    @Test(description = "Press the search button without actually putting in search query")
    @TmsLink("AP-17")
    public void searchForNull() {
        homePage.openPage();
        homePage.searchForText("");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "0 results have been found.");
        Assert.assertEquals(homePage.getError(), ("Please enter a search keyword"));
    }

    @Test(description = "Search for all items in the shop")
    @TmsLink("AP-18")
    public void searchForAll() {
        homePage.openPage();
        homePage.searchForText("all");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "7 results have been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/2/12-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/1-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/0/10-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/6/16-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/2/0/20-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/8/8-home_default.jpg"));
    }

    @Test(description = "Search for a specific item in the shop (Faded Short Sleeve T-shirts)")
    @TmsLink("AP-19")
    public void searchForItem() {
        homePage.openPage();
        homePage.searchForText("Faded Short Sleeve T-shirts");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "1 result has been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/1-home_default.jpg"));
    }

    @Test(description = "Search for printed summer dresses")
    @TmsLink("AP-20")
    public void searchForPrintedSummer() {
        homePage.openPage();
        homePage.searchForText("printed summer");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "3 results have been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/2/12-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/6/16-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/2/0/20-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/1/1-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/1/0/10-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/8/8-home_default.jpg"));
    }

    @Test(description = "Search for items with yellow as an available colour option")
    @TmsLink("AP-24")
    public void searchForYellow() {
        homePage.openPage();
        homePage.searchForText("yellow");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "3 results have been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/2/12-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/6/16-home_default.jpg"));
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/2/0/20-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/1/1-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
        Assert.assertFalse(actualResultList.contains("http://automationpractice.com/img/p/8/8-home_default.jpg"));
    }

    @Test(description = "Search for term which must produce no results on the website")
    @TmsLink("AP-25")
    public void searchForIncorrect() {
        homePage.openPage();
        homePage.searchForText("nuclear");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "0 results have been found.");
        Assert.assertEquals(homePage.getError(), ("No results were found for your search \"nuclear\""));
    }

    @Test(description = "Check if autocompletion function is implemented properly")
    @TmsLink("AP-26")
    public void autoCompleteTest() {
        homePage.openPage();
        homePage.searchForText("sum");
        homePage.waitUntilACVisible();
        Assert.assertTrue(homePage.acVisible());
        List<String> actualResultList = homePage.findACResult();
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Summer Dress"));
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Chiffon Dress"));
        Assert.assertTrue(actualResultList.contains("T-shirts > Faded Short Sleeve T-shirts"));
    }

    @Test(description = "Check if user can begin search by pressing ENTER on the keyboard")
    @TmsLink("AP-15")
    public void enterKeyTest() {
        homePage.openPage();
        homePage.searchForText("blouse");
        homePage.pressEnter();
        Assert.assertEquals(homePage.getResultCount(), "1 result has been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
    }

    @Test(description = "Check if search results are preserved after looking for more info on the item and returning back to the search result page")
    @TmsLink("AP-16")
    public void resultPreservationTest() {
        homePage.openPage();
        homePage.searchForText("blouse");
        homePage.submitSearch();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        homePage.resultsByList();
        homePage.pressMore();
        homePage.pressReturn();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Assert.assertEquals(homePage.getResultCount(), "1 result has been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
    }
}