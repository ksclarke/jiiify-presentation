
package info.freelibrary.iiif.presentation.v3.properties;

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
     * The TimeMode value in string form.
     */
    private final String myValue;

    /**
     * Creates a new TimeMode from the supplied string.
     *
     * @param aTimeMode A time mode in string form
     */
    TimeMode(final String aTimeMode) {
        myValue = aTimeMode;
    }

    /**
     * Gets a string representation of the time-mode.
     *
     * @return A string representation of the time-mode
     */
    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }
}
