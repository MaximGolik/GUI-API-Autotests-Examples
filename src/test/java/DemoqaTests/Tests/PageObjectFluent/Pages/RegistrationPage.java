package DemoqaTests.Tests.PageObjectFluent.Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static DemoqaTests.Config.EndPoints.*;

public class RegistrationPage {
    SelenideElement firstNameForm = $(By.xpath("//*[@id=\"firstname\"]"));
    SelenideElement lastNameForm = $(By.xpath("//*[@id=\"lastname\"]"));
    SelenideElement usernameForm = $(By.xpath("//*[@id=\"userName\"]"));
    SelenideElement passwordForm = $(By.xpath("//*[@id=\"password\"]"));
    SelenideElement registerButton = $(By.xpath("//*[@id=\"register\"]"));
    SelenideElement captchaButton = $(By.xpath("//*[@id=\"g-recaptcha\"]/div/div/iframe"));

    public RegistrationPage openRegistrationPage(String URL){
        Selenide.open(URL+ REGISTRATION_PAGE);
        return this;
    }
    public RegistrationPage fillFirstNameForm(String firstName){
        this.firstNameForm.sendKeys(firstName);
        return this;
    }
    public RegistrationPage fillLastNameForm(String lastName){
        this.lastNameForm.sendKeys(lastName);
        return this;
    }
    public RegistrationPage fillUsernameForm(String username){
        this.usernameForm.sendKeys(username);
        return this;
    }
    public RegistrationPage fillPasswordForm(String password){
        this.passwordForm.sendKeys(password);
        return this;
    }
    public RegistrationPage click(){
        this.captchaButton.doubleClick();
        this.registerButton.doubleClick();
        return this;
    }

}
