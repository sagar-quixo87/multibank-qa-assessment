package com.multibank.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators for elements on the homepage
    private final By exploreNavLink = By.xpath("//nav[@aria-label='Main']//a[normalize-space()='Explore']");
    private final By mainNavigationLinks = By.cssSelector("nav[aria-label='Main'] a");
    private final By companyNavLink = By.cssSelector("nav[aria-label='Main'] a[href$='/company']");
    private final By downloadAppLink = By.linkText("Download the app");
    private final By khabibImages = By.cssSelector("img[src^='/homepage/khabib-img']");
    private final By campaignKhabibBanner = By.xpath(
            "//h3[normalize-space()='Unblemished. Unstoppable. United.']"
                    + "/ancestor::div[.//img[starts-with(@src, '/homepage/khabib-img')]][1]");

    // Constructor to initialize the HomePage with WebDriver and WebDriverWait
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

    // Get app download link URL
    public String getAppDownloadUrl() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(downloadAppLink))
                .getAttribute("href");
    }

    // Opens the download destination, handling either the current tab or a new window.
    public AppDownloadPage openAppDownload() {
        Set<String> originalWindows = driver.getWindowHandles();
        String originalUrl = driver.getCurrentUrl();
        wait.until(ExpectedConditions.elementToBeClickable(downloadAppLink)).click();
        wait.until(currentDriver -> currentDriver.getWindowHandles().size() > originalWindows.size()
                || !currentDriver.getCurrentUrl().equals(originalUrl));
        for (String window : driver.getWindowHandles()) {
            if (!originalWindows.contains(window)) {
                driver.switchTo().window(window);
                break;
            }
        }
        return new AppDownloadPage(driver);
    }

    // Opens the company page by clicking the "Company" navigation link and returns a CompanyPage object.
    public CompanyPage openCompanyPage() {
        wait.until(ExpectedConditions.elementToBeClickable(companyNavLink)).click();
        return new CompanyPage(driver);
    }

    public ExplorePage openExplorePage() {
        wait.until(ExpectedConditions.elementToBeClickable(exploreNavLink)).click();
        return new ExplorePage(driver);
    }

    // Get the Khabib banner text when visible
    public String getKhabibBannerText() {
        return scrollToVisible(campaignKhabibBanner).getText().replaceAll("\\s+", " ").trim();
    }

    public boolean isKhabibBannerInExpectedRegion() {
        WebElement portfolio = getSectionByHeading("Securely build your portfolio");
        WebElement campaign = scrollToVisible(campaignKhabibBanner);
        WebElement features = getSectionByHeading("Smarter ways to trade and grow");
        return wait.withMessage("Khabib banner should be between the portfolio and features sections")
                .until(ignored -> isAbove(portfolio, campaign) && isAbove(campaign, features));
    }

    private boolean isAbove(WebElement upper, WebElement lower) {
        Rectangle bounds = upper.getRect();
        // WebDriver rounds fractional CSS coordinates to integer pixels.
        return bounds.getY() + bounds.getHeight() <= lower.getRect().getY() + 1;
    }

    private WebElement getSectionByHeading(String heading) {
        return scrollToVisible(By.xpath(
                "//h3[normalize-space()='" + heading + "']/ancestor::section[1]"));
    }

    // Below-the-fold sections animate into view only after scrolling to them.
    private WebElement scrollToVisible(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Checks if the Khabib banner image is loaded by verifying that at least one of the images within the banner is displayed and has a natural width greater than zero.
    public boolean isKhabibBannerImageLoaded() {
        WebElement banner = scrollToVisible(campaignKhabibBanner);
        List<WebElement> images = banner.findElements(khabibImages);

        for (WebElement image : images) {
            if (image.isDisplayed()) {
                Boolean loaded = (Boolean) ((JavascriptExecutor) driver)
                        .executeScript(
                                "return arguments[0].complete && arguments[0].naturalWidth > 0;",
                                image
                        );
                return loaded;
            }
        }
        return false;
}
}
