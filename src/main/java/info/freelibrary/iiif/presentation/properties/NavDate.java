
package info.freelibrary.iiif.presentation.properties;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A date that the client can use for navigation purposes.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
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
