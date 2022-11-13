package ReqresTests.ApiTests.CucumberTests;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CucumberStepsDefs {

    Response response;
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();
    @BeforeAll
    public static void before_or_after_all(){
        RestAssured.filters(new AllureRestAssured());
    }
    @When("Отправляем GET запрос на URL {string}, c id={int}")
    public void getUserById(String url, int userId) {
        response = given()
                .spec(requestSpecification)
                .pathParams("id",userId)
                .when()
                .get(url)
                .then()
                .extract().response();
    }
    @Then("Получаем statusCode={int} и пользователя с userId={int}")
    public void matchStatusCodeAndUserId(int statusCode,int userId){
        response
                .then()
                .statusCode(statusCode)
                .body("data.id",equalTo(userId));
    }
    @Then("Получаем statusCode={int}")
    public void matchStatusCode(int statusCode){
        response
                .then()
                .statusCode(statusCode);
    }
}
