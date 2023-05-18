
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

/**
 * The behaviors attributable to {@link Manifest}s.
 */
public enum ManifestBehavior implements Behavior {

    /** An auto-advance manifest behavior. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A no-auto-advance manifest behavior. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** An individuals manifest behavior. */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /** A continuous manifest behavior. */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /** A repeat manifest behavior. */
    REPEAT(BehaviorConstants.REPEAT),

    /** A no-repeat manifest behavior. */
    NO_REPEAT(BehaviorConstants.NO_REPEAT),

    /** A paged manifest behavior. */
    PAGED(BehaviorConstants.PAGED),

    /** An unordered manifest behavior. */
    UNORDERED(BehaviorConstants.UNORDERED);

    /** The manifest behavior's label. */
    private final String myLabel;

    /**
     * Creates a new manifest behavior from the supplied label.
     *
     * @param aBehavior A manifest behavior
     */
    ManifestBehavior(final String aBehavior) {
        myLabel = aBehavior;
    }

    /**
     * Gets a string representation of the manifest behavior.
     *
     * @return A string representation of the manifest behavior
     */
    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the manifest behavior's label.
     *
     * @return The manifest behavior's label
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Returns an enumeration constant from a behavior label.
     *
     * @param aBehavior A behavior
     * @return A manifest behavior
     */
    public static Optional<ManifestBehavior> fromLabel(final String aBehavior) {
        for (final ManifestBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return Optional.of(behavior);
            }
        }

        return Optional.empty();
    }
}
