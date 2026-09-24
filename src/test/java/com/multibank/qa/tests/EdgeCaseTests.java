package com.multibank.qa.tests;

import com.multibank.qa.base.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EdgeCaseTests extends BaseTest {

    // Test#1: Test to verify that invalid routes are handled gracefully by the
    // website
    @Test
    public void verifyInvalidRouteIsHandled() {
        driver.get("https://mb.io/en-AE/invalid-page-test");

        String message = driver.findElement(
                By.xpath("//h1[normalize-space()='Page not found']")).getText();

        Assert.assertEquals(
                message,
                "Page not found",
                "Invalid route should show Page not found");
    }

    // Test#2: Test to verify that the website renders correctly at a mobile
    // viewport
    @Test
    public void verifyPageAtMobileViewport() {
        driver.manage().window().setSize(new Dimension(390, 844));
        driver.navigate().refresh();

        Assert.assertTrue(
                driver.findElement(By.tagName("body")).isDisplayed(),
                "Page should render at mobile viewport");
    }
}