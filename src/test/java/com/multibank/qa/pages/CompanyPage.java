package com.multibank.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CompanyPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By pageHeading = By.tagName("h1");

    public CompanyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).getText().trim();
    }

    // Scrolls to the section identified by its heading and returns its visible paragraph text.
    public String getSectionText(String heading) {
        // Each editorial section groups its heading and paragraph in one container.
        By sectionLocator = By.xpath("//h2[normalize-space()='" + heading + "']/parent::*");
        WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(sectionLocator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", section);
        wait.until(ExpectedConditions.visibilityOf(section.findElement(By.tagName("h2"))));
        WebElement paragraph = wait.until(ExpectedConditions.visibilityOf(
                section.findElement(By.tagName("p"))));
        return paragraph.getText().replaceAll("\\s+", " ").trim();
    }
}
