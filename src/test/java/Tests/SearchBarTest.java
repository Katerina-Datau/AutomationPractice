package Tests;

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

    /**
     * 1.3 Search for a specific item
     */
    @Test
    public void searchForItem() {
        homePage.openPage();
        homePage.searchForText("Faded Short Sleeve T-shirts");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "1 result has been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/1/1-home_default.jpg"));
    }

    //TODO check these for assertfalse?
    // "dress" returns everything (as opposed to 5 dresses) because it finds "statements dresses" in "more info" product table. Is that right?

    /**
     * 1.4. Search for "printed summer"
     */
    @Test
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

    /**
     * 1.5 Search for relevant colour option
     */
    @Test
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

    /**
     * 1.6 Search for non-existent term
     */
    @Test
    public void searchForIncorrect() {
        homePage.openPage();
        homePage.searchForText("qwert");
        homePage.submitSearch();
        Assert.assertEquals(homePage.getResultCount(), "0 results have been found.");
        Assert.assertEquals(homePage.getError(), ("No results were found for your search \"qwert\""));
    }

    /**
     * 1.7 Autocompletion test
     */
    @Test
    public void autoCompleteTest() {
        homePage.openPage();
        homePage.searchForText("sum");
        homePage.ifACIsVisible();
        Assert.assertTrue(homePage.acVisible());
        List<String> actualResultList = homePage.findACResult();
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Summer Dress"));
        Assert.assertTrue(actualResultList.contains("Summer Dresses > Printed Chiffon Dress"));
        Assert.assertTrue(actualResultList.contains("T-shirts > Faded Short Sleeve T-shirts"));
    }

    /**
     * 2.1 Search by pressing ENTER
     */
    @Test
    public void enterKeyTest() {
        homePage.openPage();
        homePage.searchForText("blouse");
        homePage.pressEnter();
        Assert.assertEquals(homePage.getResultCount(), "1 result has been found.");
        List<String> actualResultList = homePage.findItem();
        Assert.assertTrue(actualResultList.contains("http://automationpractice.com/img/p/7/7-home_default.jpg"));
    }

    /**
     * 2.2 RETURN key, search result preservation
     */
    @Test
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

    //"summ" - t-shirt not in ac anymore, why?

    //TODO sorting results
    //TODO checkbox/slider search (doesn't work manually)

}
