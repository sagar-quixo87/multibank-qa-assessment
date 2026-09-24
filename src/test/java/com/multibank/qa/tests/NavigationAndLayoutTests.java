package com.multibank.qa.tests;

import com.multibank.qa.base.BaseTest;
import com.multibank.qa.pages.HomePage;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class NavigationAndLayoutTests extends BaseTest {

    // TEST#1: Test to verify that the main navigation items are displayed correctly
    // on the home page.
    @Test
    public void verifyMainNavigationItemsAreDisplayed() {
        HomePage homePage = new HomePage(driver);

        List<String> expectedNavigationItems = List.of(
                "Explore",
                "Features",
                "OTC Desk",
                "Company",
                "Support",
                "Blog",
                "$MBG");

        List<String> actualNavigationItems = homePage.getMainNavigationTexts();

        System.out.println(
                "Navigation items found: " + actualNavigationItems);

        Assert.assertEquals(
                actualNavigationItems,
                expectedNavigationItems,
                "Main navigation items do not match");
    }

    // TEST#2: Test to verify that the main navigation links point to the correct
    // destinations.
    @Test
    public void verifyNavigationDestinations() {
        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("Explore"),
                "https://mb.io/en-AE/explore");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("Features"),
                "https://mb.io/en-AE/features");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("OTC Desk"),
                "https://mb.io/en-AE/features/otc-desk");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("Company"),
                "https://mb.io/en-AE/company");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("Support"),
                "https://mb.io/en-AE/support");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("Blog"),
                "https://mb.io/en-AE/blog");

        Assert.assertEquals(
                homePage.getNavigationLinkUrl("$MBG"),
                "https://token.multibankgroup.com/en");
    }

    // TEST#3: Test to verify that the main navigation is visible at standard desktop viewports.
    @Test
    public void verifyNavigationAtDesktopViewports() {
        HomePage homePage = new HomePage(driver);

        int[][] viewports = {
                { 1366, 768 }, // Common laptop resolution
                { 1440, 900 }, // Typical desktop/laptop
                { 1920, 1080 } // Full HD resolution
        };

        for (int[] viewport : viewports) {
            driver.manage().window().setSize(
                    new Dimension(viewport[0], viewport[1]));

            Assert.assertTrue(
                    homePage.areMainNavigationLinksVisible(),
                    "Navigation should be visible at "
                            + viewport[0] + "x" + viewport[1]);
        }
    }
}