package com.multibank.qa.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    // Create a new WebDriver instance.
    public WebDriver createDriver() {
        
    // Set up the ChromeDriver using WebDriverManager.
    WebDriverManager.chromedriver().setup();
    
    // Create a new instance of ChromeDriver.
    WebDriver driver = new ChromeDriver();
     driver.manage().window().maximize();
     return driver;
    }

}
