
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.ServiceImage;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

/**
 * A property that relies on a service.
 */
class ServiceProperty<T extends ServiceProperty<T>> {

    private List<ServiceImage> myImages;

    protected ServiceProperty() {
        super();
    }

    /**
     * Adds image to the property.
     *
     * @param aIdStringArray An array of URI IDs, in string form, for the image
     * @return The property
     */
    protected T addImage(final String... aIdStringArray) {
        for (final String id : aIdStringArray) {
            if (!getImages().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException();
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
            if (!getImages().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException();
            }
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aIdString The URI ID, in string form, for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    public T addImage(final String aIdString, final int aWidth, final int aHeight) {
        if (!getImages().add(new ServiceImage(aIdString, aWidth, aHeight))) {
            throw new UnsupportedOperationException();
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
        if (!getImages().add(new ServiceImage(aURI, aWidth, aHeight))) {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aIdString The URI ID, in string form, for the image
     * @param aService A service for the image
     * @return The property
     */
    public T addImage(final String aIdString, final ImageInfoService aService) {
        if (!getImages().add(new ServiceImage(aIdString, aService))) {
            throw new UnsupportedOperationException();
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
        if (!getImages().add(new ServiceImage(aID, aService))) {
            throw new UnsupportedOperationException();
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
     * @param aServiceImageArray An array of service images
     * @return The property
     */
    protected T addImage(final ServiceImage... aServiceImageArray) {
        if (!Collections.addAll(getImages(), aServiceImageArray)) {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    /**
     * Returns the value of the property. If there is just one image, a single image is returned; else, a list of
     * images.
     *
     * @return The value of the property
     */
    @JsonValue
    protected Object getValue() {
        final List<ServiceImage> list = getImages();

        if (list.size() == 1) {
            final ServiceImage serviceImage = list.get(0);
            final Optional<ImageInfoService> service = serviceImage.getService();

            if (service.isPresent() || hasWidthHeight(serviceImage)) {
                return serviceImage;
            } else {
                // Not going to output format and type if no width, height, or service
                return serviceImage.getID();
            }
        } else if (list.size() > 1) {
            final List<Object> objects = new ArrayList<>();

            for (final ServiceImage serviceImage : list) {
                if (serviceImage.getService().isPresent() || hasWidthHeight(serviceImage)) {
                    objects.add(serviceImage);
                } else {
                    objects.add(serviceImage.getID());
                }
            }

            return objects;
        } else {
            return null;
        }
    }

    private boolean hasWidthHeight(final ServiceImage aImage) {
        return (aImage.getHeight() != 0) || (aImage.getWidth() != 0);
    }
}
