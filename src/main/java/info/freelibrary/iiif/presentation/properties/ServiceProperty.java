
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.services.ServiceImage;

/**
 * A property that relies on a service.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@SuppressWarnings("unchecked")
abstract class ServiceProperty<T extends ServiceProperty<T>> {

    private List<ServiceImage> myImages;

    protected ServiceProperty() {
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @return True if the image was added; else, false
     */
    public T addImage(final String... aID) {
        for (final String id : aID) {
            if (!getImages().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException();
            }
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @return True if the image was added; else, false
     */
    public T addImage(final URI... aID) {
        for (final URI id : aID) {
            if (!getImages().add(new ServiceImage(id))) {
                throw new UnsupportedOperationException();
            }
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The URI ID for the image
     * @param aService A service for the image
     * @return True if the image was added; else, false
     */
    public T addImage(final String aID, final ImageInfoService aService) {
        if (!getImages().add(new ServiceImage(aID, aService))) {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    /**
     * Adds image to the property.
     *
     * @param aID The ID for the image
     * @param aService A service for the image
     * @return True if the image was added; else, false
     */
    public T addImage(final URI aID, final ImageInfoService aService) {
        if (!getImages().add(new ServiceImage(aID, aService))) {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    /**
     * Adds image(s) to the property.
     *
     * @param aImage A list of images
     * @return True if the images were added to the property
     */
    public T addImage(final ServiceImage... aImage) {
        if (!Collections.addAll(getImages(), aImage)) {
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

            if (service.isPresent()) {
                return serviceImage;
            } else {
                return serviceImage.getID();
            }
        } else if (list.size() > 1) {
            final List<Object> objects = new ArrayList<>();

            for (final ServiceImage serviceImage : list) {
                if (serviceImage.getService().isPresent()) {
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

}
