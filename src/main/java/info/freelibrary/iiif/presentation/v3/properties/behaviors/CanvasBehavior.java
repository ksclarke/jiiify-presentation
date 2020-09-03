
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public enum CanvasBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), FACING_PAGES(
            BehaviorConstants.FACING_PAGES), NON_PAGED(BehaviorConstants.NON_PAGED);

    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasBehavior.class, MessageCodes.BUNDLE);

    private final String myValue;

    CanvasBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

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
    public static CanvasBehavior fromString(final String aBehavior) throws IllegalArgumentException {
        for (final CanvasBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.CANVAS));
    }
}
