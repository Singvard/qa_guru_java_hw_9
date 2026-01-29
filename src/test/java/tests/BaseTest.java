package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.Attach;

public abstract class BaseTest {

    @BeforeAll
    static void configureSelenide() {
        WebDriverProvider.configure();
        System.out.println("=== BASE TEST CONFIGURATION ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name"));
        System.out.println("User dir: " + System.getProperty("user.dir"));
        System.out.println("=== FINAL SELENIDE CONFIG ===");
        System.out.println("Browser: " + Configuration.browser);
        System.out.println("Remote URL: " + Configuration.remote);
        System.out.println("Base URL: " + Configuration.baseUrl);
        System.out.println("Timeout: " + Configuration.timeout);
        System.out.println("=============================");
    }

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        if (WebDriverProvider.getConfig().isRemote()) {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSources();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }
}