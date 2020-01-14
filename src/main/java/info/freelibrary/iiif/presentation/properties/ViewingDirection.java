
package info.freelibrary.iiif.presentation.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The direction that a sequence of canvases should be displayed to the user. Possible values are specified in the
 * table below.
 */
public enum ViewingDirection {

    LEFT_TO_RIGHT("left-to-right"), RIGHT_TO_LEFT("right-to-left"), TOP_TO_BOTTOM("top-to-bottom"), BOTTOM_TO_TOP(
            "bottom-to-top");

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
     * @param aValue A ViewingDirection value
     * @return The ViewingDirection for the supplied value
     */
    public static Optional<ViewingDirection> fromString(final String aValue) {
        for (final ViewingDirection direction : values()) {
            if (direction.toString().equalsIgnoreCase(aValue)) {
                return Optional.of(direction);
            }
        }

        return Optional.empty();
    }
}
