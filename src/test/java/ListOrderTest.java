import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class ListOrderTest {
    private StepOrder step = new StepOrder();
    private OrderAsserts check = new OrderAsserts();

    @Test
    @DisplayName("Список заказов возвращается (200)")
    public void checkBody() {
        ValidatableResponse response = step.getOrderList();
        check.checkBodyList(response);
    }
}