import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private StepOrder step;
    private OrderAsserts check;
    private String[] color;



    int track;

    public CreateOrderTest(String[] color) {
        this.color = color;

    }

    @Before
    public void preconditions() {
        step = new StepOrder();
        check = new OrderAsserts();
    }

    @Parameterized.Parameters(name = "scooter's color")
    public static Object[][] valueColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{}}
        };
    }
    @Test
    @DisplayName("Создание заказа (201). Присвоение \"color\" значений GRAY/BLACK + тело ответа содержит track")
    @Step("Create order with different colors")
    public void createTest() {
        Order orderColor = new Order(color);
        ValidatableResponse response = step.createOrder(orderColor);
        check.checkBodyOrder(response);
        track = response.extract().path("track");
        response.statusCode(201);
    }
    @After
    public void deleteOrder(){
        step.cancelOrder(track);
    }
}