
package info.freelibrary.iiif.presentation;

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

import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.services.Service;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An image resource from a {@link Service} that is used as an {@link ImageContent}, and in {@link Logo}s and
 * {@link Thumbnail}s.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.FORMAT, Constants.WIDTH, Constants.HEIGHT,
    Constants.SERVICE })
public class ServiceImage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImage.class, Constants.BUNDLE_NAME);

    private Optional<MediaType> myFormat;

    private int myWidth;

    private int myHeight;

    private URI myID;

    private Optional<ImageInfoService> myService;

    /**
     * Creates a service image from the supplied string ID.
     *
     * @param aID An image URI ID in string form
     */
    public ServiceImage(final String aID) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);

        myService = Optional.empty();
        myID = URI.create(aID);

        setMediaTypeFromExt(aID);
    }

    /**
     * Creates a service image from the supplied URI ID.
     *
     * @param aID An image ID
     */
    public ServiceImage(final URI aID) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);

        myService = Optional.empty();
        myID = aID;

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Creates a service image from the supplied URI ID, in string form, and image information service.
     *
     * @param aURI A URI image ID in string form
     * @param aService A service for the image
     */
    public ServiceImage(final String aURI, final ImageInfoService aService) {
        Objects.requireNonNull(aURI, MessageCodes.JPA_003);

        myID = URI.create(aURI);
        myService = Optional.ofNullable(aService);

        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates a service image from the supplied URI ID and image information service.
     *
     * @param aID An image ID
     * @param aService A service for the image
     */
    public ServiceImage(final URI aID, final ImageInfoService aService) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);

        myID = aID;
        myService = Optional.ofNullable(aService);

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Creates a service image.
     *
     * @param aURI An image ID in string form
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public ServiceImage(final String aURI, final int aWidth, final int aHeight) {
        Objects.requireNonNull(aURI, MessageCodes.JPA_003);

        myService = Optional.empty();
        myID = URI.create(aURI);
        myWidth = aWidth;
        myHeight = aHeight;

        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates a service image.
     *
     * @param aID An image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public ServiceImage(final URI aID, final int aWidth, final int aHeight) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);

        myID = aID;
        myWidth = aWidth;
        myHeight = aHeight;
        myService = Optional.empty();

        setMediaTypeFromExt(aID.toString());
    }

    /**
     * Constructs a service image for Jackson's deserialization process.
     */
    protected ServiceImage() {
    }

    /**
     * Gets the service image type.
     *
     * @return The service image type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return ResourceTypes.IMAGE;
    }

    /**
     * Sets the format of the service image from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The service image
     */
    @JsonSetter(Constants.FORMAT)
    public ServiceImage setFormat(final String aMediaType) {
        setMediaTypeFromExt(aMediaType);
        return this;
    }

    /**
     * Sets the format of the service image.
     *
     * @param aMediaType A media type
     * @return The service image
     */
    @JsonIgnore
    public ServiceImage setFormatMediaType(final MediaType aMediaType) {
        myFormat = Optional.ofNullable(aMediaType);
        return this;
    }

    /**
     * Gets the format of the service image.
     *
     * @return A string representation of the service image's format
     */
    @JsonGetter(Constants.FORMAT)
    public String getFormat() {
        return myFormat != null && myFormat.isPresent() ? myFormat.get().toString() : null;
    }

    /**
     * Gets the media type format of the service image.
     *
     * @return The media type format of the service image
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return myFormat == null ? Optional.empty() : myFormat;
    }

    /**
     * Sets the width and height of the service image.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     * @return This service image
     */
    @JsonIgnore
    public ServiceImage setWidthHeight(final int aWidth, final int aHeight) {
        setWidth(aWidth);
        setHeight(aHeight);

        return this;
    }

    /**
     * Gets the service image's width.
     *
     * @return The service image's width
     */
    @JsonGetter(Constants.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the service image's height.
     *
     * @return The service image's height
     */
    @JsonGetter(Constants.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the service image ID
     *
     * @return The service image's ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the service image ID from the supplied ID in string form.
     *
     * @param aID The service image's URI ID in string form
     * @return The service image
     */
    @JsonSetter(Constants.ID)
    public ServiceImage setID(final String aID) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the service image ID.
     *
     * @param aURI The service image ID
     * @return The service image
     */
    @JsonIgnore
    public ServiceImage setID(final URI aURI) {
        Objects.requireNonNull(aURI, MessageCodes.JPA_003);
        myID = aURI;
        return this;
    }

    /**
     * Gets the service image's associated service.
     *
     * @return The service image's associated service
     */
    @JsonGetter(Constants.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public Optional<ImageInfoService> getService() {
        return myService == null || myService.isEmpty() ? Optional.empty() : myService;
    }

    /**
     * Sets the service image's associated service
     *
     * @param aService The service image's associated service
     * @return The service image
     */
    @JsonSetter(Constants.SERVICE)
    public ServiceImage setService(final ImageInfoService aService) {
        myService = Optional.ofNullable(aService);
        return this;
    }

    @Override
    public String toString() {
        return String.join(":", getClass().getSimpleName(), getID().toString());
    }

    /**
     * Tries to set the service image's media type from the ID extension.
     *
     * @param aURI The service image's ID
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

    /**
     * Sets the service image width.
     *
     * @param aWidth The service image's width
     * @return The service image
     */
    @JsonSetter(Constants.WIDTH)
    private ServiceImage setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the service image height.
     *
     * @param aHeight The service image's height
     * @return The service image
     */
    @JsonSetter(Constants.HEIGHT)
    private ServiceImage setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Sets the type of service image. This method is just used by Jackson's deserialization process. It doesn't
     * actually change the hard-coded type value.
     *
     * @param aType The service image type
     * @return This service image
     */
    @JsonSetter(Constants.TYPE)
    private ServiceImage setType(final String aType) {
        // The value is hard-coded; this method is just used by Jackson
        if (!ResourceTypes.IMAGE.equals(aType)) {
            throw new I18nRuntimeException(MessageCodes.JPA_053, ResourceTypes.IMAGE);
        }

        return this;
    }
}
