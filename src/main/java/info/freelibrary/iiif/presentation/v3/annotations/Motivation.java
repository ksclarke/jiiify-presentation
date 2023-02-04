
package info.freelibrary.iiif.presentation.v3.annotations;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An annotation's motivation is either one of {@link Purpose}'s values or a user supplied string value.
 */
public class Motivation {

    /** An annotation's motivation. */
    private final String myValue;

    /**
     * Creates a new annotation motivation from the supplied purpose.
     *
     * @param aPurpose A purpose of an annotation
     */
    public Motivation(final Purpose aPurpose) {
        myValue = aPurpose.label();
    }

    /**
     * Creates a new motivation from the supplied string.
     *
     * @param aValue An annotation value
     */
    public Motivation(final String aValue) {
        // Normalize the supplied string if it is a motivation/purpose value
        myValue = Purpose.forLabel(aValue).map(Purpose::label).orElse(aValue);
    }

    /**
     * Creates a new motivation from the supplied purpose.
     *
     * @param aPurpose A purpose of the annotation
     * @return A motivation for an annotation
     */
    public static Motivation from(final Purpose aPurpose) {
        return new Motivation(aPurpose);
    }

    /**
     * Creates a new motivation from the supplied string.
     *
     * @param aValue A motivation value
     * @return A motivation for an annotation
     */
    public static Motivation from(final String aValue) {
        return new Motivation(aValue);
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }
}
