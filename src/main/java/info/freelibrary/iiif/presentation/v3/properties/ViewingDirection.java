
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The direction that a sequence of canvases should be displayed to the user.
 */
public enum ViewingDirection {

    /** A left-to-right viewing direction. */
    LEFT_TO_RIGHT("left-to-right"),

    /** A right-to-left viewing direction. */
    RIGHT_TO_LEFT("right-to-left"),

    /** A top-to-bottom viewing direction. */
    TOP_TO_BOTTOM("top-to-bottom"),

    /** A bottom-to-top viewing direction. */
    BOTTOM_TO_TOP("bottom-to-top");

    /** The viewing direction's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingDirection.class, MessageCodes.BUNDLE);

    /** The viewing direction's value. */
    private final String myValue;

    /**
     * Creates a new viewing direction from the supplied string.
     *
     * @param aDirection A viewing direction
     */
    ViewingDirection(final String aDirection) {
        myValue = aDirection;
    }

    /**
     * Gets the string representation of the viewing direction.
     */
    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Creates a viewing direction from a supplied string; it may be empty if the supplied string doesn't correspond to
     * a valid viewing direction value.
     *
     * @param aViewingDirection A viewing direction value
     * @return The ViewingDirection for the supplied value
     * @throws IllegalArgumentException If the supplied value isn't a valid viewing direction
     */
    public static ViewingDirection from(final String aViewingDirection) {
        for (final ViewingDirection direction : values()) {
            if (direction.toString().equalsIgnoreCase(aViewingDirection)) {
                return direction;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_016, aViewingDirection));
    }
}
