package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean findText(final List<WebElement> allErrors, String findText) {
        for (int i = 0; i < allErrors.size(); i++) {
            if (allErrors.get(i).getText().equals(findText)) {
                return true;
            }
        }
        return false;
    }
}

