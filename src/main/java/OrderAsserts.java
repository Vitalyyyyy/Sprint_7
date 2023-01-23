import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAsserts {
    public void checkBodyOrder(ValidatableResponse response) {
        response.assertThat().log().all().statusCode(201).body("track", is(notNullValue()));
    }
    public void checkBodyList(ValidatableResponse response) {
        response.assertThat().log().all().statusCode(200).body("orders", notNullValue());
    }
}