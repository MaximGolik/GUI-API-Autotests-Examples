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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
    @ParameterizedTest
    @ValueSource(ints = 2)
    @DisplayName("Получить пользователя + POJO")
    public void getSingleUserSuccessfulTest(int userId){
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
    @ParameterizedTest
    @ValueSource(ints = 2)
    @DisplayName("Получить список пользователей + POJO")
    public void getUsersListSuccessfulTest(int pageNumber){
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

    @ParameterizedTest
    @ValueSource(ints = 23)
    @DisplayName("Не получить пользователя")
    public void getSingleUserUnsuccessfulTest(int userId){
                given()
                .spec(reqresRequestSpecification)
                .pathParams("id",userId)
                .when()
                .get(reqresEndPoints.singleUser)
                .then()
                .statusCode(404);
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
    @ParameterizedTest()
    @ValueSource(ints = 2)
    @DisplayName("Получить одного неизвестного")
    public void getSingleUnknown(int unknownId){
                given()
                .spec(reqresRequestSpecification)
                .pathParams("id",unknownId)
                .when()
                .get(reqresEndPoints.singleUnknown)
                .then()
                .statusCode(200)
                .body("data.id",equalTo(unknownId));
    }
    @ParameterizedTest
    @CsvSource({"morpheus,leader"})
    @DisplayName("Создать пользователя")
    public void createUserTest(String name, String job){
        CRUDUserPOJO userData = new CRUDUserPOJO(name,job);
        given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.createSingleUser)
                .then()
                .statusCode(201);
    }
    @ParameterizedTest
    @CsvSource({"2,morpheus,zion resident"})
    @DisplayName("Обновить данные пользователя через PUT")
    public void putUpdateUser(int userId,String name, String job){
        CRUDUserPOJO userData = new CRUDUserPOJO(name,job);
        given()
                .body(userData)
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .put(reqresEndPoints.singleUser)
                .then()
                .statusCode(200);
    }
    @ParameterizedTest
    @CsvSource({"2,morpheus,zion resident"})
    @DisplayName("Обновить данные пользователя через PATCH")
    public void patchUpdateUse(int userId,String name, String job){
        CRUDUserPOJO userData = new CRUDUserPOJO(name,job);
        given()
                .body(userData)
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .patch(reqresEndPoints.singleUser)
                .then()
                .statusCode(200);
    }
    @ParameterizedTest
    @ValueSource(ints = 2)
    @DisplayName("Удалить пользователя")
    public void deleteUser(int userId){
        given()
                .pathParams("id",userId)
                .spec(reqresRequestSpecification)
                .when()
                .delete(ReqresEndpoints.singleUser)
                .then()
                .statusCode(204);
    }
    @ParameterizedTest
    @CsvSource({"eve.holt@reqres.in,pistol,QpwL5tke4Pnpja7X4"})
    @DisplayName("Удачно зарегестрировать пользователя")
    public void SuccessfulUserRegister(String email,String password,String expectedToken){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO(email,password);
        given()
                .spec(reqresRequestSpecification)
                .body(userData)
                .when()
                .post(ReqresEndpoints.register)
                .then()
                .statusCode(200)
                .body("token",equalTo(expectedToken));
    }
    @ParameterizedTest
    @CsvSource({"sydney@fife"})
    @DisplayName("Неудачно зарегестрировать пользователя")
    public void UnSuccessfulUserRegister(String email){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO(email);
        given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.register)
                .then()
                .statusCode(400);
    }
    @ParameterizedTest
    @CsvSource({"eve.holt@reqres.in,cityslicka,QpwL5tke4Pnpja7X4"})
    @DisplayName("Удачно аутентифицироваться пользователя")
    public void SuccessfulUserLogin(String email,String password,String expectedToken){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO(email,password);
        given()
                .spec(reqresRequestSpecification)
                .body(userData)
                .when()
                .post(reqresEndPoints.login)
                .then()
                .statusCode(200)
                .body("token",equalTo(expectedToken));

    }
    @ParameterizedTest
    @CsvSource({"peter@klaven"})
    @DisplayName("Неудачно аутентифицироваться пользователя")
    public void UnSuccessfulUserLogin(String email){
        UserLoginAndRegistrationPOJO userData
                = new UserLoginAndRegistrationPOJO(email);
        given()
                .body(userData)
                .spec(reqresRequestSpecification)
                .when()
                .post(reqresEndPoints.login)
                .then()
                .statusCode(400);
    }
}
