import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
public class LoginCourierTest {
    static private String login = RandomStringUtils.randomAlphabetic(10);
    static private String passwordTest = "4324434";
    static private String firstName = "Squirtle";
    static private String endPointCreate = "/api/v1/courier";
    static private String endPointLogin = "/api/v1/courier/login";
    static private String endPointDelete = "/api/v1/courier/";
    // Проверяем, что курьер создан для тестов:
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        Courier courierCreate  = new Courier(login, passwordTest, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(endPointCreate)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        Courier courierDelete  = new Courier(login, passwordTest);
// Удаляем курьера после тестов:
        String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierDelete)
                .when()
                .post(endPointLogin)
                .asString();

        JsonPath jsonPath = new JsonPath(response);
        String userId = jsonPath.getString("id");
        delete(endPointDelete + userId);
    }

    @Test
    @DisplayName("Авторизация (200), позитивный кейс")
    @Description("/api/v1/courier/login post: login, password")
    public void loginCourierAndCheckResponse(){
        Courier courierLogin  = new Courier(login, passwordTest);
// Проверяем, что курьер создан:
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post(endPointLogin)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("id", notNullValue());;
    }

    @Test
    @DisplayName("Ошибка авторизации (400, \"Учетная запись не найдена\"), авторизация без  поля password")
    // Expected status code <400> but was <504>
    @Description("/api/v1/courier/login post: password null")
    public  void loginCourierWithoutPassword() {
        Courier courierCreate  = new Courier(login, "");
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа")); //Service unavailable
    }
    @Test
    @DisplayName("Ошибка авторизации (404, \"Учетная запись не найдена\"), авторизация с неверным паролем.")
    // Expected status code <404> but was <504>
    @Description("/api/v1/courier/login post: wrong password")
    public  void loginCourierWithWrongPassword() {
        Courier courierCreate  = new Courier(login, passwordTest + "mistake");
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Ошибка авторизации (404, \"Учетная запись не найдена\"), авторизация с неверным логином.")
    @Description("/api/v1/courier/login post: wrong login")
    public  void loginCourierWithWrongLogin() {
        Courier courierCreate  = new Courier(login + "mistake", passwordTest);
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Ошибка авторизации (404, \"Учетная запись не найдена\"), авторизация с несуществующим данными")
    @Description("/api/v1/courier/login post: wrong login and password")
    public  void loginCourierWithWrongLoginAndPassword() {
        Courier courierCreate  = new Courier(login + "mistake", passwordTest + "mistake");
        given()
                .body(courierCreate)
                .when()
                .post(endPointLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}