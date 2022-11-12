package DemoqaTests.Tests.PageFactory.Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static DemoqaTests.Config.EndPoints.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    @FindBy(xpath = "//*[@id=\"userName\"]")
    WebElement loginField;
    @FindBy(xpath = "//*[@id=\"password\"]")
    WebElement passwordField;
    @FindBy(xpath = "//*[@id=\"login\"]")
    WebElement loginButton;
    @FindBy(xpath = "//*[@id=\"newUser\"]")
    WebElement newUserButton;
    @FindBy(xpath = "//*[@id=\"userName-value\"]")
    WebElement currentUsernameFrame;

    WebDriver driver = WebDriverRunner.getWebDriver();
    WebDriverWait w = new WebDriverWait(driver,3);

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
        this.loginButton.click();
        return this;
    }
    public String getCurrentUsername(){
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userName-value\"]")));
        return currentUsernameFrame.getText();
    }
}
