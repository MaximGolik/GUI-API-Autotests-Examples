package DemoqaTests.Tests.PageObject.Pages;

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

    public void openRegistrationPage(String URL){
        Selenide.open(URL+ REGISTRATION_PAGE);
    }
    public void fillFirstNameForm(String firstName){
        firstNameForm.sendKeys(firstName);
    }
    public void fillLastNameForm(String lastName){
        lastNameForm.sendKeys(lastName);
    }
    public void fillUsernameForm(String username){
        usernameForm.sendKeys(username);
    }
    public void fillPasswordForm(String password){
        passwordForm.sendKeys(password);
    }
    public void clickRegistration(){
        captchaButton.doubleClick();
        registerButton.doubleClick();
    }
}
