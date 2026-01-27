package utils;

import com.codeborne.selenide.Selectors;
import org.openqa.selenium.By;

public class PageUtils {
    private static final String SAUCE_DEMO_TEST_ID = "data-test";

    private PageUtils() {
    }

    public static By testId(String value) {
        return Selectors.by(SAUCE_DEMO_TEST_ID, value);
    }

}
