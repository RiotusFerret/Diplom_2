import data.DataBase;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static data.TestData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateTests extends BaseTest{
    User user = new User(EMAIL, NAME, PASSWORD);
    UserRequests usReq = new UserRequests();
    DataBase dataBase = new DataBase();
    OrderRequests ordReq = new OrderRequests();

    @After
    public void cleanUp() {
        if (user.isCreated()) {
            usReq.userDelete(user);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void orderCreateAuthorisedTest() {
        Order order = new Order(List.of(dataBase.availableBuns().get(0).getId(), dataBase.availableIngredients().get(1).getId(), dataBase.availableIngredients().get(10).getId()));
        usReq.userCreate(user);
        usReq.userLogin(user);
        ordReq.orderCreate(order)
                .then().log().all()
                .statusCode(SC_OK)
                .body("name",notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void orderCreateUnauthorisedTest() {
        Order order = new Order(List.of(dataBase.availableBuns().get(0).getId(), dataBase.availableIngredients().get(1).getId(), dataBase.availableIngredients().get(10).getId()));
        ordReq.orderCreate(order)
                .then().log().all()
                .statusCode(SC_OK)
                .body("name",notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Код 500")
    public void orderCreateWithWrongIdsTest() {
        Order order = new Order(List.of(dataBase.availableBuns().get(0).getId() + "1", dataBase.availableIngredients().get(1).getId() + "1", dataBase.availableIngredients().get(10).getId() + "1"));
        ordReq.orderCreate(order)
                .then().log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Код 400 и сообщение об ошибке")
    public void orderCreateWithoutIngredientsTest() {
        Order order = new Order();
        ordReq.orderCreate(order)
                .then().log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }
}
