
package info.freelibrary.iiif.presentation.v3.properties;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A date that clients may use for navigation purposes when presenting the resource to the user in a date-based user
 * interface, such as a calendar or timeline. More descriptive date ranges, intended for display directly to the user,
 * should be included in the metadata property for human consumption. If the resource contains Canvases that have the
 * duration property, the datetime given corresponds to the navigation datetime of the start of the resource. For
 * example, a Range that includes a Canvas that represents a set of video content recording a historical event, the
 * navDate is the datetime of the first moment of the recorded event.
 */
public class NavDate {

    /** The navDate's zoned datetime. */
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

    /**
     * Creates a new navigation date from a string representation of a zoned date time.
     *
     * @param aZonedDateTime A string representation of a zoned date time
     */
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
     * Returns a string representation of the navigation date.
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
