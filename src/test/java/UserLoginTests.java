import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTests extends BaseTest {
    User user = new User(EMAIL, NAME, PASSWORD);
    UserRequests usReq = new UserRequests();
    User dummy = new User();

    @Before
    public void startUp() {
        Response creationResponse = usReq.userCreate(user);
        usReq.userGetToken(creationResponse, user);
    }

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
    @DisplayName("Логин существующего пользователя")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void userLoginExistingTest() {
        Response loginResponse = usReq.userLogin(user);
        loginResponse
                .then().log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));

        usReq.userGetToken(loginResponse, user);
    }

    @Test
    @DisplayName("Логин пользователя c неверным логином")
    @Description("Код 401 и сообщение об ошибке")
    public void userLoginWrongLoginTest() {
        dummy.setPassword("1" + EMAIL);
        dummy.setName(user.getName());
        dummy.setPassword(user.getPassword());
        Response dummyLoginResponse = usReq.userLogin(dummy);
        dummyLoginResponse
                .then().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message",equalTo("email or password are incorrect"));

        usReq.userGetToken(dummyLoginResponse, dummy);
    }

    @Test
    @DisplayName("Логин пользователя c неверным паролем")
    @Description("Код 401 и сообщение об ошибке")
    public void userLoginWrongPasswordTest() {
        dummy.setPassword(user.getEmail());
        dummy.setName(user.getName());
        dummy.setPassword("1" + user.getPassword());
        Response dummyLoginResponse = usReq.userLogin(dummy);
        dummyLoginResponse
                .then().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message",equalTo("email or password are incorrect"));

        usReq.userGetToken(dummyLoginResponse, dummy);
    }
}
