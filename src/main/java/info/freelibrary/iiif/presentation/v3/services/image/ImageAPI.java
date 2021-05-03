
package info.freelibrary.iiif.presentation.v3.services.image;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Constants related to the IIIF Image API.
 */
public interface ImageAPI {

    String EXTRA_FORMATS = "extraFormats";

    String EXTRA_QUALITIES = "extraQualities";

    String PROTOCOL = "protocol";

    String TILES = "tiles";

    String SIZES = "sizes";

    String SCALE_FACTORS = "scaleFactors";

    String DEFAULT_PROTOCOL = "http://iiif.io/api/image";

    /**
     * An Image API format.
     */
    enum ImageFormat {

        JPG("jpg"), PNG("png"), TIF("tif"), GIF("gif"), JP2("jp2"), PDF("pdf"), WEBP("webp");

        private static final Logger LOGGER = LoggerFactory.getLogger(ImageFormat.class, MessageCodes.BUNDLE);

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
        public static ImageFormat fromString(final String aFormat) throws IllegalArgumentException {
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
    enum ImageQuality {

        COLOR("color"), GRAY("gray"), BITONAL("bitonal"), DEFAULT("default");

        private static final Logger LOGGER = LoggerFactory.getLogger(ImageQuality.class, MessageCodes.BUNDLE);

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
        public static ImageQuality fromString(final String aQuality) throws IllegalArgumentException {
            for (final ImageQuality quality : ImageQuality.values()) {
                if (quality.string().equalsIgnoreCase(aQuality)) {
                    return quality;
                }
            }

            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_115, aQuality));
        }
    }
}
