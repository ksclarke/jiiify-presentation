
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The behaviors attributable to {@link Manifest}s.
 */
public enum ManifestBehavior implements Behavior {

    /**
     * An auto-advance manifest behavior.
     */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /**
     * A no-auto-advance manifest behavior.
     */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /**
     * An individuals manifest behavior.
     */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /**
     * A continuous manifest behavior.
     */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /**
     * A repeat manifest behavior.
     */
    REPEAT(BehaviorConstants.REPEAT),

    /**
     * A no-repeat manifest behavior.
     */
    NO_REPEAT(BehaviorConstants.NO_REPEAT),

    /**
     * A paged manifest behavior.
     */
    PAGED(BehaviorConstants.PAGED),

    /**
     * An unordered manifest behavior.
     */
    UNORDERED(BehaviorConstants.UNORDERED);

    /**
     * The manifest behavior logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestBehavior.class, MessageCodes.BUNDLE);

    /**
     * The value of the manifest behavior.
     */
    private final String myValue;

    /**
     * Creates a new manifest behavior from the supplied string.
     *
     * @param aBehavior A manifest behavior
     */
    ManifestBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    /**
     * Gets a string representation of the manifest behavior.
     *
     * @return A string representation of the manifest behavior
     */
    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Maps a behavior string to an enum constant of this type.
     *
     * @param aBehavior A behavior
     * @return A manifest behavior
     * @throws IllegalArgumentException If the behavior string doesn't correspond to a manifest behavior
     */
    public static ManifestBehavior from(final String aBehavior) {
        for (final ManifestBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return behavior;
            }
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aBehavior, ResourceTypes.MANIFEST));
    }
}
