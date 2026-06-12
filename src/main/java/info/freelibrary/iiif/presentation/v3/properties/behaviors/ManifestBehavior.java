
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

import java.util.Optional;

/**
 * The behaviors attributable to {@link Manifest}s.
 */
public enum ManifestBehavior implements Behavior {

    /** An auto-advance manifest behavior. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A continuous manifest behavior. */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /** An 'individuals' manifest behavior. */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /** A no-auto-advance manifest behavior. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** A no-repeat manifest behavior. */
    NO_REPEAT(BehaviorConstants.NO_REPEAT),

    /** A paged manifest behavior. */
    PAGED(BehaviorConstants.PAGED),

    /** A repeat manifest behavior. */
    REPEAT(BehaviorConstants.REPEAT),

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
     * Gets the manifest behavior's label.
     *
     * @return The manifest behavior's label
     */
    @Override
    public String label() {
        return myLabel;
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
     * Returns an enumeration constant from a behavior label.
     *
     * @param aLabel A behavior
     * @return A manifest behavior
     */
    public static Optional<ManifestBehavior> fromLabel(final String aLabel) {
        return BehaviorLookup.fromLabel(ManifestBehavior.class, aLabel);
    }
}
