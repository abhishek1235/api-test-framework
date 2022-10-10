package booker.api.payload;

import net.datafaker.Faker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingRequestFactory {

    public static BookingRequestPayload createBookingRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }

    public static BookingRequestPayload createInvalidDateRangeBookingRequestPayload(Faker faker) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        String checkoutDate = sdfDate.format(sdfDate.parse("2017-06-01"));
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(checkoutDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }


    public static BookingRequestPayload createInvalidFirstNameBookingRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName("")
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }

    public static BookingRequestPayload createInvalidLastNameBookingRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName("")
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }

    public static BookingRequestPayload createBookingInvalidTotalPriceRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(-100, -500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }


    public static BookingRequestPayload createBookingDepositNotPaidRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(false)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("Breakfast")
                .build();
    }

    public static BookingRequestPayload createBookingBlankAdditionalNeedRequestPayload(Faker faker) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("")
                .build();
    }
}
