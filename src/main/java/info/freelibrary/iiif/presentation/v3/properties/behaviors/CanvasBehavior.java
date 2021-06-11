
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors attributable to {@link Canvas}es.
 */
public enum CanvasBehavior implements Behavior {

    /**
     * A auto-advance behavior on a canvas.
     */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), //

    /**
     * A no-auto-advance behavior on a canvas.
     */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), //

    /**
     * A facing-pages behavior on a canvas.
     */
    FACING_PAGES(BehaviorConstants.FACING_PAGES), //

    /**
     * A non-paged behavior on a canvas.
     */
    NON_PAGED(BehaviorConstants.NON_PAGED);

    /**
     * The CanvasBehavior logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasBehavior.class, MessageCodes.BUNDLE);

    /**
     * The value of CanvasBehavior.
     */
    private final String myValue;

    /**
     * Creates a new CanvasBehavior from the supplied behavior in string form.
     *
     * @param aBehavior A canvas behavior in string form
     */
    CanvasBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    /**
     * Gets a string representation of a canvas behavior.
     *
     * @return A string representation of a canvas behavior
     */
    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Maps a behavior string to an enum constant of this type.
     *
     * @param aBehavior A behavior string
     * @return A canvas behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a canvas behavior
     */
    public static CanvasBehavior fromString(final String aBehavior) {
        for (final CanvasBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.CANVAS));
    }
}
