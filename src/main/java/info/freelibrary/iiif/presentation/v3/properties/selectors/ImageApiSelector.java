
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A selector for IIIF Image APIs.
 */
public class ImageApiSelector implements Selector {

    /**
     * The Image API selector's size.
     */
    protected static final String SIZE = "size";

    /**
     * The Image API selector's region.
     */
    protected static final String REGION = "region";

    /**
     * The Image API selector's rotation.
     */
    protected static final String ROTATION = "rotation";

    /**
     * The Image API selector's quality.
     */
    protected static final String QUALITY = "quality";

    /**
     * The Image API selector's format.
     */
    protected static final String FORMAT = "format";

    /**
     * The Image API selector's max size.
     */
    protected static final String DEFAULT_SIZE = "max";

    /**
     * The Image API selector's full region.
     */
    protected static final String DEFAULT_REGION = "full";

    /**
     * The Image API selector's default rotation.
     */
    protected static final String DEFAULT_ROTATION = "0";

    /**
     * The Image API selector's default quality.
     */
    protected static final String DEFAULT_QUALITY = "default";

    /**
     * The Image API selector's default format.
     */
    protected static final String DEFAULT_FORMAT = "jpg";

    /**
     * The Image API selector's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageApiSelector.class, MessageCodes.BUNDLE);

    /**
     * The expected number of URL delimiters.
     */
    private static final int EXPECTED_URL_DELIM_COUNT = 4;

    /**
     * The Image API selector's slash constant.
     */
    private static final String SLASH = "/";

    /**
     * The Image API selector's region;
     */
    private String myRegion = DEFAULT_REGION;

    /**
     * The Image API selector's size.
     */
    private String mySize = DEFAULT_SIZE;

    /**
     * The Image API selector's rotation.
     */
    private String myRotation = DEFAULT_ROTATION;

    /**
     * The Image API selector's quality.
     */
    private String myQuality = DEFAULT_QUALITY;

    /**
     * The Image API selector's format.
     */
    private String myFormat = DEFAULT_FORMAT;

    /**
     * Creates an Image API selector.
     */
    public ImageApiSelector() {
        // This is intentionally empty
    }

    /**
     * Creates an Image API selector.
     *
     * @param aRegion A region from an image
     * @param aSize A size from an image
     * @param aRotation A rotation from an image
     * @param aQuality A quality of an image
     * @param aFormat A format of an image
     */
    public ImageApiSelector(final String aRegion, final String aSize, final String aRotation, final String aQuality,
            final String aFormat) {
        myRegion = aRegion;
        mySize = aSize;
        myRotation = aRotation;
        myQuality = aQuality;
        myFormat = aFormat;
    }

    /**
     * Creates an Image API selector from the supplied API path.
     *
     * @param aUrlPath An Image API URL path
     * @throws IllegalArgumentException If the supplied URL path isn't a valid IIIF Image API URL
     */
    public ImageApiSelector(final String aUrlPath) {
        final String[] parts = aUrlPath.indexOf('/') == 0 ? aUrlPath.substring(1).split(SLASH) : aUrlPath.split(SLASH);
        final int dotIndex;

        if (parts.length != EXPECTED_URL_DELIM_COUNT) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_036, aUrlPath));
        }

        if ((dotIndex = parts[3].indexOf('.')) == -1) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_035, aUrlPath));
        }

        myRegion = parts[0];
        mySize = parts[1];
        myRotation = parts[2];
        myQuality = parts[3].substring(0, dotIndex);
        myFormat = parts[3].substring(dotIndex + 1);
    }

    /**
     * Gets the requested region.
     *
     * @return The requested region
     */
    public String getRegion() {
        return myRegion;
    }

    /**
     * Sets the requested region.
     *
     * @param aRegion A region to set
     * @return This image API selector
     */
    public ImageApiSelector setRegion(final String aRegion) {
        myRegion = aRegion;
        return this;
    }

    /**
     * Gets the requested size.
     *
     * @return The requested size
     */
    public String getSize() {
        return mySize;
    }

    /**
     * Sets the requested size.
     *
     * @param aSize A size to set
     * @return This image API selector
     */
    public ImageApiSelector setSize(final String aSize) {
        mySize = aSize;
        return this;
    }

    /**
     * Gets the requested rotation.
     *
     * @return The requested rotation
     */
    public String getRotation() {
        return myRotation;
    }

    /**
     * Sets the requested rotation.
     *
     * @param aRotation A rotation to set
     * @return This image API selector
     */
    public ImageApiSelector setRotation(final String aRotation) {
        myRotation = aRotation;
        return this;
    }

    /**
     * Gets the requested image quality.
     *
     * @return The requested image quality
     */
    public String getQuality() {
        return myQuality;
    }

    /**
     * Sets the image quality.
     *
     * @param aQuality An image quality to set
     * @return This image API selector
     */
    public ImageApiSelector setQuality(final String aQuality) {
        myQuality = aQuality;
        return this;
    }

    /**
     * Gets the image format.
     *
     * @return The image format
     */
    public String getFormat() {
        return myFormat;
    }

    /**
     * Sets the image format.
     *
     * @param aFormat The image format to set
     * @return This image API selector
     */
    public ImageApiSelector setFormat(final String aFormat) {
        myFormat = aFormat;
        return this;
    }

    @Override
    public String toString() {
        return StringUtils.format("/{}/{}/{}/{}.{}", myRegion, mySize, myRotation, myQuality, myFormat);
    }
}
