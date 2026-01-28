package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Attach;

import java.util.Map;

public abstract class BaseTest {
    private static final String FULL_HD = "1920x1080";
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeAll
    static void configureSelenide() {
        Configuration.browserSize = FULL_HD;
        Configuration.baseUrl = BASE_URL;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability(
                "selenoid:options",
                Map.of(
                        "enableVNC", true,
                        "enableVideo", true
                )
        );
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSources();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}