package DemoqaTests.Tests.PageObjectFluent;

import DemoqaTests.Config.ConfigProperties;
import DemoqaTests.Tests.PageObjectFluent.Pages.LoginPage;
import DemoqaTests.Tests.PageObjectFluent.Pages.ProfilePage;
import DemoqaTests.Tests.PageObjectFluent.Pages.RegistrationPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static DemoqaTests.Config.EndPoints.*;

@DisplayName("Page Object Fluent Тестирование https://demoqa.com/")
@Tag("PageObjectFluent")
public class PageObjectFluentTest {
    ConfigProperties configProperties = new ConfigProperties();
    String TEST_FIRST_NAME = ConfigProperties.GetProperty("TEST_FIRST_NAME");
    String TEST_LAST_NAME = ConfigProperties.GetProperty("TEST_SURNAME");
    String TEST_USERNAME = ConfigProperties.GetProperty("TEST_USERNAME");
    String TEST_PASSWORD = ConfigProperties.GetProperty("TEST_PASSWORD");
    String SITE_URL = ConfigProperties.GetProperty("WEBSITE_URL");

    @DisplayName("Тестирование регистрации и авторизации Page Object (Fluent) + тестирование cookie")
    @Tag("Registration")
    @Tag("Login")
    @Tag("PageObjectFluent")
    @Test
    @Order(1)
    public void registrationAndLoginTest(){
        new RegistrationPage()
                .openRegistrationPage(SITE_URL)
                .fillFirstNameForm(TEST_FIRST_NAME)
                .fillLastNameForm(TEST_LAST_NAME)
                .fillUsernameForm(TEST_USERNAME)
                .fillPasswordForm(TEST_PASSWORD)
                .click();
        new LoginPage()
                .openLoginPage(SITE_URL)
                .setLoginField(TEST_USERNAME)
                .setPasswordField(TEST_PASSWORD)
                .click();
        WebDriver firstDriver = WebDriverRunner.getWebDriver();
        WebDriverWait w = new WebDriverWait(firstDriver,Duration.ofSeconds(3));
        String expectedURL = SITE_URL+PROFILE_PAGE;
        w.until(ExpectedConditions.urlToBe(expectedURL));
        String currentUsername = firstDriver.manage().getCookieNamed("userName").getValue();
        assertThat(currentUsername,equalTo(TEST_USERNAME));
    }
    @Test
    @Order(2)
    @DisplayName("Тестирование запоминания через cookies")
    public void userCookiesTest() {
        Selenide.open(SITE_URL + PROFILE_PAGE);
        WebDriver driver = WebDriverRunner.getWebDriver();
        new ProfilePage()
                .openProfilePage(SITE_URL);
        String visibleUsername = new ProfilePage().getUsernameFieldText();
        String cookieUsername = driver.manage().getCookieNamed("userName").getValue();
        assertThat(cookieUsername, equalTo(visibleUsername));
    }
}
