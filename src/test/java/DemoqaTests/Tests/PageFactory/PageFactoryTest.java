package DemoqaTests.Tests.PageFactory;

import DemoqaTests.Config.ConfigProperties;
import DemoqaTests.Tests.PageFactory.Pages.LoginPage;
import DemoqaTests.Tests.PageFactory.Pages.RegistrationPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static DemoqaTests.Config.EndPoints.*;

@DisplayName("Page Factory тестирование https://demoqa.com/")
@Tag("PageFactory")
public class PageFactoryTest {
    ConfigProperties configProperties = new ConfigProperties();

    String TEST_FIRST_NAME = configProperties.GetProperty("TEST_FIRST_NAME");
    String TEST_LAST_NAME = configProperties.GetProperty("TEST_SURNAME");
    String TEST_USERNAME = configProperties.GetProperty("TEST_USERNAME");
    String TEST_PASSWORD = configProperties.GetProperty("TEST_PASSWORD");
    String SITE_URL = configProperties.GetProperty("WEBSITE_URL");

    @DisplayName("Тестирование регистрации и авторизации Page Factory + тестирование cookie")
    @Tag("Registration")
    @Tag("Login")
    @Tag("PageFactory")
    @org.junit.jupiter.api.Test
    public void registrationAndLoginTest() {
        Selenide.open(SITE_URL+ REGISTRATION_PAGE);
        WebDriver firstDriver = WebDriverRunner.getWebDriver();
        WebDriverWait w = new WebDriverWait(firstDriver,3);

        RegistrationPage registrationPage = PageFactory.initElements(firstDriver,RegistrationPage.class)
                .fillFirstNameForm(TEST_FIRST_NAME)
                .fillLastNameForm(TEST_LAST_NAME)
                .fillUsernameForm(TEST_USERNAME)
                .fillPasswordForm(TEST_PASSWORD)
                .click();

        Selenide.open(SITE_URL+ LOGIN_PAGE);
        LoginPage loginPage = PageFactory.initElements(firstDriver,LoginPage.class)
                .setLoginField(TEST_USERNAME)
                .setPasswordField(TEST_PASSWORD)
                .click();

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
        assertThat(secondDriver.getCurrentUrl(),equalTo(SITE_URL+ PROFILE_PAGE));
        Selenide.closeWebDriver();
    }
}
