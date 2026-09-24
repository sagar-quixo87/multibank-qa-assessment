package com.multibank.qa.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    public WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        // Standard desktop viewport
        driver.manage().window().maximize();

        return driver;
    }
}