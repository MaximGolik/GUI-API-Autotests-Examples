package DemoqaTests.Tests.Hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class Hooks {
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false; // видим ли браузер во время теста, false = да
        Configuration.proxyEnabled = true;
    }
    @BeforeEach
    public void init(){
        setUp();
    }
    @AfterEach
    public void turnDown(){
        Selenide.closeWebDriver();
    }
}
