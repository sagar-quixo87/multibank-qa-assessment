package com.multibank.qa.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    // Creates a fresh browser session before each test method and navigates to the specified URL.
    @BeforeMethod 
    public void setUp() {
        // Initialize the WebDriver using DriverFactory.
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.createDriver();
        driver.get("https://mb.io/en-AE");
    }

    @AfterMethod 
    public void tearDown() {
        // Close the WebDriver after each test method.
        if (driver != null) {
            driver.quit();
        }
    }
    
}
