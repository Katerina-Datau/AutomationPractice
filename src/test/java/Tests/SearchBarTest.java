package Tests;

import Pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchBarTest extends BaseTest {

    /**
     * 1.1 Null Data Input
     */
    @Test
    public void searchForNull() {
        homePage.openPage();
        homePage.searchForText("");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "0 results have been found.");
        Assert.assertEquals(homePage.getError(), ("Please enter a search keyword"));
    }

    /**
     * 1.2 Search for all
     */
    @Test
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

    //TODO cases # 3-7

    @Test
    public void autoCompleteTest() {
        homePage.openPage();
        homePage.searchForText("sum");
        homePage.ifACIsVisible();


        Assert.assertTrue(homePage.acAbides());
        List<String> actualResultList = homePage.findACResult();
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Summer Dress"));
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Chiffon Dress"));
        Assert.assertTrue(actualResultList.contains("T-shirts > Faded Short Sleeve T-shirts"));
    }

    //TODO case 10

    //TODO return, enter button

    //TODO sorting results

}
