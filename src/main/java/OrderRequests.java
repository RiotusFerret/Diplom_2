import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.URLs.ORDER_CREATE;
import static io.restassured.RestAssured.given;

public class OrderRequests {

    public Response orderCreate(Order order) {
        Response orderCreateResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_CREATE);

        if (orderCreateResponse.getStatusCode() == 200) {
           String dummy = orderCreateResponse.jsonPath().getString("order");
           String[] split = dummy.split(":");
           dummy = split[1];
           String[] split2 = dummy.split("]");
           dummy = split2[0];
           order.setNumber(dummy);
           order.setName(orderCreateResponse.jsonPath().getString("name"));
        }
        return orderCreateResponse;
    }
}
