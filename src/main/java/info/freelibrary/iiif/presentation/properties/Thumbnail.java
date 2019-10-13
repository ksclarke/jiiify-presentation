
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A thumbnail property.
 */
public class Thumbnail extends ServiceProperty<Thumbnail> {

    /**
     * Creates a thumbnail property.
     *
     * @param aID A thumbnail ID
     */
    public Thumbnail(final String... aID) {
        addImage(aID);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aID A thumbnail ID
     */
    public Thumbnail(final URI... aID) {
        addImage(aID);
    }

    /**
     * Creates a thumbnail property.
     *
     * @param aURI A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Thumbnail(final String aURI, final int aWidth, final int aHeight) {
        addImage(aURI, aWidth, aHeight);
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
     * @param aID An ID for the thumbnail image
     * @param aService A service for the thumbnail image
     */
    public Thumbnail(final String aID, final ImageInfoService aService) {
        addImage(URI.create(aID), aService);
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
