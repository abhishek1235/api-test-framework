package booker.util;

import booker.api.payload.AuthRequestPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static booker.constants.ApiEndpoints.AUTH_ENDPOINT;
import static booker.constants.ApiEndpoints.BASE_URL;
import static io.restassured.RestAssured.given;


public final class AuthApi {
  private AuthApi() {}

  public static Response createToken(AuthRequestPayload authRequestPayload) {
    return given()
        .contentType(ContentType.JSON)
        .body(authRequestPayload)
        .when()
        .post(BASE_URL + AUTH_ENDPOINT);
  }
}
