package tests;

import booker.util.AuthApi;
import booker.util.PingApi;
import booker.api.payload.AuthRequestPayload;
import booker.api.payload.AuthResponsePayload;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import booker.constants.StatusCode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaseTest {
  protected String token;
  protected final Faker faker = new Faker();

  @BeforeAll
  void testHealthCheck() {
    Response response = PingApi.healthCheck();
    assertThat(response.statusCode(), equalTo(StatusCode.CREATED));
  }

  @BeforeEach
  void testCreateToken() {
    AuthRequestPayload authRequestPayload =
        AuthRequestPayload.builder().username("admin").password("password123").build();

    Response response = AuthApi.createToken(authRequestPayload);
    token = response.as(AuthResponsePayload.class).getToken();

    assertThat(response.statusCode(), equalTo(StatusCode.OK));
  }

  @Test
  void testCreateTokenReturnsNonEmptyToken() {
    assertThat(token, is(not(emptyString())));
  }
}
