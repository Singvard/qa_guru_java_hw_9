package utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import pages.SauceDemoLoginPage;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.codeborne.selenide.Selenide.open;

public class SessionManager {
    private static final String SESSION_COOKIE_NAME = "session-username";
    private static final ConcurrentHashMap<String, String> sessionCache = new ConcurrentHashMap<>();

    private static SessionManager instance;

    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getOrCreateSession(String username, String password) {
        return sessionCache.computeIfAbsent(
                username + ":" + password,
                key -> createNewSession(username, password)
        );
    }

    private String createNewSession(String username, String password) {
        new SauceDemoLoginPage().open()
                .fillUsername(username)
                .fillPassword(password)
                .login();
        var sessionCookie = WebDriverRunner.getWebDriver().manage().getCookieNamed(SESSION_COOKIE_NAME);
        if (Objects.isNull(sessionCookie)) return findAlternativeCookie();
        var sessionValue = sessionCookie.getValue();
        Selenide.closeWebDriver();
        return sessionValue;
    }

    private String findAlternativeCookie() {
        return WebDriverRunner.getWebDriver().manage().getCookies().stream()
                .filter(cookie -> cookie.getName().contains("session") ||
                        cookie.getName().contains("auth") ||
                        cookie.getName().contains("token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Нет сессионных кук после логина!"));
    }

    private void applySessionToBrowser(String sessionValue) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.getWebDriver().manage().deleteCookieNamed(SESSION_COOKIE_NAME);

            Cookie sessionCookie = new Cookie.Builder(SESSION_COOKIE_NAME, sessionValue)
                    .domain("www.saucedemo.com")
                    .path("/")
                    .isHttpOnly(false)
                    .isSecure(false)
                    .build();

            WebDriverRunner.getWebDriver().manage().addCookie(sessionCookie);
        }
    }

    public void openPageWithSession(String sessionToken, String url) {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            new SauceDemoLoginPage().open(); // Открываем любую страницу для инициализации драйвера
        }
        applySessionToBrowser(sessionToken);
        open(url);
    }
}
