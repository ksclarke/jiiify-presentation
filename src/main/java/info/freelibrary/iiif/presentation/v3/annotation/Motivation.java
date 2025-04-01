
package info.freelibrary.iiif.presentation.v3.annotation;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Labeled;

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

    @Override
    public boolean equals(final Object aObject) {
        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        return Objects.equals(myLabel, ((Motivation) aObject).myLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(myLabel);
    }

    /**
     * Gets whether the supplied {@code Purpose} is the same as this {@code Motivation}.
     *
     * @param aPurpose A purpose to compare to this motivation
     * @return True if the purpose's and the motivation's values are the same
     */
    public boolean isSameAs(final Purpose aPurpose) {
        return myLabel.equals(aPurpose.toString());
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
