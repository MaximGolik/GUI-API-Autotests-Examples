package ReqresTests.ApiTests;

import ReqresTests.ApiTests.ReqresTestsPOJO.*;
import io.qameta.allure.Epic;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Тестирование API сайта https://reqres.in/api")
public class ReqresTests {
    ReqresEndpoints reqresEndPoints;
    RequestSpecification reqresRequestSpecification = new RequestSpecBuilder()
            .setBaseUri(ReqresEndpoints.basePath)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();
    File jsonSingleUserExpectedRequestSchema = new File("src/test/java/ReqresTests/ApiTests/JsonSchemas/ExpectedSingleUserRequestSchema.json");

    @BeforeAll
    public static void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }
    @Test
    @DisplayName("Получить пользователя")
    public void getSingleUserSuccessfulTest(){
        int userId = 2;
        SingleUserPOJO singleUserSuccessfulResponse = given()
                .spec(reqresRequestSpecification)
                .pathParams("id",userId)
                .when()
                .get(reqresEndPoints.singleUser)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(jsonSingleUserExpectedRequestSchema))
                .extract()
                .jsonPath()
                .getObject("data",SingleUserPOJO.class);
        Assertions.assertEquals(singleUserSuccessfulResponse.getId(),userId);
    }
    @Test
    @DisplayName("Получить список пользователей")
    public void getUsersListSuccessfulTest(){
        int pageNumber = 2;
        List<SingleUserPOJO> usersList = given()
                .spec(reqresRequestSpecification)
                .pathParams("pageNumber",pageNumber)
                .when()
                .get(reqresEndPoints.usersList)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("data",SingleUserPOJO.class);
        for (SingleUserPOJO user : usersList ){
            assertThat(user.getId(),allOf(greaterThan((pageNumber-1)*6-1),lessThanOrEqualTo(pageNumber*6)));
            assertThat(user.getEmail(),endsWith("@reqres.in"));
            assertThat(user.getAvatar(),endsWith("-image.jpg"));
        }
    }

    @Test
    @DisplayName("Не получить пользователя")
    public void getSingleUserUnsuccessfulTest(){
        int userId = 23;
        Response response = given()
                .spec(reqresRequestSpecification)
                .pathParams("id",userId)
                .when()
                .get(reqresEndPoints.singleUser)
                .then()
                .statusCode(404)
                .extract().response();
    }
    @Test
    @DisplayName("Получить список неизвестных")
    public void getUnknownList(){
       List<UnknownPOJO> unknownResourcesList = given()
                .spec(reqresRequestSpecification)
                .when()
                .get(reqresEndPoints.unknownList)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("data",UnknownPOJO.class);
        int j = 1;
        for(UnknownPOJO unknown : unknownResourcesList){
            assertThat(unknown.getId(),equalTo(j));
            j++;
        }
    }
    @Test()
    @DisplayName("Получить одного неизвестного")
    public void getSingleUnknown(){
        int unknownId = 2;
        UnknownPOJO unknownPOJO = given()
                .spec(reqresRequestSpecification)
                .pathParams("id",unknownId)
                .when()
                .get(reqresEndPoints.singleUnknown)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getObject("data",UnknownPOJO.class);
        assertThat(unknownPOJO.getId(),equalTo(unknownId));
    }
    @Test
    @DisplayName("Создать пользователя")
    public void createUserTest(){
        CRUDUserPOJO userData = new CRUDUserPOJO("morpheus","leader");
        given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.createSingleUser)
                .then()
                .statusCode(201)
                .extract().response();
    }
    @Test
    @DisplayName("Обновить данные пользователя через PUT")
    public void putUpdateUser(){
        CRUDUserPOJO userData = new CRUDUserPOJO("morpheus","zion resident");
        int userId = 2;
        given()
                .body(userData)
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .put(reqresEndPoints.singleUser)
                .then()
                .statusCode(200)
                .extract().response();
    }
    @Test
    @DisplayName("Обновить данные пользователя через PATCH")
    public void patchUpdateUse(){
        CRUDUserPOJO userData = new CRUDUserPOJO("morpheus","zion resident");
        int userId = 2;
        given()
                .body(userData)
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .patch(reqresEndPoints.singleUser)
                .then()
                .statusCode(200)
                .extract().response();
    }
    @Test
    @DisplayName("Удалить пользователя")
    public void deleteUser(){
        int userId = 2;
        given()
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .delete(ReqresEndpoints.singleUser)
                .then()
                .statusCode(204);
    }
    @Test
    @DisplayName("Удачно зарегестрировать пользователя")
    public void SuccessfulUserRegister(){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO("eve.holt@reqres.in","pistol");
        Response response = given()
                .spec(reqresRequestSpecification)
                .body(userData)
                .when()
                .post(ReqresEndpoints.register)
                .then()
                .statusCode(200)
                .extract().response();
        String actualToken = response.then().extract().body().jsonPath().getString("token");
        String expectedToken = "QpwL5tke4Pnpja7X4";
        assertThat(actualToken,equalTo(expectedToken));
    }
    @Test
    @DisplayName("Неудачно зарегестрировать пользователя")
    public void UnSuccessfulUserRegister(){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO("sydney@fife");
        Response response = given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.register)
                .then()
                .statusCode(400)
                .extract().response();
    }
    @Test
    @DisplayName("Удачно аутентифицироваться пользователя")
    public void SuccessfulUserLogin(){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO("eve.holt@reqres.in","cityslicka");
        Response response = given()
                .spec(reqresRequestSpecification)
                .body(userData)
                .when()
                .post(reqresEndPoints.login)
                .then()
                .statusCode(200)
                .extract().response();
        String actualToken = response.then().extract().body().jsonPath().getString("token");
        String expectedToken = "QpwL5tke4Pnpja7X4";
        assertThat(actualToken,equalTo(expectedToken));
    }
    @Test
    @DisplayName("Неудачно аутентифицироваться пользователя")
    public void UnSuccessfulUserLogin(){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO("peter@klaven");
        Response response = given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.login)
                .then()
                .statusCode(400)
                .extract()
                .response();
    }
}
