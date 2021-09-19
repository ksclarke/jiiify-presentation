
package info.freelibrary.iiif.presentation.v2;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.services.Service;
import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An image resource from a {@link Service} that is used as an {@link ImageResource}, and in {@link Logo}s and
 * {@link Thumbnail}s.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.FORMAT, Constants.WIDTH, Constants.HEIGHT,
    Constants.SERVICE })
@JsonInclude(Include.NON_DEFAULT)
public class ServiceImage {

    /**
     * The service image logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImage.class, MessageCodes.BUNDLE);

    /**
     * The service image's type.
     */
    private static final String TYPE = "dctypes:Image";

    /**
     * The service image's format.
     */
    private Optional<MediaType> myFormat;

    /**
     * The service image's width.
     */
    private int myWidth;

    /**
     * The service image's height.
     */
    private int myHeight;

    /**
     * The service image's ID.
     */
    private URI myID;

    /**
     * The service image's image info service.
     */
    private Optional<ImageInfoService> myService;

    /**
     * Creates an image
     *
     * @param aURI An image ID
     */
    public ServiceImage(final String aURI) {
        Objects.requireNonNull(aURI, LOGGER.getMessage(MessageCodes.JPA_003));

        myService = Optional.empty();
        myID = URI.create(aURI);

        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates an image
     *
     * @param aID An image ID
     */
    public ServiceImage(final URI aID) {
        Objects.requireNonNull(aID, LOGGER.getMessage(MessageCodes.JPA_003));

        myService = Optional.empty();
        myID = aID;

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Creates an image.
     *
     * @param aURI A URI image ID
     * @param aService A service for the image
     */
    public ServiceImage(final String aURI, final ImageInfoService aService) {
        Objects.requireNonNull(aURI, LOGGER.getMessage(MessageCodes.JPA_003));

        myID = URI.create(aURI);
        myService = Optional.ofNullable(aService);

        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates an image.
     *
     * @param aID An image ID
     * @param aService A service for the image
     */
    public ServiceImage(final URI aID, final ImageInfoService aService) {
        Objects.requireNonNull(aID, LOGGER.getMessage(MessageCodes.JPA_003));

        myID = aID;
        myService = Optional.ofNullable(aService);

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Creates an image.
     *
     * @param aURI An image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public ServiceImage(final String aURI, final int aWidth, final int aHeight) {
        Objects.requireNonNull(aURI, LOGGER.getMessage(MessageCodes.JPA_003));

        myService = Optional.empty();
        myID = URI.create(aURI);
        myWidth = aWidth;
        myHeight = aHeight;

        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates an image.
     *
     * @param aID An image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public ServiceImage(final URI aID, final int aWidth, final int aHeight) {
        Objects.requireNonNull(aID, LOGGER.getMessage(MessageCodes.JPA_003));

        myID = aID;
        myWidth = aWidth;
        myHeight = aHeight;
        myService = Optional.empty();

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Gets the image type.
     *
     * @return The image type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return TYPE;
    }

    /**
     * Sets the format of the image from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The image
     */
    @JsonSetter(Constants.FORMAT)
    public ServiceImage setFormat(final String aMediaType) {
        setMediaTypeFromExt(aMediaType);
        return this;
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The image
     */
    @JsonIgnore
    public ServiceImage setFormatMediaType(final MediaType aMediaType) {
        myFormat = Optional.ofNullable(aMediaType);
        return this;
    }

    /**
     * Gets the format of the image.
     *
     * @return A string representation of the format
     */
    @JsonGetter(Constants.FORMAT)
    public String getFormat() {
        return myFormat.isPresent() ? myFormat.get().toString() : null;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return myFormat;
    }

    /**
     * Sets the image width.
     *
     * @param aWidth The image width
     * @return The image
     */
    @JsonSetter(Constants.WIDTH)
    public ServiceImage setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the image height.
     *
     * @param aHeight The image height
     * @return The image
     */
    @JsonSetter(Constants.HEIGHT)
    public ServiceImage setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Gets the image width.
     *
     * @return The image width
     */
    @JsonGetter(Constants.WIDTH)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the image height.
     *
     * @return The image height
     */
    @JsonGetter(Constants.HEIGHT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the image ID
     *
     * @return The image ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the image ID.
     *
     * @param aURI The image ID
     * @return The image
     */
    @JsonSetter(Constants.ID)
    public ServiceImage setID(final String aURI) {
        Objects.requireNonNull(aURI, LOGGER.getMessage(MessageCodes.JPA_003));
        myID = URI.create(aURI);
        return this;
    }

    /**
     * Sets the image ID.
     *
     * @param aURI The image ID
     * @return The image
     */
    @JsonIgnore
    public ServiceImage setID(final URI aURI) {
        Objects.requireNonNull(aURI, LOGGER.getMessage(MessageCodes.JPA_003));
        myID = aURI;
        return this;
    }

    /**
     * Gets the image's service.
     *
     * @return The image's service
     */
    @JsonGetter(Constants.SERVICE)
    public Optional<ImageInfoService> getService() {
        return myService.isEmpty() ? Optional.empty() : myService;
    }

    /**
     * Sets the image's service
     *
     * @param aService The image's service
     * @return The image
     */
    @JsonSetter(Constants.SERVICE)
    public ServiceImage setService(final ImageInfoService aService) {
        myService = Optional.ofNullable(aService);
        return this;
    }

    /**
     * Sets the media type from the file extension.
     *
     * @param aURI A file extension
     */
    @JsonIgnore
    protected final void setMediaTypeFromExt(final String aURI) {
        final String fragment = '#' + URI.create(aURI).getFragment();
        final String mimeType;
        final String uri;
        final int index;

        // If we have a fragment on our URI, remove it before checking media type
        if ((index = aURI.indexOf(fragment)) != -1) {
            uri = aURI.substring(0, index);
        } else {
            uri = aURI;
        }

        mimeType = FileUtils.getMimeType(uri);

        try {
            if (mimeType != null) {
                myFormat = Optional.ofNullable(MediaType.parse(mimeType));
            } else {
                myFormat = Optional.ofNullable(MediaType.parse(aURI));
            }
        } catch (final IllegalArgumentException details) {
            LOGGER.warn(MessageCodes.JPA_013, aURI);
            myFormat = Optional.empty();
        }
    }
}
