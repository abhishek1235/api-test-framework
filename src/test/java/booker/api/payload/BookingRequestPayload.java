package booker.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Jacksonized
@Builder
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequestPayload {
  @JsonProperty("firstname")
  private String firstName;

  @JsonProperty("lastname")
  private String lastName;

  @JsonProperty("totalprice")
  private int totalPrice;

  @JsonProperty("depositpaid")
  private boolean depositPaid;

  @JsonProperty("bookingdates")
  private BookingDates bookingDates;

  @JsonProperty("additionalneeds")
  private String additionalNeeds;
}
