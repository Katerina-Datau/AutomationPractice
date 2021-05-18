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
        return allErrors.stream()
                .map(WebElement::getText)
                .anyMatch(text -> text.equals(findText));
    }

    public abstract void openPage();
}

