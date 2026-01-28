package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.Item;
import utils.PageUtils;
import utils.SessionManager;

public class ProductsPage {
    private static final String ADDRESS = "/inventory.html";
    private static final SelenideElement TITLE = Selenide.$(PageUtils.testId("title"));
    private static final SelenideElement CART = Selenide.$(PageUtils.testId("shopping-cart-link"));
    private static final SelenideElement CART_BADGE = Selenide.$(PageUtils.testId("shopping-cart-badge"));
    private static final SelenideElement INVENTORY_CONTAINER = Selenide.$(PageUtils.testId("inventory-container"));
    private static final SelenideElement ADD_BACKPACK_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-sauce-labs-backpack"));
    private static final SelenideElement ADD_BIKE_LIGHT_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-sauce-labs-bike-light"));
    private static final SelenideElement ADD_BOLT_T_SHIRT_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-sauce-labs-bolt-t-shirt"));
    private static final SelenideElement ADD_FLEECE_JACKET_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-sauce-labs-fleece-jacket"));
    private static final SelenideElement ADD_ONESIE_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-sauce-labs-onesie"));
    private static final SelenideElement ADD_RED_T_SHIRT_TO_CART = Selenide.$(PageUtils.testId("add-to-cart-test.allthethings()-t-shirt-(red)"));

    private final SessionManager sessionManager = SessionManager.getInstance();

    public String getTitle() {
        return TITLE.getText();
    }

    public boolean isCartVisible() {
        return CART.isDisplayed();
    }

    public boolean isInventoryVisible() {
        return INVENTORY_CONTAINER.isDisplayed();
    }

    public int getCartItemCount() {
        return Integer.parseUnsignedInt(CART_BADGE.getText());
    }

    @Step("Авторизоваться на сайте с логином: {username} и паролем: {password}.")
    public ProductsPage openWithUser(String username, String password) {
        sessionManager.openPageWithSession(sessionManager.getOrCreateSession(username, password), ADDRESS);
        return this;
    }

    @Step("Добавить в корзину товар: {item}.")
    public ProductsPage addItemToCart(Item item) {
        switch (item) {
            case BACKPACK -> ADD_BACKPACK_TO_CART.click();
            case BIKE_LIGHT -> ADD_BIKE_LIGHT_TO_CART.click();
            case BOLT_T_SHIRT -> ADD_BOLT_T_SHIRT_TO_CART.click();
            case FLEECE_JACKET -> ADD_FLEECE_JACKET_TO_CART.click();
            case ONESIE -> ADD_ONESIE_TO_CART.click();
            case RED_T_SHIRT -> ADD_RED_T_SHIRT_TO_CART.click();
        }
        return this;
    }

    @Step("Перейти в корзину.")
    public CartPage goToCart() {
        CART.click();
        return new CartPage();
    }
}
