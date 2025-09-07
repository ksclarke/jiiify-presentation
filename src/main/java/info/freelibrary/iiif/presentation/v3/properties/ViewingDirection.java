
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Labeled;

import java.util.Optional;

/**
 * The direction that a sequence of canvases should be displayed to the user.
 */
public enum ViewingDirection implements Labeled {

    /** A bottom-to-top viewing direction. */
    BOTTOM_TO_TOP("bottom-to-top"),

    /** A left-to-right viewing direction. */
    LEFT_TO_RIGHT("left-to-right"),

    /** A right-to-left viewing direction. */
    RIGHT_TO_LEFT("right-to-left"),

    /** A top-to-bottom viewing direction. */
    TOP_TO_BOTTOM("top-to-bottom");

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
     * Gets the viewingDirection label.
     *
     * @return the viewingDirection label
     */
    @Override
    public String label() {
        return myValue;
    }

    /**
     * Gets the string representation of the viewing direction.
     *
     * @return A string representation of the ViewingDirection
     */
    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Creates a viewing direction from a supplied string; if the supplied string isn't a valid viewing direction, an
     * empty Optional is returned.
     *
     * @param aViewingDirection A viewing direction value
     * @return The ViewingDirection for the supplied value or an empty Optional
     */
    public static Optional<ViewingDirection> fromLabel(final String aViewingDirection) {
        for (final ViewingDirection direction : values()) {
            if (direction.toString().equalsIgnoreCase(aViewingDirection)) {
                return Optional.of(direction);
            }
        }

        return Optional.empty();
    }
}
