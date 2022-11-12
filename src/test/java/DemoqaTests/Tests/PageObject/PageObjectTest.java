package DemoqaTests.Tests.PageObject;

import DemoqaTests.Config.ConfigProperties;
import DemoqaTests.Tests.PageObject.Pages.LoginPage;
import DemoqaTests.Tests.PageObject.Pages.RegistrationPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static DemoqaTests.Config.EndPoints.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Page Object тестирование https://demoqa.com/")
@Tag("PageObject")
public class PageObjectTest {
    ConfigProperties configProperties = new ConfigProperties();

    String TEST_FIRST_NAME = configProperties.GetProperty("TEST_FIRST_NAME");
    String TEST_LAST_NAME = configProperties.GetProperty("TEST_SURNAME");
    String TEST_USERNAME = configProperties.GetProperty("TEST_USERNAME");
    String TEST_PASSWORD = configProperties.GetProperty("TEST_PASSWORD");
    String SITE_URL = configProperties.GetProperty("WEBSITE_URL");

    @DisplayName("Тестирование регистрации и авторизации Page Object")
    @Tag("Registration")
    @Tag("Login")
    @Tag("PageObject")
    @org.junit.jupiter.api.Test
    public void registrationAndLoginTest(){
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.openRegistrationPage(SITE_URL);
        registrationPage.fillFirstNameForm(TEST_FIRST_NAME);
        registrationPage.fillLastNameForm(TEST_LAST_NAME);
        registrationPage.fillUsernameForm(TEST_USERNAME);
        registrationPage.fillPasswordForm(TEST_PASSWORD);
        registrationPage.clickRegistration();

        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage(SITE_URL);
        loginPage.setLoginField(TEST_USERNAME);
        loginPage.setPasswordField(TEST_PASSWORD);
        loginPage.clickLoginButton();

        WebDriver firstDriver = WebDriverRunner.getWebDriver();
        WebDriverWait w = new WebDriverWait(firstDriver,3);

        String expectedURL = SITE_URL+ PROFILE_PAGE;
        w.until(ExpectedConditions.urlToBe(expectedURL));
        String currentUsername = firstDriver.manage().getCookieNamed("userName").getValue();
        assertThat(currentUsername,equalTo(TEST_USERNAME));
        Set<Cookie> currentCookie = WebDriverRunner.getWebDriver().manage().getCookies();
        Selenide.closeWebDriver();

        Selenide.open(SITE_URL);
        WebDriver secondDriver = WebDriverRunner.getWebDriver();
        for (Cookie cookie : currentCookie){
            secondDriver.manage().addCookie(cookie);
        }
        Selenide.open(SITE_URL+ PROFILE_PAGE);
        String newCurrentUsername = secondDriver.manage().getCookieNamed("userName").getValue();
        assertThat(newCurrentUsername,equalTo(TEST_USERNAME));
    }
}
