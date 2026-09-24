# MultiBank QA Automation Assessment

Web UI automation framework developed for the MultiBank QA Automation Coding Challenge.

The framework covers key navigation, trading market content, marketing and download links, page content, and selected edge cases on the MultiBank website.

## Tech Stack

* Java 21
* Selenium WebDriver
* TestNG
* Maven
* WebDriverManager
* GitHub Actions

## Framework Design

The framework follows the Page Object Model (POM) to keep page locators and page interactions separate from test logic.

Main structure:

```text
src/test/java/com/multibank/qa/
├── base/
│   ├── BaseTest.java
│   └── DriverFactory.java
├── pages/
│   ├── AppDownloadPage.java
│   ├── CompanyPage.java
│   ├── ExplorePage.java
│   └── HomePage.java
└── tests/
    ├── ContentAndLinksTests.java
    ├── EdgeCaseTests.java
    ├── NavigationAndLayoutTests.java
    ├── SmokeTest.java
    └── TradingFunctionalityTests.java
```

## Test Coverage

The automated suite currently contains 11 tests covering the main assessment requirements.

### Navigation and Layout

* Verify expected main navigation items are displayed
* Verify navigation links point to the correct destinations
* Verify navigation at standard desktop viewport sizes

### Trading Functionality

* Verify the Spot Market section displays assets
* Verify market categories such as Hot, Gainers and Losers can be selected

### Content and Links

* Verify the homepage marketing banner and image
* Verify the application download link resolves to a supported mobile store
* Verify key content on the Why MultiBank page

### Negative and Edge Cases

* Verify an invalid route displays the Page Not Found state
* Verify the page renders at a mobile viewport

## Prerequisites

Install:

* Java 21
* Maven
* Chrome or Firefox

Verify the installation:

```bash
java --version
mvn --version
```

## Running the Tests

Run the complete suite in Chrome:

```bash
mvn clean test
```

Chrome is the default browser.

Run in Firefox:

```bash
mvn clean test -Dbrowser=firefox
```

Run Chrome in headless mode:

```bash
mvn clean test -Dheadless=true
```

TestNG/Surefire reports are generated under:

```text
target/surefire-reports/
```

## Cross-Browser Testing

The complete suite has been executed successfully on:

* Google Chrome
* Mozilla Firefox

The suite also runs in headless Chrome through GitHub Actions.

## CI

GitHub Actions is configured to execute the automation suite automatically on pushes and pull requests.

The CI environment runs:

```bash
mvn clean test -Dheadless=true
```

A fixed desktop viewport is used during headless execution so responsive behavior remains consistent with the desktop navigation tests.

## Design Decisions

### Page Object Model

Page-specific locators and interactions are kept inside page classes. Test classes mainly contain test flow and assertions.

### Explicit Waits

Explicit waits are used for dynamic elements instead of relying on fixed sleeps.

### Browser Selection

Chrome is used by default. The browser can be changed using the `browser` Maven system property.

### Headless Execution

Headless execution is controlled using the `headless` system property, allowing the same framework to run locally and in CI.

### Locale-Independent Navigation

The MultiBank website may use different English locale paths depending on the execution environment, for example `/en` or `/en-AE`.

Navigation assertions therefore validate the expected destination path rather than hard-coding a specific locale.

## Assumptions and Limitations

* The assessment originally referenced `trade.multibank.io`. Testing was performed against the currently available MultiBank web experience at `mb.io`.
* Website content and market data are dynamic, so tests focus on expected structure and behavior rather than hard-coded market values.
* The current website exposes a smart application download link which redirects the user to the appropriate mobile store. The test validates that this link resolves to a supported store rather than assuming separate App Store and Google Play links are always exposed.
* Mobile coverage in this assessment is limited to a viewport rendering check. Full mobile application testing is discussed separately in Task 2.

## Current Result

```text
Tests run: 11
Failures: 0
Errors: 0
Skipped: 0
```

All automated tests are currently passing locally and in the GitHub Actions CI pipeline.

## Author

Sagar Singla
