
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonValue;
import info.freelibrary.util.Labeled;

import java.util.Optional;

/**
 * A mode associated with an Annotation that is to be applied to the rendering of any time-based media, or otherwise
 * could be considered to have a duration, used as a body resource of that Annotation.
 */
public enum TimeMode implements Labeled {

    /**
     * A loop time-mode.
     */
    LOOP("loop"), //

    /**
     * A scale time-mode.
     */
    SCALE("scale"), //

    /**
     * A trim time-mode.
     */
    TRIM("trim");

    /**
     * The <code>TimeMode</code> label.
     */
    private final String myLabel;

    /**
     * Creates a new <code>TimeMode</code> from the supplied label.
     *
     * @param aLabel A label
     */
    TimeMode(final String aLabel) {
        myLabel = aLabel;
    }

    /**
     * Gets the label of the <code>TimeMode</code>.
     *
     * @return The label of the <code>TimeMode</code>
     */
    @Override
    public String label() {
        return myLabel;
    }

    /**
     * Gets a string representation of the <code>TimeMode</code>.
     *
     * @return A string representation of the <code>TimeMode</code>
     */
    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets a <code>TimeMode</code> by its label.
     *
     * @param aLabel A label
     * @return An empty optional or one containing the <code>TimeMode</code> corresponding to the supplied label
     */
    public static Optional<TimeMode> fromLabel(final String aLabel) {
        for (final TimeMode timeMode : values()) {
            if (timeMode.myLabel.equalsIgnoreCase(aLabel)) {
                return Optional.of(timeMode);
            }
        }

        return Optional.empty();
    }

}
