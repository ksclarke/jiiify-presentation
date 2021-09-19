
package info.freelibrary.iiif.presentation.v2.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.v2.ServiceImage;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * A small image that represents an individual or organization associated with the resource it is attached to. This
 * could be the logo of the owning or hosting institution. The logo must be clearly rendered when the resource is
 * displayed or used, without cropping, rotating or otherwise distorting the image. It is recommended that a IIIF Image
 * API service be available for this image for manipulations such as resizing.
 */
public class Logo extends ServiceProperty<Logo> {

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final String... aID) {
        super(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final URI... aID) {
        super(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aURI A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Logo(final String aURI, final int aWidth, final int aHeight) {
        super(aURI, aWidth, aHeight);
    }

    /**
     * Creates a logo property.
     *
     * @param aID A URI image ID
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Logo(final URI aID, final int aWidth, final int aHeight) {
        super(aID, aWidth, aHeight);
    }

    /**
     * Creates a logo property.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final String aID, final ImageInfoService aService) {
        super(URI.create(aID), aService);
    }

    /**
     * Creates a logo property that can contain multiple images. If multiple images are used they should each be
     * different.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final URI aID, final ImageInfoService aService) {
        super(aID, aService);
    }

    @Override
    public Logo addImage(final String... aIdArray) {
        return (Logo) super.addImage(aIdArray);
    }

    @Override
    public Logo addImage(final URI... aIdArray) {
        return (Logo) super.addImage(aIdArray);
    }

    @Override
    public Logo addImage(final String aID, final int aWidth, final int aHeight) {
        return (Logo) super.addImage(aID, aWidth, aHeight);
    }

    @Override
    public Logo addImage(final URI aURI, final int aWidth, final int aHeight) {
        return (Logo) super.addImage(aURI, aWidth, aHeight);
    }

    @Override
    public Logo addImage(final String aID, final ImageInfoService aService) {
        return (Logo) super.addImage(aID, aService);
    }

    @Override
    public Logo addImage(final URI aID, final ImageInfoService aService) {
        return (Logo) super.addImage(aID, aService);
    }

    @Override
    public Logo addImage(final ServiceImage... aImageArray) {
        return (Logo) super.addImage(aImageArray);
    }

    @Override
    @JsonGetter(Constants.LOGO)
    protected Object getValue() {
        return super.getValue();
    }

}
