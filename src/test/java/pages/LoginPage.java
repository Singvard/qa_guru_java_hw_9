package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.PageUtils;

public class LoginPage implements ErrorPage {
    private static final String ADDRESS = "/";
    private static final SelenideElement USERNAME = Selenide.$(PageUtils.testId("username"));
    private static final SelenideElement PASSWORD = Selenide.$(PageUtils.testId("password"));
    private static final SelenideElement ERROR = Selenide.$(PageUtils.testId("error"));
    private static final SelenideElement LOGIN_BUTTON = Selenide.$(PageUtils.testId("login-button"));

    @Step("Открыть страницу авторизации.")
    public LoginPage open() {
        Selenide.open(ADDRESS);
        return this;
    }

    @Step("Заполнить поле логина значением: {username}.")
    public LoginPage fillUsername(String username) {
        USERNAME.setValue(username);
        return this;
    }

    @Step("Заполнить поле пароля значением: {password}.")
    public LoginPage fillPassword(String password) {
        PASSWORD.setValue(password);
        return this;
    }

    @Step("Кликнуть кнопку авторизации.")
    public ProductsPage login() {
        LOGIN_BUTTON.submit();
        return new ProductsPage();
    }

    @Step("Кликнуть кнопку авторизации.")
    public LoginPage loginWithError() {
        LOGIN_BUTTON.submit();
        return this;
    }

    public String getErrorMessage(){
        return ERROR.getText();
    }
}
