
package info.freelibrary.iiif.presentation.v3.services.image;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Constants related to the <a href="https://iiif.io/api/image/">IIIF Image API</a>.
 */
public final class ImageAPI {

    /**
     * The image API's extra formats.
     */
    public static final String EXTRA_FORMATS = "extraFormats";

    /**
     * The image API's extra qualities.
     */
    public static final String EXTRA_QUALITIES = "extraQualities";

    /**
     * The image API's protocol.
     */
    public static final String PROTOCOL = "protocol";

    /**
     * The image API's tiles.
     */
    public static final String TILES = "tiles";

    /**
     * The image API's sizes.
     */
    public static final String SIZES = "sizes";

    /**
     * The image API's scale factors.
     */
    public static final String SCALE_FACTORS = "scaleFactors";

    /**
     * The default protocol for the image API.
     */
    public static final String DEFAULT_PROTOCOL = "http://iiif.io/api/image";

    /**
     * Creates a new ImageAPI constants class.
     */
    private ImageAPI() {
        // This is intentionally left empty.
    }

    /**
     * An Image API format.
     */
    public enum ImageFormat {

        JPG("jpg"), PNG("png"), TIF("tif"), GIF("gif"), JP2("jp2"), PDF("pdf"), WEBP("webp");

        /**
         * The image format's logger.
         */
        private static final Logger LOGGER = LoggerFactory.getLogger(ImageFormat.class, MessageCodes.BUNDLE);

        /**
         * The string representation of the image format.
         */
        private final String myFormat;

        /**
         * Creates a new image format.
         *
         * @param aFormat The string representation of a format
         */
        ImageFormat(final String aFormat) {
            myFormat = aFormat;
        }

        /**
         * Gets the lower case string representation of this format.
         *
         * @return The lower case string representation of this format
         */
        public String string() {
            return myFormat;
        }

        /**
         * Creates an Image API extra format from a supplied string value.
         *
         * @param aFormat A format in string form
         * @return An image service format
         * @throws IllegalArgumentException If the format string doesn't correspond to a valid format
         */
        public static ImageFormat fromString(final String aFormat) {
            for (final ImageFormat format : ImageFormat.values()) {
                if (format.string().equalsIgnoreCase(aFormat)) {
                    return format;
                }
            }

            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_114, aFormat));
        }
    }

    /**
     * An Image API quality.
     */
    public enum ImageQuality {

        COLOR("color"), GRAY("gray"), BITONAL("bitonal"), DEFAULT("default");

        /**
         * The image quality's logger.
         */
        private static final Logger LOGGER = LoggerFactory.getLogger(ImageQuality.class, MessageCodes.BUNDLE);

        /**
         * The string form of the image quality.
         */
        private final String myQuality;

        /**
         * Creates a new image quality.
         *
         * @param aQuality The string representation of a quality
         */
        ImageQuality(final String aQuality) {
            myQuality = aQuality;
        }

        /**
         * Gets the lower case string representation of this quality.
         *
         * @return The lower case string representation of this quality
         */
        public String string() {
            return myQuality;
        }

        /**
         * Creates an Image API extra quality from a supplied string value.
         *
         * @param aQuality A quality in string form
         * @return An image service quality
         * @throws IllegalArgumentException If the quality string doesn't correspond to a valid quality
         */
        public static ImageQuality fromString(final String aQuality) {
            for (final ImageQuality quality : ImageQuality.values()) {
                if (quality.string().equalsIgnoreCase(aQuality)) {
                    return quality;
                }
            }

            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_115, aQuality));
        }
    }
}
