package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {
    private static final String FULL_HD = "1920x1080";
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeAll
    static void configureSelenide() {
        Configuration.browserSize = FULL_HD;
        Configuration.baseUrl = BASE_URL;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }
}