
package info.freelibrary.iiif.presentation.v2.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v2.ServiceImage;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * A property that relies on a service.
 */
@SuppressWarnings("PMD.GodClass")
class ServiceProperty<T extends ServiceProperty<T>> {

    /**
     * A list of images for a service property.
     */
    private List<ServiceImage> myImages;

    /**
     * Creates a new service property.
     */
    protected ServiceProperty() {
        // This is intentionally empty
    }

    /**
     * Creates a new service property.
     *
     * @param aID An ID array
     */
    protected ServiceProperty(final String... aID) {
        final List<URI> ids = new ArrayList<>();

        for (final String id : aID) {
            ids.add(URI.create(id));
        }

        addImagePrivately(ids.toArray(new URI[0]));
    }

    /**
     * Creates a new service property.
     *
     * @param aID An ID array
     */
    protected ServiceProperty(final URI... aID) {
        addImagePrivately(aID);
    }

    /**
     * Creates a service property.
     *
     * @param aURI A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    protected ServiceProperty(final String aURI, final int aWidth, final int aHeight) {
        addImagePrivately(URI.create(aURI), aWidth, aHeight);
    }

    /**
     * Creates a service property.
     *
     * @param aID A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    protected ServiceProperty(final URI aID, final int aWidth, final int aHeight) {
        addImagePrivately(aID, aWidth, aHeight);
    }

    /**
     * Creates a service property.
     *
     * @param aID An ID for the service property
     * @param aService An image info service for the service property
     */
    protected ServiceProperty(final String aID, final ImageInfoService aService) {
        addImagePrivately(URI.create(aID), aService);
    }

    /**
     * Creates a service property.
     *
     * @param aID An ID for the service property
     * @param aService An image info service for the service property
     */
    protected ServiceProperty(final URI aID, final ImageInfoService aService) {
        addImagePrivately(aID, aService);
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @return The property
     */
    protected ServiceProperty<T> addImage(final String... aID) {
        final List<URI> ids = new ArrayList<>();

        for (final String id : aID) {
            ids.add(URI.create(id));
        }

        return addImagePrivately(ids.toArray(new URI[0]));
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @return The property
     */
    protected ServiceProperty<T> addImage(final URI... aID) {
        return addImagePrivately(aID);
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @return The property
     */
    private ServiceProperty<T> addImagePrivately(final URI... aID) {
        for (final URI id : aID) {
            if (!getImagesPrivately().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException();
            }
        }

        return this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    protected ServiceProperty<T> addImage(final String aID, final int aWidth, final int aHeight) {
        return addImagePrivately(URI.create(aID), aWidth, aHeight);
    }

    /**
     * Adds image to the property.
     *
     * @param aURI The ID for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    protected ServiceProperty<T> addImage(final URI aURI, final int aWidth, final int aHeight) {
        return addImagePrivately(aURI, aWidth, aHeight);
    }

    /**
     * Adds image to the property.
     *
     * @param aURI The ID for the image
     * @param aWidth A image width
     * @param aHeight A image height
     * @return The property
     */
    private ServiceProperty<T> addImagePrivately(final URI aURI, final int aWidth, final int aHeight) {
        if (!getImagesPrivately().add(new ServiceImage(aURI, aWidth, aHeight))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @param aService A service for the image
     * @return The property
     */
    protected ServiceProperty<T> addImage(final String aID, final ImageInfoService aService) {
        return addImagePrivately(URI.create(aID), aService);
    }

    /**
     * Adds image to the property.
     *
     * @param aID The ID for the image
     * @param aService A service for the image
     * @return The property
     */
    protected ServiceProperty<T> addImage(final URI aID, final ImageInfoService aService) {
        return addImagePrivately(aID, aService);
    }

    /**
     * Adds image to the property.
     *
     * @param aID The ID for the image
     * @param aService A service for the image
     * @return The property
     */
    private ServiceProperty<T> addImagePrivately(final URI aID, final ImageInfoService aService) {
        if (!getImagesPrivately().add(new ServiceImage(aID, aService))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Returns a list of images.
     *
     * @return A list of images
     */
    public List<ServiceImage> getImages() {
        return getImagesPrivately();
    }

    /**
     * Returns a list of images.
     *
     * @return A list of images
     */
    private List<ServiceImage> getImagesPrivately() {
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
     * Adds image(s) to the property.
     *
     * @param aImage A list of images
     * @return The property
     */
    protected ServiceProperty<T> addImage(final ServiceImage... aImage) {
        return addImagePrivately(aImage);
    }

    /**
     * Adds image(s) to the property.
     *
     * @param aImage A list of images
     * @return The property
     */
    private ServiceProperty<T> addImagePrivately(final ServiceImage... aImage) {
        if (!Collections.addAll(getImagesPrivately(), aImage)) {
            throw new UnsupportedOperationException();
        }

        return this;
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

        if (list.size() == Constants.SINGLE_INSTANCE) {
            final ServiceImage serviceImage = list.get(0);
            final Optional<ImageInfoService> service = serviceImage.getService();

            if (service.isPresent() || hasWidthHeight(serviceImage)) {
                return serviceImage;
            }

            // Not going to output format and type if no width, height, or service
            return serviceImage.getID();
        }

        if (list.size() <= Constants.SINGLE_INSTANCE) {
            return null;
        }

        final List<Object> objects = new ArrayList<>();

        for (final ServiceImage serviceImage : list) {
            if (serviceImage.getService().isPresent() || hasWidthHeight(serviceImage)) {
                objects.add(serviceImage);
            } else {
                objects.add(serviceImage.getID());
            }
        }

        return objects;
    }

    /**
     * Determines whether a service image has a width and height.
     *
     * @param aImage A service image to check
     * @return True if the supplied service image has a width and height; else, false
     */
    private boolean hasWidthHeight(final ServiceImage aImage) {
        return aImage.getHeight() != 0 || aImage.getWidth() != 0;
    }
}
