
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Range;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors attributable to {@link Range}s.
 */
public enum RangeBehavior implements Behavior {

    /** An auto-advance range behavior. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A no-auto-advance range behavior. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** An individual range behavior. */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /** A no-nav range behavior. */
    NO_NAV(BehaviorConstants.NO_NAV),

    /** A continuous range behavior. */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /** A paged range behavior. */
    PAGED(BehaviorConstants.PAGED),

    /** An unordered range behavior. */
    UNORDERED(BehaviorConstants.UNORDERED),

    /** A thumbnail-nav range behavior. */
    THUMBNAIL_NAV(BehaviorConstants.THUMBNAIL_NAV),

    /** A sequence range behavior. */
    SEQUENCE(BehaviorConstants.SEQUENCE);

    /** The logger for the range behavior. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RangeBehavior.class, MessageCodes.BUNDLE);

    /** The string form of the range behavior. */
    private final String myValue;

    /**
     * Creates a new range behavior from the supplied string.
     *
     * @param aBehavior A range behavior in string form
     */
    RangeBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    /**
     * Gets a string representation of the range behavior.
     *
     * @return A string representation of the range behavior
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
     * @return A range behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a range behavior
     */
    public static RangeBehavior fromString(final String aBehavior) {
        for (final RangeBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.RANGE));
    }
}
