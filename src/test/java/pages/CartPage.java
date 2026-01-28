package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import models.Item;
import utils.PageUtils;

public class CartPage {
    private static final ElementsCollection INVENTORY_ITEMS = Selenide.$$(PageUtils.testId("inventory-item"));

    public String getCartItemPrice(Item item) {
        return INVENTORY_ITEMS.filterBy(Condition.innerText(item.toString()))
                .get(0)
                .find(PageUtils.testId("inventory-item-price"))
                .getText();
    }
}
