
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors attributable to {@link Collection}s.
 */
public enum CollectionBehavior implements Behavior {

    /**
     * An auto-advance collection behavior.
     */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /**
     * A no-auto-advance collection behavior.
     */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /**
     * A individuals collection behavior.
     */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /**
     * A continuous collection behavior.
     */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /**
     * A repeat collection behavior.
     */
    REPEAT(BehaviorConstants.REPEAT),

    /**
     * A no-repeat collection behavior.
     */
    NO_REPEAT(BehaviorConstants.NO_REPEAT),

    /**
     * A paged collection behavior.
     */
    PAGED(BehaviorConstants.PAGED),

    /**
     * An unordered collection behavior.
     */
    UNORDERED(BehaviorConstants.UNORDERED),

    /**
     * A multi-part collection behavior.
     */
    MULTI_PART(BehaviorConstants.MULTI_PART),

    /**
     * A together collection behavior.
     */
    TOGETHER(BehaviorConstants.TOGETHER);

    /**
     * The CollectionBehavior logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionBehavior.class, MessageCodes.BUNDLE);

    /**
     * The value of CollectionBehavior.
     */
    private final String myValue;

    /**
     * Creates a new <code>CollectionBehavior</code> from the supplied string value.
     *
     * @param aBehavior A string representation of the desired collection behavior
     */
    CollectionBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    /**
     * Gets a string representation of a collection behavior.
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
     * @return A collection behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a collection behavior
     */
    public static CollectionBehavior fromString(final String aBehavior) {
        for (final CollectionBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(
                LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.COLLECTION));
    }
}
