package com.multibank.qa.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.multibank.qa.base.BaseTest;
import com.multibank.qa.pages.ExplorePage;
import com.multibank.qa.pages.HomePage;

public class TradingFunctionalityTests extends BaseTest {

        /*
         * Assumption: Trading display coverage maps to Explore > Spot market on
         * mb.io/en.
         * This page lists assets, not explicit base/quote pairs. Categories are Hot,
         * Gainers,
         * and Losers. Their ranking/membership rules are not specified, and Gainers
         * currently
         * includes negative movers. Category coverage verifies selection and displayed
         * rows;
         * it does not claim to validate the underlying ranking algorithm. No trades are
         * placed.
         */

        // Test#1: Test to verify that the spot market displays assets and the Hot
        // category is selected by default.
        @Test
        public void verifySpotMarketDisplaysAssets() {
                ExplorePage explorePage = new HomePage(driver).openExplorePage();
                Assert.assertEquals(explorePage.getSpotMarketHeading(), "Spot market");
                Assert.assertTrue(explorePage.areMarketRowsDisplayed(), "Spot market should display assets");
                Assert.assertEquals(explorePage.getSelectedCategory(), "Hot", "Hot should be selected initially");
        }

        // Test#2: Test to verify that the spot market categories are displayed and can
        // be switched.
        @Test
        public void verifyMarketCategorySwitching() {
                ExplorePage explorePage = new HomePage(driver).openExplorePage();

                Assert.assertEquals(
                                explorePage.getCategories(),
                                List.of("Hot", "Gainers", "Losers"));

                for (String category : List.of("Gainers", "Losers", "Hot")) {
                        explorePage.selectCategory(category);

                        Assert.assertEquals(
                                        explorePage.getSelectedCategory(),
                                        category,
                                        category + " should be selected");
                }
        }

}
