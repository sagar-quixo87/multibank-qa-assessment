package com.multibank.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppDownloadPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AppDownloadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Waits for redirect to an app store
    public String getResolvedUrl() {
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("apps.apple.com"),
                        ExpectedConditions.urlContains("play.google.com")
                )
        );
        return driver.getCurrentUrl();
    }
}