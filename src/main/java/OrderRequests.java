import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.URLs.ORDER_CREATE;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class OrderRequests {

    @Step("Запрос на создание заказа")
    public Response orderCreate(Order order) {
        Response orderCreateResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_CREATE);
        return orderCreateResponse;
    }

    @Step("Запись номера и имени в заказ")
    public void orderSetNumberAndName(Response response, Order order) {
        if (response.getStatusCode() == SC_OK) {
            String dummy = response.jsonPath().getString("order");
            String[] split = dummy.split(":");
            dummy = split[1];
            String[] split2 = dummy.split("]");
            dummy = split2[0];
            order.setNumber(dummy);
            order.setName(response.jsonPath().getString("name"));
        }
    }
}
