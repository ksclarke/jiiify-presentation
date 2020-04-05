
package info.freelibrary.iiif.presentation.properties.selectors;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A selector for IIIF Image APIs.
 */
public class ImageApiSelector extends AbstractSelector {

    protected static final String SIZE = "size";

    protected static final String REGION = "region";

    protected static final String ROTATION = "rotation";

    protected static final String QUALITY = "quality";

    protected static final String FORMAT = "format";

    protected static final String DEFAULT_SIZE = "max";

    protected static final String DEFAULT_REGION = "full";

    protected static final String DEFAULT_ROTATION = "0";

    protected static final String DEFAULT_QUALITY = "default";

    protected static final String DEFAULT_FORMAT = "jpg";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageApiSelector.class, Constants.BUNDLE_NAME);

    private static final String SLASH = "/";

    private String myRegion = DEFAULT_REGION;

    private String mySize = DEFAULT_SIZE;

    private String myRotation = DEFAULT_ROTATION;

    private String myQuality = DEFAULT_QUALITY;

    private String myFormat = DEFAULT_FORMAT;

    /**
     * Creates an Image API selector.
     */
    public ImageApiSelector() {
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
        final String[] parts = aUrlPath.indexOf('/') == 0 ? aUrlPath.substring(1).split(SLASH) : aUrlPath.split(
                SLASH);
        final int dotIndex;

        if (parts.length != 4) {
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
