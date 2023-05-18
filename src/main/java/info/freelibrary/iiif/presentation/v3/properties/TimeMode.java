
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A mode associated with an Annotation that is to be applied to the rendering of any time-based media, or otherwise
 * could be considered to have a duration, used as a body resource of that Annotation.
 */
public enum TimeMode {

    /**
     * A trim time-mode.
     */
    TRIM("trim"), //

    /**
     * A scale time-mode.
     */
    SCALE("scale"), //

    /**
     * A loop time-mode.
     */
    LOOP("loop");

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
    public static Optional<TimeMode> forLabel(final String aLabel) {
        for (final TimeMode timeMode : values()) {
            if (timeMode.myLabel.equalsIgnoreCase(aLabel)) {
                return Optional.of(timeMode);
            }
        }

        return Optional.empty();
    }
}
