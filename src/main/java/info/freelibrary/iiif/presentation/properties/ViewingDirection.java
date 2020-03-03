
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * The direction that a sequence of canvases should be displayed to the user. Possible values are specified in the
 * table below.
 */
public enum ViewingDirection {

    LEFT_TO_RIGHT("left-to-right"), RIGHT_TO_LEFT("right-to-left"), TOP_TO_BOTTOM("top-to-bottom"), BOTTOM_TO_TOP(
            "bottom-to-top");

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingDirection.class, Constants.BUNDLE_NAME);

    private final String myValue;

    ViewingDirection(final String aDirection) {
        myValue = aDirection;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Create a ViewingDirection from a supplied string; it may be empty if the supplied string doesn't correspond to
     * a valid ViewingDirection value.
     *
     * @param aString A ViewingDirection value
     * @return The ViewingDirection for the supplied value
     * @throws IllegalArgumentException If the supplied value isn't a viewing direction
     */
    public static ViewingDirection fromString(final String aString) {
        for (final ViewingDirection direction : values()) {
            if (direction.toString().equalsIgnoreCase(aString)) {
                return direction;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_016, aString));
    }
}
