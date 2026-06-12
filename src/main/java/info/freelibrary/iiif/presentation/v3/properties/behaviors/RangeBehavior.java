
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;
import info.freelibrary.iiif.presentation.v3.Range;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

import java.util.Optional;

/**
 * The behaviors attributable to {@link Range}s.
 */
public enum RangeBehavior implements Behavior {

    /** An auto-advance range behavior. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A continuous range behavior. */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /** An individual range behavior. */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /** A no-auto-advance range behavior. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** A no-nav range behavior. */
    NO_NAV(BehaviorConstants.NO_NAV),

    /** A paged range behavior. */
    PAGED(BehaviorConstants.PAGED),

    /** A sequence range behavior. */
    SEQUENCE(BehaviorConstants.SEQUENCE),

    /** A thumbnail-nav range behavior. */
    THUMBNAIL_NAV(BehaviorConstants.THUMBNAIL_NAV),

    /** An unordered range behavior. */
    UNORDERED(BehaviorConstants.UNORDERED);

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
     * Gets the label of the range behavior.
     *
     * @return The range behavior's label
     */
    @Override
    public String label() {
        return myLabel;
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
     * Returns an enumeration constant from a behavior label.
     *
     * @param aLabel A behavior label
     * @return A 'range' behavior
     */
    public static Optional<RangeBehavior> fromLabel(final String aLabel) {
        return BehaviorLookup.fromLabel(RangeBehavior.class, aLabel);
    }
}
