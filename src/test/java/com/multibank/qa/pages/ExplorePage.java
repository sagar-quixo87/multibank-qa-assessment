package com.multibank.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExplorePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By spotHeading = By.xpath("//h2[normalize-space()='Spot market']");
    private final By marketPanel = By.xpath(
            "//h3[normalize-space()=\"Today's top crypto prices\"]/ancestor::div[.//table][1]");
    private final By categoryButtons = By.xpath(
            ".//button[normalize-space()='Hot' or normalize-space()='Gainers' or normalize-space()='Losers']");
    private final By rows = By.cssSelector("tbody tr[data-index]");

    public ExplorePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getSpotMarketHeading() {
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(spotHeading));
        scrollTo(heading);
        return wait.until(ExpectedConditions.visibilityOf(heading)).getText().trim();
    }

    // Checks if market rows are displayed
    public boolean areMarketRowsDisplayed() {
        WebElement panel = getPanel();
        List<WebElement> marketRows = panel.findElements(rows);

        return !marketRows.isEmpty();
    }

    public List<String> getCategories() {
        List<WebElement> buttons = getPanel().findElements(categoryButtons);
        List<String> categories = new ArrayList<>();

        for (WebElement button : buttons) {
            if (button.isDisplayed()) {
                categories.add(button.getText().trim());
            }
        }
        return categories;
    }

    public String getSelectedCategory() {
        List<WebElement> buttons = getPanel().findElements(categoryButtons);

        for (WebElement button : buttons) {
            String classes = button.getDomAttribute("class");

            if (classes != null && classes.contains("bg-lighter")) {
                return button.getText().trim();
            }
        }
        return "";
    }

    public void selectCategory(String category) {
        if (!List.of("Hot", "Gainers", "Losers").contains(category)) {
            throw new IllegalArgumentException(
                    "Unsupported market category: " + category);
        }

        WebElement panel = getPanel();
        WebElement button = panel.findElement(
                By.xpath(".//button[normalize-space()='" + category + "']"));

        scrollTo(button);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();

        // Wait until the selected category changes
        wait.until(ExpectedConditions.attributeContains(
                By.xpath("//button[normalize-space()='" + category + "']"),
                "class",
                "bg-lighter"));
    }


    private WebElement getPanel() {
        WebElement panel = wait.until(ExpectedConditions.presenceOfElementLocated(marketPanel));
        scrollTo(panel);
        return wait.until(ExpectedConditions.visibilityOf(panel));
    }

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }


}
