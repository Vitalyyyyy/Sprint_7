import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.client.methods.RequestBuilder.delete;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest{
    private String firstName = "Squirtle";
    private String password = "33333333";
    private String login = RandomStringUtils.randomAlphabetic(8);

    private static String penCreate = "/api/v1/courier";
    private static String penLogin = "/api/v1/courier/login";
    private static String penDelete = "/api/v1/courier/";



    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    @DisplayName("Создание нового курьера (201), позитивный кейс")
    public void createNewCourierAndCheckResponse() {
        Courier courierCreate = new Courier(login, password, firstName);
// Проверяем, что курьер создан:
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(penCreate)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Ошибка создания курьера (400, \"Недостаточно данных для создания учетной записи.\"), cоздание курьера без обязательного поля firstName")
    @Description("/api/v1/courier post: login, password")
    public void createCourierWithoutFirstName() {
        Courier courierCreate = new Courier(login, password);
        given()
                .body(courierCreate)
                .when()
                .post(penCreate)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Ошибка создания курьера (409, \"Этот логин уже используется. Попробуйте другой.\"), создание дубликата курьера.")
    @Description("/api/v1/courier post: login, password, firstName")
    public void createCourierCheckResponse() {
        Courier courierCreate = new Courier(login, password, firstName);
// Проверяем, что курьер создан:
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(penCreate)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
//Проверяем, что нельзя создать дубликат курьера:
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreate)
                .when()
                .post(penCreate)
                .then().assertThat().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        Courier courierDelete = new Courier(login, password, firstName);
        String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierDelete)
                .when()
                .post(penLogin)
                .asString();
        JsonPath jsonPath = new JsonPath(response);
        String userId = jsonPath.getString("id");
        delete(penDelete + userId);
    }
}