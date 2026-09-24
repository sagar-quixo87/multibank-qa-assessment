package com.multibank.qa.tests;

import com.multibank.qa.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    /**
     * Basic smoke test to verify that the trading platform
     * can be opened successfully.
     */
    @Test
    public void verifyTradingPlatformLoads() {

        // Get the title of the currently opened browser page.
        String pageTitle = driver.getTitle();

        // Print it so we can see what the application returned.
        System.out.println("Page title: " + pageTitle);

        // A blank title could indicate that the page did not load correctly.
        Assert.assertFalse(
                pageTitle.isBlank(),
                "Page title should not be empty"
        );
    }
}