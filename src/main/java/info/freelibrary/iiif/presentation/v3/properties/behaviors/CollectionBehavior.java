
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors available to the collections.
 */
public enum CollectionBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS), CONTINUOUS(BehaviorConstants.CONTINUOUS),
    REPEAT(BehaviorConstants.REPEAT), NO_REPEAT(BehaviorConstants.NO_REPEAT), PAGED(BehaviorConstants.PAGED),
    UNORDERED(BehaviorConstants.UNORDERED), MULTI_PART(BehaviorConstants.MULTI_PART),
    TOGETHER(BehaviorConstants.TOGETHER);

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionBehavior.class, MessageCodes.BUNDLE);

    private final String myValue;

    CollectionBehavior(final String aBehavior) {
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
     * @return A collection behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a collection behavior
     */
    public static CollectionBehavior fromString(final String aBehavior) throws IllegalArgumentException {
        for (final CollectionBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(
                LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.COLLECTION));
    }
}
