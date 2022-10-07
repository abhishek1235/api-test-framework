package tests;

import booker.util.BookingApi;
import booker.constants.StatusCode;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import booker.api.payload.BookingDates;
import booker.api.payload.BookingRequestPayload;
import booker.api.payload.BookingResponsePayload;

import java.text.SimpleDateFormat;
import java.util.Date;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class ApiTest extends BaseTest {
    BookingRequestPayload createBookingRequestPayload() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("None")
                .build();
    }

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
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
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
        Response response = BookingApi.createBooking(createBookingRequestPayload());
        assertThat(response.statusCode(), equalTo(StatusCode.OK));
    }


    /*    Create a new Booking
          Method : POST
          Validation : Validate new Booking Response Payload with Request Payload    */
    @Test
    void testCreateBookingReturnsCorrectDetails() {
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
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
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
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
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
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


    /*    Update Booking price details
          Method : PATCH
          Validation : Response is 200 OK     */
    @Test
    void testPartialUpdateBooking() {
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
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
    void testDeleteBookingReturns201() {
        int id =
                BookingApi.createBooking(createBookingRequestPayload())
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        Response response = BookingApi.deleteBooking(id, token);

        assertThat(response.statusCode(), equalTo(StatusCode.CREATED));
    }
}
