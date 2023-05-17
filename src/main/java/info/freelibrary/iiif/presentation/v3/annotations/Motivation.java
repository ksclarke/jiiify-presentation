
package info.freelibrary.iiif.presentation.v3.annotations;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.utils.Labeled;

/**
 * An annotation's motivation is either one of {@link Purpose}'s values or a user supplied string value.
 */
public class Motivation implements Labeled {

    /** An annotation's motivation. */
    private final String myLabel;

    /**
     * Creates a new annotation motivation from the supplied purpose.
     *
     * @param aPurpose A purpose of an annotation
     */
    public Motivation(final Purpose aPurpose) {
        myLabel = aPurpose.label();
    }

    /**
     * Creates a new motivation from the supplied string.
     *
     * @param aValue An annotation value
     */
    public Motivation(final String aValue) {
        // Normalize the supplied string if it is a motivation/purpose value
        myLabel = Purpose.fromLabel(aValue).map(Purpose::label).orElse(aValue);
    }

    /**
     * Returns the motivation label.
     *
     * @return The label of this motivation
     */
    @Override
    public String label() {
        return myLabel;
    }

    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Creates a new motivation from the supplied purpose.
     *
     * @param aPurpose A purpose of the annotation
     * @return A motivation for an annotation
     */
    public static Motivation fromLabel(final Purpose aPurpose) {
        return new Motivation(aPurpose);
    }

    /**
     * Creates a new motivation from the supplied string.
     *
     * @param aLabel A motivation label
     * @return A motivation for an annotation
     */
    public static Motivation fromLabel(final String aLabel) {
        return new Motivation(aLabel);
    }
}
