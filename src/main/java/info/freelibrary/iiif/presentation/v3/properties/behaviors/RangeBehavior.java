
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.Range;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

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

    /** The range behavior's label. */
    private final String myLabel;

    /**
     * Creates a new range behavior from the supplied string.
     *
     * @param aBehavior A range behavior
     */
    RangeBehavior(final String aBehavior) {
        myLabel = aBehavior;
    }

    /**
     * Gets a string representation of the range behavior.
     *
     * @return A string representation of the range behavior
     */
    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the label of the range behavior.
     *
     * @return The range behavior's label
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Returns an enumeration constant from a behavior label.
     *
     * @param aBehavior A behavior label
     * @return A range behavior
     */
    public static Optional<RangeBehavior> fromLabel(final String aBehavior) {
        for (final RangeBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return Optional.of(behavior);
            }
        }

        return Optional.empty();
    }
}
