package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import utils.PageUtils;

public class SauceDemoLoginPage {
    private static final String ADDRESS = "/";
    private static final SelenideElement USERNAME = Selenide.$(PageUtils.testId("username"));
    private static final SelenideElement PASSWORD = Selenide.$(PageUtils.testId("password"));
    private static final SelenideElement ERROR = Selenide.$(PageUtils.testId("error"));
    private static final SelenideElement LOGIN_BUTTON = Selenide.$(PageUtils.testId("login-button"));

    public SauceDemoLoginPage open() {
        Selenide.open(ADDRESS);
        return this;
    }

    public SauceDemoLoginPage fillUsername(String username) {
        USERNAME.setValue(username);
        return this;
    }

    public SauceDemoLoginPage fillPassword(String password) {
        PASSWORD.setValue(password);
        return this;
    }

    public SauceDemoProductsPage login() {
        LOGIN_BUTTON.submit();
        return new SauceDemoProductsPage();
    }

    public SauceDemoLoginPage loginWithError() {
        LOGIN_BUTTON.submit();
        return this;
    }

    public String getErrorMessage(){
        return ERROR.getText();
    }
}
