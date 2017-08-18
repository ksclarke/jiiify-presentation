
package info.freelibrary.iiif.presentation;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.helpers.Constants;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.services.ServiceImage;
import info.freelibrary.util.FileUtils;

/**
 * An image content resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.THUMBNAIL, Constants.WIDTH,
    Constants.HEIGHT, Constants.FORMAT, Constants.SERVICE })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImageResource extends ServiceImage {

    static final String TYPE = "dctypes:Image";

    private MediaType myFormat;

    private int myWidth;

    private int myHeight;

    private String myLabel;

    /**
     * Creates an image resource with the supplied ID URI.
     *
     * @param aURI An image resource ID
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
     * @param aURI An image resource ID
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
     * Gets the resource type.
     *
     * @return The resource type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return TYPE;
    }

    /**
     * Sets the format of the image resource.
     *
     * @param aMediaType A string representation of media type
     * @return The image resource
     */
    @JsonSetter(Constants.FORMAT)
    public ImageResource setFormat(final String aMediaType) {
        myFormat = MediaType.parse(aMediaType);
        return this;
    }

    /**
     * Sets the format of the image resource.
     *
     * @param aMediaType A media type
     * @return The image resource
     */
    @JsonIgnore
    public ImageResource setFormatMediaType(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the format of the image resource.
     *
     * @return A string representation of the format
     */
    @JsonGetter(Constants.FORMAT)
    public String getFormat() {
        return myFormat != null ? myFormat.toString() : null;
    }

    /**
     * Gets the media type format of the image resource.
     *
     * @return The media type format of the image resource
     */
    @JsonIgnore
    public MediaType getFormatMediaType() {
        return myFormat;
    }

    /**
     * Sets the image resource width.
     *
     * @param aWidth The image resource width
     * @return The image resource
     */
    @JsonSetter(Constants.WIDTH)
    public ImageResource setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the image resource height.
     *
     * @param aHeight The image resource height
     * @return The image resource
     */
    @JsonSetter(Constants.HEIGHT)
    public ImageResource setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Sets the image resource label.
     *
     * @param aLabel The image resource label
     * @return The image resource
     */
    @JsonSetter(Constants.LABEL)
    public ImageResource setLabel(final String aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets the image resource width.
     *
     * @return The image resource
     */
    @JsonGetter(Constants.WIDTH)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the image resource height.
     *
     * @return The image resource
     */
    @JsonGetter(Constants.HEIGHT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the image resource label.
     *
     * @return The image resource label
     */
    @JsonGetter(Constants.LABEL)
    public String getLabel() {
        return myLabel;
    }

    @JsonIgnore
    private void setMediaTypeFromExt(final String aURI) {
        String mimeType = null;

        try {
            mimeType = FileUtils.getMimeType(aURI);
        } catch (final IOException details) {
            throw new RuntimeException(details);
        }

        if (mimeType != null) {
            myFormat = MediaType.parse(mimeType);
        }
    }
}
