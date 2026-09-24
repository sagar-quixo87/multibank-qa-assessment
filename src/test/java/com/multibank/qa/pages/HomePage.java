package com.multibank.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By exploreNavLink = By.xpath("//nav[@aria-label='Main']//a[normalize-space()='Explore']");

    private final By mainNavigationLinks = By.cssSelector("nav[aria-label='Main'] a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Returns the Explore link when visible
    public WebElement getExploreNavLink() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(exploreNavLink));
    }

    // Returns all main navigation links
    public List<WebElement> getMainNavigationLinks() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(mainNavigationLinks));
        return driver.findElements(mainNavigationLinks);
    }

    // Returns text of all navigation links
    public List<String> getMainNavigationTexts() {
        List<WebElement> navigationLinks = getMainNavigationLinks();
        List<String> navigationTexts = new ArrayList<>();

        for (WebElement link : navigationLinks) {
            String text = link.getText().trim();

            if (!text.isEmpty()) {
                navigationTexts.add(text);
            }
        }
        return navigationTexts;
    }

    // Returns href of a navigation item
    public String getNavigationLinkUrl(String navigationText) {
        List<WebElement> navigationLinks = getMainNavigationLinks();

        for (WebElement link : navigationLinks) {
            if (link.getText().trim().equals(navigationText)) {
                return link.getAttribute("href");
            }
        }
        return null;
    }

    // Checks if all main navigation links are visible
    public boolean areMainNavigationLinksVisible() {
        List<WebElement> navigationLinks = getMainNavigationLinks();

        for (WebElement link : navigationLinks) {
            if (!link.isDisplayed()) {
                return false;
            }
        }
        return true;
    }
}