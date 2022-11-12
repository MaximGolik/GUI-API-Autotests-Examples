package DemoqaTests.Tests.API;

import DemoqaTests.Tests.PageObjectFluent.Pages.ProfilePage;
import DemoqaTests.Config.ConfigProperties;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тестирование https://demoqa.com используя Rest Assured и Selenide")
public class RestAssuredTests {
    ConfigProperties configProperties = new ConfigProperties();
    CookieResponse cookieResponse = new CookieResponse();

//    String TEST_FIRST_NAME = configProperties.GetProperty("TEST_FIRST_NAME");
//    String TEST_LAST_NAME = configProperties.GetProperty("TEST_SURNAME");
    String TEST_USERNAME = configProperties.GetProperty("TEST_USERNAME");
    String TEST_PASSWORD = configProperties.GetProperty("TEST_PASSWORD");
    String SITE_URL = configProperties.GetProperty("WEBSITE_URL");

    @Test
    @DisplayName("Аутентефикация через API сайта минуя UI")
    public void apiLoginTest(){
        Set<Cookie> testCookie = cookieResponse.apiLoginCookies(TEST_USERNAME,TEST_PASSWORD);
        Selenide.open(SITE_URL);
        WebDriver driver = WebDriverRunner.getWebDriver();
        for(Cookie cookie : testCookie){
            driver.manage().addCookie(cookie);
        }
        String visibleUsername = new ProfilePage().openProfilePage(SITE_URL).getUsernameFieldText();
        assertThat(TEST_USERNAME,equalTo(visibleUsername));
    }
}
