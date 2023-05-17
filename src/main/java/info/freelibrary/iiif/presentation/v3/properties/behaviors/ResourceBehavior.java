
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

/**
 * The behaviors attributable to {@link Resource}s.
 */
public enum ResourceBehavior implements Behavior {

    /** A hidden resource behavior. */
    HIDDEN(BehaviorConstants.HIDDEN);

    /** The label of the resource behavior. */
    private final String myLabel;

    /**
     * Creates a resource behavior from the supplied string.
     *
     * @param aBehavior A resource behavior
     */
    ResourceBehavior(final String aBehavior) {
        myLabel = aBehavior;
    }

    /**
     * Gets a string representation of the resource behavior.
     *
     * @return A string representation of the resource behavior
     */
    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the label of the resource behavior.
     *
     * @return The resource behavior's label
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Returns an enumeration constant from a behavior label.
     *
     * @param aBehavior A behavior label
     * @return A resource behavior
     */
    public static Optional<ResourceBehavior> fromLabel(final String aBehavior) {
        for (final ResourceBehavior behavior : values()) {
            if (behavior.toString().equalsIgnoreCase(aBehavior)) {
                return Optional.of(behavior);
            }
        }

        return Optional.empty();
    }
}
