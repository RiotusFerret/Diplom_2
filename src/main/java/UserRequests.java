import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class UserRequests {
    public Response userCreate(User user) {
          Response creationResponse = given()
                  .log().all()
                  .contentType(ContentType.JSON)
                  .body(user)
                  .when()
                  .post(USER_CREATE);
          if (creationResponse.getStatusCode() == 200)  {
              user.setCreated(true);
           user.setAccessToken(creationResponse.jsonPath().getString("accessToken"));
         user.setRefreshToken(creationResponse.jsonPath().getString("refreshToken"));
          }
          return creationResponse;
    }

    public Response userLogin(User user) {
        Response loginResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_LOGIN);
        if (loginResponse.getStatusCode() == 200)  {
            user.setAccessToken(loginResponse.jsonPath().getString("accessToken"));
            user.setRefreshToken(loginResponse.jsonPath().getString("refreshToken"));
        }
        return loginResponse;
    }

    public void userDelete(User user) {
        given()
                .log().all()
                .header("Authorization", user.getAccessToken())
                .delete(USER_DELETE).then().statusCode(202);
    }
}
