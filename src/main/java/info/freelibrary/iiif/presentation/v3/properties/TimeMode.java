package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A mode associated with an Annotation that is to be applied to the rendering of any time-based media, or otherwise
 * could be considered to have a duration, used as a body resource of that Annotation.
 */
public enum TimeMode {

    TRIM("trim"), SCALE("scale"), LOOP("loop");

    private final String myValue;

    TimeMode(final String aTimeMode) {
        myValue = aTimeMode;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }
}
