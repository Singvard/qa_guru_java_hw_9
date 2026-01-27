package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import models.Item;
import utils.PageUtils;
import utils.SessionManager;

public class SauceDemoCartPage {
    private static final String ADDRESS = "/cart.html";
    private static final ElementsCollection INVENTORY_ITEMS = Selenide.$$(PageUtils.testId("inventory-item"));
    private static final SelenideElement REMOVE_BACKPACK_FROM_CART = Selenide.$(PageUtils.testId("remove-sauce-labs-backpack"));
    private static final SelenideElement REMOVE_BIKE_LIGHT_FROM_CART = Selenide.$(PageUtils.testId("remove-sauce-labs-bike-light"));
    private static final SelenideElement REMOVE_BOLT_T_SHIRT_FROM_CART = Selenide.$(PageUtils.testId("remove-sauce-labs-bolt-t-shirt"));
    private static final SelenideElement REMOVE_FLEECE_JACKET_FROM_CART = Selenide.$(PageUtils.testId("remove-sauce-labs-fleece-jacket"));
    private static final SelenideElement REMOVE_ONESIE_FROM_CART = Selenide.$(PageUtils.testId("remove-sauce-labs-onesie"));
    private static final SelenideElement REMOVE_RED_T_SHIRT_FROM_CART = Selenide.$(PageUtils.testId("remove-test.allthethings()-t-shirt-(red)"));

    private final SessionManager sessionManager = SessionManager.getInstance();

    public SauceDemoCartPage openWithUser(String username, String password) {
        sessionManager.openPageWithSession(sessionManager.getOrCreateSession(username, password), ADDRESS);
        return this;
    }

    public String getCartItemPrice(Item item) {
        return INVENTORY_ITEMS.filterBy(Condition.innerText(item.toString()))
                .get(0)
                .find(PageUtils.testId("inventory-item-price"))
                .getText();
    }

    public SauceDemoCartPage clearCart() {
        if (!INVENTORY_ITEMS.isEmpty()) {
            if (REMOVE_BACKPACK_FROM_CART.exists()) REMOVE_BACKPACK_FROM_CART.click();
            if (REMOVE_BIKE_LIGHT_FROM_CART.exists()) REMOVE_BIKE_LIGHT_FROM_CART.click();
            if (REMOVE_BOLT_T_SHIRT_FROM_CART.exists()) REMOVE_BOLT_T_SHIRT_FROM_CART.click();
            if (REMOVE_FLEECE_JACKET_FROM_CART.exists()) REMOVE_FLEECE_JACKET_FROM_CART.click();
            if (REMOVE_ONESIE_FROM_CART.exists()) REMOVE_ONESIE_FROM_CART.click();
            if (REMOVE_RED_T_SHIRT_FROM_CART.exists()) REMOVE_RED_T_SHIRT_FROM_CART.click();
        }
        return this;
    }
}
