
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors available to manifests.
 */
public enum ManifestBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS), CONTINUOUS(BehaviorConstants.CONTINUOUS),
    REPEAT(BehaviorConstants.REPEAT), NO_REPEAT(BehaviorConstants.NO_REPEAT), PAGED(BehaviorConstants.PAGED),
    UNORDERED(BehaviorConstants.UNORDERED);

    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestBehavior.class, MessageCodes.BUNDLE);

    private final String myValue;

    ManifestBehavior(final String aBehavior) {
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
     * @return A manifest behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a manifest behavior
     */
    public static ManifestBehavior fromString(final String aBehavior) throws IllegalArgumentException {
        for (final ManifestBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.MANIFEST));
    }
}
