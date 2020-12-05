
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors available to resources.
 */
public enum ResourceBehavior implements Behavior {

    HIDDEN(BehaviorConstants.HIDDEN);

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBehavior.class, MessageCodes.BUNDLE);

    private final String myValue;

    ResourceBehavior(final String aBehavior) {
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
     * @return A resource behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a resource behavior
     */
    public static ResourceBehavior fromString(final String aBehavior) throws IllegalArgumentException {
        for (final ResourceBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        // If BehaviorsDeserializer.deserialize is the caller, then the resource type will be filled in there
        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, Constants.MESSAGE_SLOT));
    }
}
