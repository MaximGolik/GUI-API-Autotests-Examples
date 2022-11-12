package DemoqaTests.Tests.API;

import DemoqaTests.Config.ConfigProperties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static DemoqaTests.Tests.API.DemoqaEndpoints.*;
import static io.restassured.RestAssured.given;

public class CookieResponse {
    ConfigProperties configProperties = new ConfigProperties();
    String SITE_URL = configProperties.GetProperty("WEBSITE_URL");

    RequestSpecification reqresRequestSpecification = new RequestSpecBuilder()
            .setBaseUri(SITE_URL)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();
    public Set<Cookie> apiLoginCookies(String username, String password){
        Map<String,String> userData = new HashMap<>();
        userData.put("userName",username);
        userData.put("password",password);

        Response response = given()
                .spec(reqresRequestSpecification)
                .body(userData)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        Cookie userId_cookie = new Cookie("userID",
                response.then().extract().body().jsonPath().getString("userId"));
        Cookie username_cookie = new Cookie("userName",
                response.then().extract().body().jsonPath().getString("username"));
        Cookie token_cookie = new Cookie("token",
                response.then().extract().body().jsonPath().getString("token"));
        Cookie expires_token = new Cookie("expires",
                response.then().extract().body().jsonPath().getString("expires"));
        Set<Cookie> testCookie = new HashSet<>();
        testCookie.add(userId_cookie);
        testCookie.add(username_cookie);
        testCookie.add(token_cookie);
        testCookie.add(expires_token);
        return testCookie;
    }
}
