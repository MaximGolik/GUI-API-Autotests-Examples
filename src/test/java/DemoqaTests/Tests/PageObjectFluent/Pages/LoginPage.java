package DemoqaTests.Tests.PageObjectFluent.Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static DemoqaTests.Config.EndPoints.*;

public class LoginPage {
    SelenideElement loginField = $(By.xpath("//*[@id=\"userName\"]"));
    SelenideElement passwordField = $(By.xpath("//*[@id=\"password\"]"));
    SelenideElement loginButton = $(By.xpath("//*[@id=\"login\"]"));
    SelenideElement newUserButton = $(By.xpath("//*[@id=\"newUser\"]"));

    public LoginPage openLoginPage(String URL){
        Selenide.open(URL+ LOGIN_PAGE);
        return this;
    }
    public LoginPage setLoginField(String login){
        this.loginField.sendKeys(login);
        return this;
    }
    public LoginPage setPasswordField(String password){
        this.passwordField.sendKeys(password);
        return this;
    }
    public LoginPage click(){
        this.loginButton.doubleClick();
        return this;
    }
}
