import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static constants.URLs.USER_DELETE;
import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationTests extends BaseTest{
    User user = new User(EMAIL, NAME, PASSWORD);
    UserRequests usReq = new UserRequests();

    @After
    public void cleanUp() {
        if (user.isCreated()) {
            usReq.userDelete(user);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void userCreateUniqueTest() {
        usReq.userCreate(user)
                .then().log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateExistingTest() {
        usReq.userCreate(user);
        User dummy = new User(user.getEmail(), NAME + System.currentTimeMillis(), PASSWORD + System.currentTimeMillis());
        usReq.userCreate(dummy)
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("User already exists"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutEmailTest() {
        User dummy = new User();
        dummy.setName(NAME);
        dummy.setPassword(PASSWORD);
        usReq.userCreate(dummy)
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutNameTest() {
        User dummy = new User();
        dummy.setEmail(EMAIL);
        dummy.setPassword(PASSWORD);
        usReq.userCreate(dummy)
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutPasswordTest() {
        User dummy = new User();
        dummy.setEmail(EMAIL);
        dummy.setName(NAME);
        usReq.userCreate(dummy)
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }


}
