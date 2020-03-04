
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An image resource that's used in {@link ImageContent}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.THUMBNAIL, Constants.WIDTH,
    Constants.HEIGHT, Constants.FORMAT, Constants.SERVICE })
@JsonInclude(Include.NON_EMPTY)
public class ImageResource extends ServiceImage {

    private Label myLabel;

    /**
     * Creates an image resource with the supplied ID URI.
     *
     * @param aURI An image resource ID in string form
     */
    public ImageResource(final String aURI) {
        super(aURI);
        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates an image resource with the supplied ID URI.
     *
     * @param aURI An image resource ID
     */
    public ImageResource(final URI aURI) {
        super(aURI);
        setMediaTypeFromExt(aURI.toString());
    }

    /**
     * Creates an image resource with the supplied ID URI and image info service.
     *
     * @param aURI An image resource ID in string form
     * @param aService An image info service
     */
    public ImageResource(final String aURI, final ImageInfoService aService) {
        super(aURI, aService);
        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates an image resource with the supplied ID URI and image info service.
     *
     * @param aURI An image resource ID
     * @param aService An image info service
     */
    public ImageResource(final URI aURI, final ImageInfoService aService) {
        super(aURI, aService);
        setMediaTypeFromExt(aURI.toString());
    }

    /**
     * Sets the format of the image resource.
     *
     * @param aMediaType A string representation of media type
     * @return The image resource
     */
    @Override
    @JsonSetter(Constants.FORMAT)
    public ImageResource setFormat(final String aMediaType) {
        return (ImageResource) super.setFormat(aMediaType);
    }

    /**
     * Sets the format of the image resource.
     *
     * @param aMediaType A media type
     * @return The image resource
     */
    @Override
    @JsonIgnore
    public ImageResource setFormatMediaType(final MediaType aMediaType) {
        return (ImageResource) super.setFormatMediaType(aMediaType);
    }

    /**
     * Sets the image resource width.
     *
     * @param aWidth The image resource width
     * @return The image resource
     */
    @Override
    @JsonSetter(Constants.WIDTH)
    public ImageResource setWidth(final int aWidth) {
        return (ImageResource) super.setWidth(aWidth);
    }

    /**
     * Sets the image resource height.
     *
     * @param aHeight The image resource height
     * @return The image resource
     */
    @Override
    @JsonSetter(Constants.HEIGHT)
    public ImageResource setHeight(final int aHeight) {
        return (ImageResource) super.setHeight(aHeight);
    }

    /**
     * Sets the image resource label.
     *
     * @param aLabel The image resource label in string form
     * @return The image resource
     */
    @JsonIgnore
    public ImageResource setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Sets the image resource label.
     *
     * @param aLabel The image resource label
     * @return The image resource
     */
    @JsonSetter(Constants.LABEL)
    public ImageResource setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets the image resource label.
     *
     * @return The image resource label
     */
    @JsonGetter(Constants.LABEL)
    public Label getLabel() {
        return myLabel;
    }

}
