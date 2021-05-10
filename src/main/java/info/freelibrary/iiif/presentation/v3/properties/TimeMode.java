
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A mode associated with an Annotation that is to be applied to the rendering of any time-based media, or otherwise
 * could be considered to have a duration, used as a body resource of that Annotation.
 */
public enum TimeMode {

    TRIM("trim"), SCALE("scale"), LOOP("loop");

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

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }
}
