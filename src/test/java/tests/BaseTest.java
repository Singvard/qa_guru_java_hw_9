package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.Attach;

public abstract class BaseTest {
    private static WebDriverProvider provider;

    @BeforeAll
    static void configureSelenide() {
        provider = new WebDriverProvider();
        provider.configure();
    }

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(false));
    }

    @AfterEach
    void tearDown() {
        if (provider.getConfig().isRemote()) {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSources();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }
}