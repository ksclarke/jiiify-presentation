
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.ServiceImage;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A property that relies on a service.
 */
class ServiceProperty<T extends ServiceProperty<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProperty.class, Constants.BUNDLE_NAME);

    private List<ServiceImage> myImages;

    protected ServiceProperty() {
        super();
    }

    /**
     * Adds image to the property.
     *
     * @param aIdArray An array of URI IDs, in string form, for the image
     * @return The property
     */
    protected T addImage(final String... aIdArray) {
        for (final String id : aIdArray) {
            if (!getImages().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, id));
            }
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aIdArray An array of URI IDs for the image
     * @return The property
     */
    protected T addImage(final URI... aIdArray) {
        for (final URI id : aIdArray) {
            final ServiceImage serviceImage = new ServiceImage(id);

            if (!getImages().add(serviceImage)) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, serviceImage));
            }
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID, in string form, for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    public T addImage(final String aID, final int aWidth, final int aHeight) {
        final ServiceImage serviceImage = new ServiceImage(aID, aWidth, aHeight);

        if (!getImages().add(serviceImage)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, serviceImage));
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aURI The ID for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    public T addImage(final URI aURI, final int aWidth, final int aHeight) {
        final ServiceImage serviceImage = new ServiceImage(aURI, aWidth, aHeight);

        if (!getImages().add(serviceImage)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, serviceImage));
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID, in string form, for the image
     * @param aService A service for the image
     * @return The property
     */
    public T addImage(final String aID, final ImageInfoService aService) {
        final ServiceImage serviceImage = new ServiceImage(aID, aService);

        if (!getImages().add(serviceImage)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, serviceImage));
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The ID for the image
     * @param aService A service for the image
     * @return The property
     */
    public T addImage(final URI aID, final ImageInfoService aService) {
        final ServiceImage serviceImage = new ServiceImage(aID, aService);

        if (!getImages().add(serviceImage)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_048, serviceImage));
        }

        return (T) this;
    }

    /**
     * Returns a list of images.
     *
     * @return A list of images
     */
    public List<ServiceImage> getImages() {
        if (myImages == null) {
            myImages = new ArrayList<>();
        }

        return myImages;
    }

    /**
     * Gets the number of images in this property.
     *
     * @return The number of images in this property
     */
    public int count() {
        return getImages().size();
    }

    /**
     * Gets the first image ID
     *
     * @return The first image ID
     */
    public URI getID() {
        return getImages().get(0).getID();
    }

    /**
     * Gets the first image width.
     *
     * @return The image width
     */
    public int getWidth() {
        return getImages().get(0).getWidth();
    }

    /**
     * Gets the first image height.
     *
     * @return The image height
     */
    public int getHeight() {
        return getImages().get(0).getHeight();
    }

    /**
     * Gets the format of the first image.
     *
     * @return A string representation of the format
     */
    public String getFormat() {
        return getImages().get(0).getFormat();
    }

    /**
     * Gets the media type format of the first image.
     *
     * @return The media type format of the first image
     */
    public Optional<MediaType> getFormatMediaType() {
        return getImages().get(0).getFormatMediaType();
    }

    /**
     * Gets the first image's service.
     *
     * @return The first image's service
     */
    public Optional<ImageInfoService> getService() {
        return getImages().get(0).getService();
    }

    /**
     * Adds service image(s) to the property.
     *
     * @param aImageArray An array of service images
     * @return The property
     */
    protected T addImage(final ServiceImage... aImageArray) {
        if (!Collections.addAll(getImages(), aImageArray)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_048, StringUtils.toString(aImageArray, ' '));
            throw new UnsupportedOperationException(message);
        }

        return (T) this;
    }

    /**
     * Returns a list of service images, used as the value of the property. This method serializes service properties.
     *
     * @return A list of service images
     */
    @JsonValue
    protected Object toList() {
        final List<ServiceImage> serviceImageList = getImages();

        if (serviceImageList.size() > 0) {
            final List<Object> listForJsonMapping = new ArrayList<>();

            for (final ServiceImage serviceImage : serviceImageList) {
                if (serviceImage.getService().isPresent() || hasWidthHeight(serviceImage)) {
                    listForJsonMapping.add(serviceImage);
                } else {
                    listForJsonMapping.add(serviceImage.getID());
                }
            }

            return listForJsonMapping;
        } else {
            return null;
        }
    }

    private boolean hasWidthHeight(final ServiceImage aImage) {
        return (aImage.getHeight() != 0) || (aImage.getWidth() != 0);
    }
}
