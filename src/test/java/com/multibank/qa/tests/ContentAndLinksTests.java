package com.multibank.qa.tests;

import com.multibank.qa.base.BaseTest;
import com.multibank.qa.pages.AppDownloadPage;
import com.multibank.qa.pages.CompanyPage;
import com.multibank.qa.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ContentAndLinksTests extends BaseTest {

    /*
     * Assumption: The Khabib/$MBG campaign is the marketing banner. Its expected region
     * is based on browser and DOM inspection of the current site: below the entire
     * portfolio section, including asset cards, and above the platform features section.
     */
    // Test#1: Test to verify that the Khabib campaign banner is displayed correctly on the homepage.
    @Test
    public void verifyMarketingBanner() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(
                homePage.getKhabibBannerText()
                        .contains("Unblemished. Unstoppable. United."),
                "Khabib banner should display its campaign heading"
        );
        Assert.assertTrue(
                homePage.isKhabibBannerInExpectedRegion(),
                "Khabib banner should appear between portfolio and platform features sections"
        );
        Assert.assertTrue(
                homePage.isKhabibBannerImageLoaded(),
                "Khabib banner image should be loaded"
        );
}

    // Test#2: Test to verify that the app download link resolves to a valid app store URL.
    @Test
    public void verifyAppDownloadLinkResolves() {
        HomePage homePage = new HomePage(driver);
        String downloadUrl = homePage.getAppDownloadUrl();

        Assert.assertNotNull(
                downloadUrl,
                "Download link should have a destination"
        );

        Assert.assertFalse(
                downloadUrl.isBlank(),
                "Download URL should not be empty"
        );

        AppDownloadPage downloadPage = homePage.openAppDownload();
        String resolvedUrl = downloadPage.getResolvedUrl();

        Assert.assertTrue(
                resolvedUrl.contains("apps.apple.com")
                        || resolvedUrl.contains("play.google.com"),
                "Download link should resolve to an app store"
        );
}


    // Test#3: Test to verify that the "Why MultiBank" page displays its expected content sections.
    @Test
    public void verifyWhyMultiBankPageContent() {
         
        CompanyPage companyPage = new HomePage(driver).openCompanyPage();
        Assert.assertEquals(companyPage.getPageHeadingText(), "Why MultiBank Group?");
        Assert.assertTrue(companyPage.getSectionText("A tradition of global leadership")
                        .contains("Founded in 2005, MultiBank has grown"),
                "Leadership section should contain its company history text");
        Assert.assertTrue(companyPage.getSectionText("Innovation with purpose")
                        .contains("We believe technology should simplify finance"),
                "Innovation section should contain its technology text");
        Assert.assertTrue(companyPage.getSectionText("Integrity built into every decision")
                        .contains("Trust is earned through consistent action."),
                "Integrity section should contain its trust and governance text");
    }
}
