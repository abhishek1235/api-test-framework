package tests;

import booker.api.payload.BookingRequestFactory;
import booker.api.payload.BookingRequestPayload;
import booker.api.payload.BookingResponsePayload;
import booker.constants.StatusCode;
import booker.util.BookingApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class ApiTest extends BaseTest {

    /*    Get All Booking IDs
          Method : Get
          Validation : Response is 200 OK    */
    @Test
    void testGetAllBookingIds() {
        Response response = BookingApi.getAllBookingIds();
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Get All Booking IDs
          Method : Get
          Validation : Response is a non-empty array   */
    @Test
    void testGetAllBookingIdsReturnsNonEmptyArray() {
        BookingResponsePayload[] bookingResponsePayload =
                BookingApi.getAllBookingIds().as(BookingResponsePayload[].class);
        assertThat(bookingResponsePayload.length, greaterThan(0));
    }

    /*    Get All Booking IDs by Name
          Method : Get
          Validation : Response is 200 OK    */
    @Test
    void testGetBookingIdsByName() {
        Response response = BookingApi.getBookingIdsByName("sally", "brown");
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Get All Booking IDs by Date
          Method : Get
          Validation : Response is 200 OK    */

    @Test
    void testGetBookingIdsByDate() {
        Response response = BookingApi.getBookingIdsByDate("2014-03-13", "2014-05-21");
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Get Booking by booking id
          Method : Get
          Validation : Response is 200 OK    */
    @Test
    void testGetBookingById() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        Response response = BookingApi.getBookingById(id);
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Create a new Booking
          Method : POST
          Validation : Response is 200 OK    */

    @Test
    void testCreateBooking() {
        Response response = BookingApi.createBooking(BookingRequestFactory.createBookingRequestPayload(faker));
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }


    /*    Create a new Booking
          Method : POST
          Validation : Validate new Booking Response Payload with Request Payload    */
    @Test
    void testCreateBookingReturnsCorrectDetails() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        BookingResponsePayload bookingResponsePayload =
                BookingApi.createBooking(bookingRequestPayload).as(BookingResponsePayload.class);

        assertThat(
                bookingRequestPayload.equals(bookingResponsePayload.getBookingRequestPayload()), is(true));
    }


    /*    Update Booking price details
          Method : POST
          Validation : Response is 200 OK     */
    @Test
    void testUpdateBooking() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));

        Response response = BookingApi.updateBooking(bookingRequestPayload, id, token);

        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Update Booking firstname,lastname and price details
          Method : PUT
          Validation : Validate updated Booking Response Payload with Request Payload     */
    @Test
    void testUpdateBookingReturnsCorrectDetails() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        bookingRequestPayload.setFirstName(faker.name().firstName());
        bookingRequestPayload.setLastName(faker.name().lastName());
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));

        BookingRequestPayload bookingResponsePayload =
                BookingApi.updateBooking(bookingRequestPayload, id, token).as(BookingRequestPayload.class);

        assertThat(bookingRequestPayload.equals(bookingResponsePayload), is(true));
    }

    /*    Update Booking firstname,lastname and price details without token
          Method : PUT
          Validation : Validate output is forbidden 403   */
    @Test
    void testUpdateBookingWithNoToken() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        bookingRequestPayload.setFirstName(faker.name().firstName());
        bookingRequestPayload.setLastName(faker.name().lastName());
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));

        Response response =
                BookingApi.updateBookingWithoutToken(bookingRequestPayload, id);

        assertThat(response.statusCode(), equalTo(StatusCode.FORBIDDEN));
    }


    /*    Update Booking price details
          Method : PATCH
          Validation : Response is 200 OK     */
    @Test
    void testPartialUpdateBooking() {
        BookingRequestPayload bookingRequestPayload = BookingRequestFactory.createBookingRequestPayload(faker);
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));

        Response response = BookingApi.partialUpdateBooking(bookingRequestPayload, id, token);

        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }

    /*    Delete Booking by booking id
          Method : DELETE
          Validation : Response is 201 OK     */

    @Test
    void testDeleteBooking() {
        int id =
                BookingApi.createBooking(BookingRequestFactory.createBookingRequestPayload(faker))
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        Response response = BookingApi.deleteBooking(id, token);

        assertThat(response.statusCode(), equalTo(StatusCode.CREATED));
    }


     /*   Delete Booking by booking id
          Method : DELETE
          Validation : Response is 201 OK for first delete and 405 for second delete     */

    @Test
    void testDeleteABookingReturnsTwice() {
        int id =
                BookingApi.createBooking(BookingRequestFactory.createBookingRequestPayload(faker))
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        Response response = BookingApi.deleteBooking(id, token);
        assertThat(response.statusCode(), equalTo(StatusCode.CREATED));
        Response response1 = BookingApi.deleteBooking(id, token);
        assertThat(response1.statusCode(), equalTo(StatusCode.METHOD_NOT_ALLOWED));
    }

   /* Create a new Booking with checkout before checkin date
      Method : POST
      Validation : Response is not 200 OK    */
   // Should Fail
    @Test
    void testCreateInvalidDateRangeBooking() throws ParseException {
        Response response = BookingApi.createBooking(BookingRequestFactory.createInvalidDateRangeBookingRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }


   /* Create a new Booking with Blank First name
      Method : POST
      Validation : Response is not 200 OK    */
   // Should Fail
    @Test
    void testCreateInvalidFirstNameBooking() {
        Response response = BookingApi.createBooking(BookingRequestFactory.createInvalidFirstNameBookingRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }

    /* Create a new Booking with Invalid Last name
   Method : POST
   Validation : Response is not 200 OK    */
    // Should Fail
    @Test
    void testCreateInvalidLastNameBooking(){
        Response response = BookingApi.createBooking(BookingRequestFactory.createInvalidLastNameBookingRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }

    /* Create a new Booking with Invalid(Negative) Total Price
   Method : POST
   Validation : Response is not 200 OK    */
    // Should Fail
    @Test
    void testCreateInvalidTotalPriceBooking(){
        Response response = BookingApi.createBooking(BookingRequestFactory.createBookingInvalidTotalPriceRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }

    /* Create a new Booking with No Deposit paid
   Method : POST
   Validation : Response is not 200 OK    */
    // Should Fail
    @Test
    void testCreateDepositNotPaidBooking() {
        Response response = BookingApi.createBooking(BookingRequestFactory.createBookingDepositNotPaidRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }


    /* Create a new Booking with No Additional Need
        Method : POST
        Validation : Response is not 200 OK    */
    // Should Fail
    @Test
    void testCreateBlankAdditionalNeedBooking(){
        Response response = BookingApi.createBooking(BookingRequestFactory.createBookingBlankAdditionalNeedRequestPayload(faker));
        assertThat(response.statusCode(), not(StatusCode.OK));
    }

}
