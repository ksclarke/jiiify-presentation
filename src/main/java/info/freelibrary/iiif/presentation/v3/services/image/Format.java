
package info.freelibrary.iiif.presentation.v3.services.image;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.utils.Labeled;

/**
 * An Image API format.
 */
public enum Format implements Labeled {

    /** JPEG image format. */
    JPG("jpg"),

    /** PNG image format. */
    PNG("png"),

    /** TIFF image format. */
    TIF("tif"),

    /** GIF image format. */
    GIF("gif"),

    /** JPEG-2000 image format. */
    JP2("jp2"),

    /** PDF format. */
    PDF("pdf"),

    /** WEBP image format. */
    WEBP("webp");

    /** The string representation of the image format. */
    private final String myLabel;

    /**
     * Creates a new image format from the supplied label.
     *
     * @param aFormat A format label
     */
    Format(final String aFormat) {
        myLabel = aFormat;
    }

    /**
     * Gets the lower case string representation of this format.
     *
     * @return The lower case string representation of this format
     */
    @Override
    public String toString() {
        return myLabel;
    }

    /**
     * Gets the format's label.
     *
     * @return A label
     */
    @Override
    @JsonValue
    public String label() {
        return myLabel;
    }

    /**
     * Creates an Image API extra format from a supplied label. Returns an empty optional if the supplied label isn't
     * valid.
     *
     * @param aLabel A format label
     * @return An optional image service format or an empty optional
     */
    public static Optional<Format> fromLabel(final String aLabel) {
        for (final Format format : Format.values()) {
            if (format.label().equalsIgnoreCase(aLabel)) {
                return Optional.of(format);
            }
        }

        return Optional.empty();
    }
}
