
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.services.ImageInfoService;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Image content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.THUMBNAIL, Constants.WIDTH,
    Constants.HEIGHT, Constants.FORMAT, Constants.LANGUAGE, Constants.SERVICE })
public class ImageContent extends AbstractContentResource<ImageContent> implements Thumbnail {

    private Optional<ImageInfoService> myService;

    private int myWidth;

    private int myHeight;

    /**
     * Creates image content with the supplied ID.
     *
     * @param aURI An image content ID in string form
     */
    public ImageContent(final String aURI) {
        super(ResourceTypes.IMAGE, aURI);
        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates image content with the supplied ID.
     *
     * @param aURI An image content ID
     */
    public ImageContent(final URI aURI) {
        super(ResourceTypes.IMAGE, aURI);
        setMediaTypeFromExt(aURI.toString());
    }

    /**
     * Constructs an image content resource for Jackson's deserialization process.
     */
    private ImageContent() {
        super(ResourceTypes.IMAGE);
    }

    /**
     * Sets the width and height of the image.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     * @return This image content
     */
    @JsonIgnore
    public ImageContent setWidthHeight(final int aWidth, final int aHeight) {
        setWidth(aWidth);
        setHeight(aHeight);

        return this;
    }

    /**
     * Gets the image's width.
     *
     * @return The image's width
     */
    @JsonGetter(Constants.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the image's height.
     *
     * @return The image's height
     */
    @JsonGetter(Constants.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the image's associated service.
     *
     * @return The image's associated service
     */
    @JsonGetter(Constants.SERVICE)
    public Optional<ImageInfoService> getService() {
        return myService == null || myService.isEmpty() ? Optional.empty() : myService;
    }

    /**
     * Sets the image's associated service
     *
     * @param aService The image's associated service
     * @return The image content
     */
    @JsonSetter(Constants.SERVICE)
    public ImageContent setService(final ImageInfoService aService) {
        myService = Optional.ofNullable(aService);
        return this;
    }

    /**
     * Returns image content from its JSON representation.
     *
     * @param aJsonObject A image content resource in JSON form
     * @return The image content
     */
    public static ImageContent fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), ImageContent.class);
    }

    /**
     * Returns image content from its JSON representation.
     *
     * @param aJsonString A image content resource in string form
     * @return The image content
     */
    public static ImageContent fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

    /**
     * Sets the image width.
     *
     * @param aWidth The image's width
     * @return The image
     */
    @JsonSetter(Constants.WIDTH)
    private ImageContent setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the image height.
     *
     * @param aHeight The image's height
     * @return The image
     */
    @JsonSetter(Constants.HEIGHT)
    private ImageContent setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }
}
