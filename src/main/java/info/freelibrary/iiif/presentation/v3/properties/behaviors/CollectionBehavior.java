
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

/**
 * The behaviors attributable to {@link Collection}s.
 */
public enum CollectionBehavior implements Behavior {

    /** An auto-advance collection behavior. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A no-auto-advance collection behavior. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** A individuals collection behavior. */
    INDIVIDUALS(BehaviorConstants.INDIVIDUALS),

    /** A continuous collection behavior. */
    CONTINUOUS(BehaviorConstants.CONTINUOUS),

    /** A repeat collection behavior. */
    REPEAT(BehaviorConstants.REPEAT),

    /** A no-repeat collection behavior. */
    NO_REPEAT(BehaviorConstants.NO_REPEAT),

    /** A paged collection behavior. */
    PAGED(BehaviorConstants.PAGED),

    /** An unordered collection behavior. */
    UNORDERED(BehaviorConstants.UNORDERED),

    /** A multi-part collection behavior. */
    MULTI_PART(BehaviorConstants.MULTI_PART),

    /** A together collection behavior. */
    TOGETHER(BehaviorConstants.TOGETHER);

    /** The label on the collection behavior. */
    private final String myLabel;

    /**
     * Creates a new <code>CollectionBehavior</code> from the supplied label value.
     *
     * @param aBehavior A string representation of the desired collection behavior
     */
    CollectionBehavior(final String aBehavior) {
        myLabel = aBehavior;
    }

    /**
     * Gets a string representation of a collection behavior.
     */
    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the label value of the collection behavior.
     *
     * @return A enumeration label
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Returns an enumeration constant from a behavior label.
     *
     * @param aBehavior A behavior value
     * @return A collection behavior
     */
    public static Optional<CollectionBehavior> fromLabel(final String aBehavior) {
        for (final CollectionBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return Optional.of(behavior);
            }
        }

        return Optional.empty();
    }
}
