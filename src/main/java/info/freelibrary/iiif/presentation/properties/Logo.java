
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A small image that represents an individual or organization associated with the resource it is attached to. This
 * could be the logo of the owning or hosting institution. The logo must be clearly rendered when the resource is
 * displayed or used, without cropping, rotating or otherwise distorting the image. It is recommended that a IIIF
 * Image API service be available for this image for manipulations such as resizing.
 */
public class Logo extends ServiceProperty<Logo> {

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final String... aID) {
        addImage(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final URI... aID) {
        addImage(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aURI A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Logo(final String aURI, final int aWidth, final int aHeight) {
        addImage(aURI, aWidth, aHeight);
    }

    /**
     * Creates a logo property.
     *
     * @param aID A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Logo(final URI aID, final int aWidth, final int aHeight) {
        addImage(aID, aWidth, aHeight);
    }

    /**
     * Creates a logo property.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final String aID, final ImageInfoService aService) {
        addImage(URI.create(aID), aService);
    }

    /**
     * Creates a logo property that can contain multiple images. If multiple images are used they should each be
     * different.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final URI aID, final ImageInfoService aService) {
        addImage(aID, aService);
    }

    @Override
    @JsonGetter(Constants.LOGO)
    protected Object getValue() {
        return super.getValue();
    }

}
