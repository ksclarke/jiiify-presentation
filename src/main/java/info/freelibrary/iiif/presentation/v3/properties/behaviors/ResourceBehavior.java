
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

import java.util.Optional;

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
     * Gets the label of the resource behavior.
     *
     * @return The resource behavior's label
     */
    @Override
    public String label() {
        return myLabel;
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
     * Returns an enumeration constant from a behavior label.
     *
     * @param aLabel A behavior label
     * @return A resource behavior
     */
    public static Optional<ResourceBehavior> fromLabel(final String aLabel) {
        return BehaviorLookup.fromLabel(ResourceBehavior.class, aLabel);
    }
}
