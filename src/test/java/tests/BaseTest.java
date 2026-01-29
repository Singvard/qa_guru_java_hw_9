package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import utils.Attach;

public abstract class BaseTest {
    private static WebDriverProvider provider;

    @BeforeAll
    static void configureSelenide() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(false));

        provider = new WebDriverProvider();
        provider.configure();
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