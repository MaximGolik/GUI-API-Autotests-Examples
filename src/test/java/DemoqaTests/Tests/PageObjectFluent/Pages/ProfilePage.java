package DemoqaTests.Tests.PageObjectFluent.Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static DemoqaTests.Config.EndPoints.PROFILE_PAGE;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    SelenideElement usernameField = $(By.xpath("//*[@id=\"userName-value\"]"));

    public ProfilePage openProfilePage(String URL){
        Selenide.open(URL+PROFILE_PAGE);
        return this;
    }
    public String getUsernameFieldText(){
        return usernameField.getText();
    }
}
