import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTests extends BaseTest {
    User user = new User(EMAIL, NAME, PASSWORD);
    UserRequests usReq = new UserRequests();

    @After
    public void cleanUp() {
        if (user.isCreated()) {
            usReq.userDelete(user);
        }
    }

    @Test
    @DisplayName("Логин существующего пользователя")
    @Description("Код 200 и сообщение в ответе на успешный запрос")
    public void userLoginExistingTest() {
        usReq.userCreate(user);
            usReq.userLogin(user)
                .then().log().all()
                .statusCode(SC_OK)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Логин пользователя c неверным логином")
    @Description("Код 401 и сообщение об ошибке")
    public void userLoginWrongLoginTest() {
        usReq.userCreate(user);
        User dummy = new User("1" + EMAIL, user.getName(), user.getPassword());
        usReq.userLogin(dummy)
                .then().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message",equalTo("email or password are incorrect"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }

    @Test
    @DisplayName("Логин пользователя c неверным паролем")
    @Description("Код 401 и сообщение об ошибке")
    public void userLoginWrongPasswordTest() {
        usReq.userCreate(user);
        User dummy = new User(user.getPassword(), user.getName(), "1" + user.getPassword());
        usReq.userLogin(dummy)
                .then().log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message",equalTo("email or password are incorrect"));

        if (dummy.isCreated()) {
            usReq.userDelete(dummy);
        }
    }



}
