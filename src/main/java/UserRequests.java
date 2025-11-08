import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserRequests {
    @Step("Запрос на создание пользователя")
    public Response userCreate(User user) {
          Response creationResponse = given()
                  .log().all()
                  .contentType(ContentType.JSON)
                  .body(user)
                  .when()
                  .post(USER_CREATE);
          return creationResponse;
    }

    @Step("Запрос на авторизацию пользователя")
    public Response userLogin(User user) {
        Response loginResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_LOGIN);
        return loginResponse;
    }

    @Step("Запрос на получения токена доступа для удаления поль-ля после теста")
    public void userGetToken(Response response, User user) {
        if (response.getStatusCode() == SC_OK)  {
                user.setCreated(true);
                user.setAccessToken(response.jsonPath().getString("accessToken"));
                user.setRefreshToken(response.jsonPath().getString("refreshToken"));
             }
    }

    @Step("Запрос на удаление пользователя")
    public void userDelete(User user) {
        given()
                .log().all()
                .header("Authorization", user.getAccessToken())
                .delete(USER_DELETE).then().statusCode(SC_ACCEPTED);
    }
}
