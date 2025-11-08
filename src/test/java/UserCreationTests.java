import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationTests extends BaseTest {
    User user = new User(EMAIL, NAME, PASSWORD);
    UserRequests usReq = new UserRequests();
    User dummy = new User();

    @After
    public void cleanUp() {
        if (user.isCreated()) {
            usReq.userDelete(user);
        }
        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void userCreateUniqueTest() {
        Response creationResponse = usReq.userCreate(user);
        creationResponse
                .then().log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));

        usReq.userGetToken(creationResponse, user);
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateExistingTest() {
        Response creationResponse = usReq.userCreate(user);
        dummy.setEmail(user.getEmail());
        dummy.setName(NAME + System.currentTimeMillis());
        dummy.setPassword(PASSWORD + System.currentTimeMillis());
        Response dummyCreationResponse = usReq.userCreate(dummy);
        dummyCreationResponse
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("User already exists"));

        usReq.userGetToken(creationResponse, user);
        usReq.userGetToken(dummyCreationResponse, dummy);
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutEmailTest() {
        dummy.setName(NAME);
        dummy.setPassword(PASSWORD);
        Response dummyCreationResponse = usReq.userCreate(dummy);
        dummyCreationResponse
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        usReq.userGetToken(dummyCreationResponse, dummy);
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutNameTest() {
        dummy.setEmail(EMAIL);
        dummy.setPassword(PASSWORD);
        Response dummyCreationResponse = usReq.userCreate(dummy);
        dummyCreationResponse
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        usReq.userGetToken(dummyCreationResponse, dummy);
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Код 403 и сообщение об ошибке")
    public void userCreateWithoutPasswordTest() {
        dummy.setEmail(EMAIL);
        dummy.setName(NAME);
        Response dummyCreationResponse = usReq.userCreate(dummy);
        dummyCreationResponse
                .then().log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message",equalTo("Email, password and name are required fields"));

        usReq.userGetToken(dummyCreationResponse, dummy);
    }
}
