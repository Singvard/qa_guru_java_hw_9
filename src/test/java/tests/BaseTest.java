package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    private static final String FULL_HD = "1920x1080";
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeAll
    static void configureSelenide() {
        Configuration.browserSize = FULL_HD;
        Configuration.baseUrl = BASE_URL;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}