
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A small image that depicts or pictorially represents the resource that the property is attached to, such as the
 * title page, a significant image or rendering of a canvas with multiple content resources associated with it. It is
 * recommended that a IIIF Image API service be available for this image for manipulations such as resizing. If a
 * resource has multiple thumbnails, then each of them should be different.
 */
public class Thumbnail extends ServiceProperty<Thumbnail> {

    /**
     * Creates a thumbnail property.
     *
     * @param aIdStringArray An array of thumbnail IDs in string form
     */
    public Thumbnail(final String... aIdStringArray) {
        addImage(aIdStringArray);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aIdArray An array of thumbnail IDs
     */
    public Thumbnail(final URI... aIdArray) {
        addImage(aIdArray);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aUriString A URI image ID in string form
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Thumbnail(final String aUriString, final int aWidth, final int aHeight) {
        addImage(aUriString, aWidth, aHeight);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aID A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Thumbnail(final URI aID, final int aWidth, final int aHeight) {
        addImage(aID, aWidth, aHeight);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aIdString An ID for the thumbnail image in string form
     * @param aService A service for the thumbnail image
     */
    public Thumbnail(final String aIdString, final ImageInfoService aService) {
        addImage(URI.create(aIdString), aService);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aID An ID for the thumbnail image
     * @param aService A service for the thumbnail image
     */
    public Thumbnail(final URI aID, final ImageInfoService aService) {
        addImage(aID, aService);
    }

    @Override
    @JsonGetter(Constants.THUMBNAIL)
    protected Object getValue() {
        return super.getValue();
    }

}
