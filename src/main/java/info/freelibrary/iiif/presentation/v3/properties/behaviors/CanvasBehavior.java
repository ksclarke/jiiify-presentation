
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;

import java.util.Optional;

/**
 * The behaviors attributable to {@link Canvas}es.
 */
public enum CanvasBehavior implements Behavior {

    /** An auto-advance behavior on a canvas. */
    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE),

    /** A facing-pages behavior on a canvas. */
    FACING_PAGES(BehaviorConstants.FACING_PAGES),

    /** A no-auto-advance behavior on a canvas. */
    NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE),

    /** A non-paged behavior on a canvas. */
    NON_PAGED(BehaviorConstants.NON_PAGED);

    /** The canvas behavior label. */
    private final String myLabel;

    /**
     * Creates a new canvas behavior from the supplied string.
     *
     * @param aBehavior A canvas behavior
     */
    CanvasBehavior(final String aBehavior) {
        myLabel = aBehavior;
    }

    /**
     * Gets the enumeration value's label.
     *
     * @return The enumeration value's label
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Gets a string representation of a canvas behavior.
     *
     * @return A string representation of a canvas behavior
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
     * @return A canvas behavior
     */
    public static Optional<CanvasBehavior> fromLabel(final String aLabel) {
        return BehaviorLookup.fromLabel(CanvasBehavior.class, aLabel);
    }
}
