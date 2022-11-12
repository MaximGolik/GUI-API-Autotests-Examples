package DemoqaTests.Tests.PageObject.Pages;

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

    public void openLoginPage(String URL){
        Selenide.open(URL+ LOGIN_PAGE);
    }
    public void setLoginField(String login){
        loginField.sendKeys(login);
    }
    public void setPasswordField(String password){
        passwordField.sendKeys(password);
    }
    public void clickLoginButton(){
        loginButton.doubleClick();
    }
    public void clickNewUserButton(){
        newUserButton.doubleClick();
    }
}
