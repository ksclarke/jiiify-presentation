
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public enum RangeBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), INDIVIDUALS(
            BehaviorConstants.INDIVIDUALS), NO_NAV(BehaviorConstants.NO_NAV), CONTINUOUS(BehaviorConstants.CONTINUOUS),
    PAGED(BehaviorConstants.PAGED), UNORDERED(BehaviorConstants.UNORDERED), THUMBNAIL_NAV(
            BehaviorConstants.THUMBNAIL_NAV), SEQUENCE(BehaviorConstants.SEQUENCE);

    private static final Logger LOGGER = LoggerFactory.getLogger(RangeBehavior.class, MessageCodes.BUNDLE);

    private final String myValue;

    RangeBehavior(final String aBehavior) {
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
     * @return A range behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a range behavior
     */
    public static RangeBehavior fromString(final String aBehavior) throws IllegalArgumentException {
        for (final RangeBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.RANGE));
    }
}
