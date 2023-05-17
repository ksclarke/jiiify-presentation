
package info.freelibrary.iiif.presentation.v3.services.image;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.utils.Labeled;

/**
 * An Image API quality.
 */
public enum Quality implements Labeled {

    /** Color image quality. */
    COLOR("color"),

    /** Grey image quality. */
    GRAY("gray"),

    /** Bitonal image quality. */
    BITONAL("bitonal"),

    /** Default image quality. */
    DEFAULT("default");

    /** The image quality label. */
    private final String myLabel;

    /**
     * Creates a new image quality.
     *
     * @param aLabel An image quality label
     */
    Quality(final String aLabel) {
        myLabel = aLabel;
    }

    /**
     * Gets the lower case string representation of this quality.
     *
     * @return The lower case string representation of this quality
     */
    @Override
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the quality's label.
     *
     * @return A label
     */
    @Override
    @JsonValue
    public String label() {
        return myLabel;
    }

    /**
     * Creates an Image API extra quality from a supplied label. Returns an empty optional if the supplied label isn't
     * valid.
     *
     * @param aLabel A quality label
     * @return An optional image service quality or an empty optional
     */
    public static Optional<Quality> fromLabel(final String aLabel) {
        for (final Quality quality : Quality.values()) {
            if (quality.label().equalsIgnoreCase(aLabel)) {
                return Optional.of(quality);
            }
        }

        return Optional.empty();
    }
}
