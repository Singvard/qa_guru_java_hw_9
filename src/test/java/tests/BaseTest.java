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

    @BeforeAll
    static void configureSelenide() {
        WebDriverProvider.configure();
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