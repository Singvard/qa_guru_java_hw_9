package tests;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import models.Item;
import org.assertj.core.api.Assertions;
import pages.CartPage;
import pages.ErrorPage;
import pages.ProductsPage;

public class Steps {
    private Steps() {
    }

    @Step("Проверить, что фактический заголовок страницы соответствует ожидаемому.")
    public static void verifyPageTitle(String actualTitle, String expectedTitle) {
        Assertions.assertThat(actualTitle)
                .as("Фактический заголовок страницы не соответствует ожидаемому.")
                .isEqualTo(expectedTitle);
    }

    @Step("Проверить, что элемент '{elementName}' отображается на странице.")
    public static void verifyElementVisibility(String elementName, boolean actualVisibility) {
        Assertions.assertThat(actualVisibility)
                .as("Элемент '%s' должен быть видимым.".formatted(elementName))
                .isTrue();
    }

    @Step("Проверить, что фактическое сообщение об ошибке соответствует ожидаемому.")
    public static void verifyPageErrorMessage(
            @Param(mode = Parameter.Mode.HIDDEN) ErrorPage page,
            String expectedMessage
    ) {
        Assertions.assertThat(page.getErrorMessage())
                .as("Сообщение об ошибке должно соответствовать ожидаемому.")
                .isEqualTo(expectedMessage);
    }

    @Step("Проверить, что фактическое количество товаров в корзине равно {expectedValue}.")
    public static void verifyNumberOfItemsInCart(
            @Param(mode = Parameter.Mode.HIDDEN) ProductsPage page,
            int expectedValue
    ) {
        Assertions.assertThat(page.getCartItemCount())
                .as("Количество товаров в корзине должно быть равно %d.".formatted(expectedValue))
                .isEqualTo(expectedValue);
    }

    @Step("Проверить, что фактическая цена товара {item} в корзине равна {expectedPrice}.")
    public static void verifyPriceOfItemInCart(
            @Param(mode = Parameter.Mode.HIDDEN) CartPage page,
            Item item,
            String expectedPrice
    ) {
        Assertions.assertThat(page.getCartItemPrice(item))
                .as("Цена товара в корзине должна совпадать с ожидаемой ценой")
                .isEqualTo(expectedPrice);
    }
}
