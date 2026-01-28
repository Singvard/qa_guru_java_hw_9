package tests;

import com.codeborne.selenide.Selenide;
import models.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.stream.Stream;

class SauceDemoTest extends BaseTest {
    private static final String STANDARD_USER = "standard_user";
    private static final String PROBLEM_USER = "problem_user";
    private static final String PERFORMANCE_GLITCH_USER = "performance_glitch_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String BACKPACK_PRICE = "$29.99";
    private static final String BIKE_LIGHT_PRICE = "$9.99";
    private static final String BOLT_T_SHIRT_PRICE = "$15.99";
    private static final String EXPECTED_PAGE_TITLE = "Products";
    private static final int ONE = 1;

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }

    @ParameterizedTest(name = "Успешный вход с логином {0}.")
    @DisplayName("Успешная авторизация на сайте с различными валидными логинами.")
    @ValueSource(strings = {
            STANDARD_USER,
            PROBLEM_USER,
            PERFORMANCE_GLITCH_USER
    })
    void loginWithDifferentUsers(String username) {
        var loginPage = new LoginPage();
        var productsPage = loginPage.open()
                .fillUsername(username)
                .fillPassword(VALID_PASSWORD)
                .login();

        Steps.verifyPageTitle(productsPage.getTitle(), EXPECTED_PAGE_TITLE);
        Steps.verifyElementVisibility("Корзина покупок", productsPage.isCartVisible());
        Steps.verifyElementVisibility("Ассортимент", productsPage.isInventoryVisible());
    }

    @ParameterizedTest(name = "Неудачная авторизация с логином {0}, паролем {1} и ожидаемым текстом ошибки {2}.")
    @DisplayName("Ошибка авторизации с невалидными логинами/паролями.")
    @CsvFileSource(resources = "/test.csv", numLinesToSkip = ONE)
    void testInvalidLoginScenariosFromFile(String username, String password, String expectedErrorMessage) {
        var loginPage = new LoginPage().open()
                .fillUsername(username)
                .fillPassword(password)
                .loginWithError();

        Steps.verifyPageErrorMessage(loginPage, expectedErrorMessage);
    }

    @ParameterizedTest(name = "Добавление товара {0} в корзину с ожидаемой ценой {1}")
    @DisplayName("Проверка добавления товара в корзину.")
    @MethodSource("provideProductData")
    void testAddingItemToCart(Item item, String expectedPrice) {
        var productsPage = new ProductsPage()
                .openWithUser(STANDARD_USER, VALID_PASSWORD)
                .addItemToCart(item);
        Steps.verifyNumberOfItemsInCart(productsPage, ONE);
        var cartPage = productsPage.goToCart();
        Steps.verifyPriceOfItemInCart(cartPage, item, expectedPrice);
    }

    private static Stream<Arguments> provideProductData() {
        return Stream.of(
                Arguments.of(Item.BACKPACK, BACKPACK_PRICE),
                Arguments.of(Item.BIKE_LIGHT, BIKE_LIGHT_PRICE),
                Arguments.of(Item.BOLT_T_SHIRT, BOLT_T_SHIRT_PRICE)
        );
    }
}
