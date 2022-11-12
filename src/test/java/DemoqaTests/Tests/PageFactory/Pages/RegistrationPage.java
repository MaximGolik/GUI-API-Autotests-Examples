package DemoqaTests.Tests.PageFactory.Pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static DemoqaTests.Config.EndPoints.*;

public class RegistrationPage {
    @FindBy(xpath = "//*[@id=\"firstname\"]")
    WebElement firstNameForm;
    @FindBy(xpath = "//*[@id=\"lastname\"]")
    WebElement lastNameForm;
    @FindBy(xpath = "//*[@id=\"userName\"]")
    WebElement usernameForm;
    @FindBy(xpath = "//*[@id=\"password\"]")
    WebElement passwordForm;
    @FindBy(xpath = "//*[@id=\"register\"]")
    WebElement registerButton;
    @FindBy(xpath = "//*[@id=\"g-recaptcha\"]/div/div/iframe")
    WebElement captchaButton ;

    private WebDriver driver;

    public RegistrationPage(WebDriver driver){
        this.driver = driver;
    }

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
        this.captchaButton.click();
        this.registerButton.click();
        return this;
    }

}
