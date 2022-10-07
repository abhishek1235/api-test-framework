package booker.util;

import io.restassured.response.Response;

import static booker.constants.ApiEndpoints.BASE_URL;
import static booker.constants.ApiEndpoints.PING_ENDPOINT;
import static io.restassured.RestAssured.given;


public final class PingApi {
  private PingApi() {}

  public static Response healthCheck() {
    return given().when().get(BASE_URL + PING_ENDPOINT);
  }
}
