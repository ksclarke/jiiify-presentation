
package info.freelibrary.iiif.presentation.v2.properties;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * The direction that a sequence of canvases should be displayed to the user. Possible values are specified in the table
 * below.
 */
public enum ViewingDirection {

    /**
     * The "left-to-right" viewing direction.
     */
    LEFT_TO_RIGHT("left-to-right"),

    /**
     * The "right-to-left" viewing direction.
     */
    RIGHT_TO_LEFT("right-to-left"),

    /**
     * The "top-to-bottom" viewing direction.
     */
    TOP_TO_BOTTOM("top-to-bottom"),

    /**
     * The "bottom-to-top" viewing direction.
     */
    BOTTOM_TO_TOP("bottom-to-top");

    /**
     * The viewing direction's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingDirection.class, MessageCodes.BUNDLE);

    /**
     * The viewing direction's value.
     */
    private final String myValue;

    /**
     * Creates a new viewing direction.
     *
     * @param aDirection A strong form of a viewing direction
     */
    ViewingDirection(final String aDirection) {
        myValue = aDirection;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Create a ViewingDirection from a supplied string; it may be empty if the supplied string doesn't correspond to a
     * valid ViewingDirection value.
     *
     * @param aValue A ViewingDirection value
     * @return The ViewingDirection for the supplied value
     * @throws IllegalArgumentException If the supplied value isn't a viewing direction
     */
    public static ViewingDirection fromString(final String aValue) {
        for (final ViewingDirection direction : values()) {
            if (direction.toString().equalsIgnoreCase(aValue)) {
                return direction;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_016, aValue));
    }
}
