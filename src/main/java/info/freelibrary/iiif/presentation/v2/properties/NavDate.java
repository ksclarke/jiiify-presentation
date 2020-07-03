
package info.freelibrary.iiif.presentation.v2.properties;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A date that the client can use for navigation purposes when presenting the resource to the user in a time-based
 * user interface, such as a calendar or timeline. The value must be an xsd:dateTime literal in UTC, expressed in the
 * form “YYYY-MM-DDThh:mm:ssZ”. If the exact time is not known, then “00:00:00” should be used. Similarly, the month
 * or day should be 01 if not known. There must be at most one navDate associated with any given resource. More
 * descriptive date ranges, intended for display directly to the user, should be included in the metadata property for
 * human consumption.
 */
public class NavDate {

    private final String myZonedDateTime;

    /**
     * Creates a new navigation date.
     *
     * @param aZonedDateTime A zoned date time
     */
    public NavDate(final ZonedDateTime aZonedDateTime) {
        final ZonedDateTime zdt = aZonedDateTime.withZoneSameInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);

        myZonedDateTime = zdt.format(DateTimeFormatter.ISO_INSTANT);
    }

    private NavDate(final String aZonedDateTime) {
        myZonedDateTime = aZonedDateTime;
    }

    /**
     * Creates a new navigation date.
     *
     * @return A new <code>NavDate</code>
     */
    public static final NavDate now() {
        final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
        return new NavDate(now.format(DateTimeFormatter.ISO_INSTANT));
    }

    /**
     * Returns the navigation date in string form.
     *
     * @return A string representation of the navigation date
     */
    @JsonValue
    public String getString() {
        return myZonedDateTime;
    }

    /**
     * Returns the navigation date in zoned date time form.
     *
     * @return A zoned date time representation of the navigation date
     */
    @JsonIgnore
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.parse(myZonedDateTime);
    }

}
