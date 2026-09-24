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

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("Explore").endsWith("/explore"));

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("Features").endsWith("/features"));

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("OTC Desk").endsWith("/features/otc-desk"));

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("Company").endsWith("/company"));

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("Support").endsWith("/support"));

                Assert.assertTrue(
                                homePage.getNavigationLinkUrl("Blog").endsWith("/blog"));

                Assert.assertEquals(
                                homePage.getNavigationLinkUrl("$MBG"),
                                "https://token.multibankgroup.com/en");
        }

        // TEST#3: Test to verify that the main navigation is visible at standard
        // desktop viewports.
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