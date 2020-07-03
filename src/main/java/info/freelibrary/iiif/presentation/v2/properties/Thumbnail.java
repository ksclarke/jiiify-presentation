
package info.freelibrary.iiif.presentation.v2.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.v2.ServiceImage;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * A small image that depicts or pictorially represents the resource that the property is attached to, such as the title
 * page, a significant image or rendering of a canvas with multiple content resources associated with it. It is
 * recommended that a IIIF Image API service be available for this image for manipulations such as resizing. If a
 * resource has multiple thumbnails, then each of them should be different.
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
    public Thumbnail addImage(final String... aIdArray) {
        return (Thumbnail) super.addImage(aIdArray);
    }

    @Override
    public Thumbnail addImage(final URI... aIdArray) {
        return (Thumbnail) super.addImage(aIdArray);
    }

    @Override
    public Thumbnail addImage(final String aID, final int aWidth, final int aHeight) {
        return (Thumbnail) super.addImage(aID, aWidth, aHeight);
    }

    @Override
    public Thumbnail addImage(final URI aURI, final int aWidth, final int aHeight) {
        return (Thumbnail) super.addImage(aURI, aWidth, aHeight);
    }

    @Override
    public Thumbnail addImage(final String aID, final ImageInfoService aService) {
        return (Thumbnail) super.addImage(aID, aService);
    }

    @Override
    public Thumbnail addImage(final URI aID, final ImageInfoService aService) {
        return (Thumbnail) super.addImage(aID, aService);
    }

    @Override
    public Thumbnail addImage(final ServiceImage... aImageArray) {
        return (Thumbnail) super.addImage(aImageArray);
    }

    @Override
    @JsonGetter(Constants.THUMBNAIL)
    protected Object getValue() {
        return super.getValue();
    }

}
