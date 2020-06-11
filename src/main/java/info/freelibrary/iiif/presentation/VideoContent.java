
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Video content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.FORMAT, Constants.LANGUAGE })
public class VideoContent extends AbstractContentResource<VideoContent> implements Thumbnail {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoContent.class, Constants.BUNDLE_NAME);

    private int myWidth;

    private int myHeight;

    private float myDuration;

    /**
     * Creates a video content resource.
     *
     * @param aID An video content resource ID in string form
     */
    public VideoContent(final String aID) {
        super(ResourceTypes.VIDEO, aID);
    }

    /**
     * Creates a video content resource.
     *
     * @param aID An video content resource ID
     */
    public VideoContent(final URI aID) {
        super(ResourceTypes.VIDEO, aID);
    }

    /**
     * Creates a video content resource.
     */
    private VideoContent() {
        super(ResourceTypes.VIDEO);
    }

    /**
     * Gets the video's width.
     *
     * @return The video's width
     */
    @JsonGetter(Constants.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the video's height.
     *
     * @return The video's height
     */
    @JsonGetter(Constants.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the width and height of the video.
     *
     * @param aWidth A video width
     * @param aHeight A video height
     * @return This video content
     */
    @JsonIgnore
    public VideoContent setWidthHeight(final int aWidth, final int aHeight) {
        setWidth(aWidth);
        setHeight(aHeight);

        return this;
    }

    /**
     * Gets the duration of the video content.
     *
     * @return The duration of the video content
     */
    @JsonGetter(Constants.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Sets the duration of the video content. Duration must be positive and finite.
     *
     * @param aDuration A video content's duration
     * @return The video content
     */
    @JsonSetter(Constants.DURATION)
    public VideoContent setDuration(final float aDuration) {
        if ((aDuration > 0) && (Float.isFinite(aDuration))) {
            myDuration = aDuration;
            return this;
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_024, aDuration));
        }
    }

    /**
     * Returns video content from its JSON representation.
     *
     * @param aJsonObject A video content resource in JSON form
     * @return The video content
     */
    public static VideoContent fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), VideoContent.class);
    }

    /**
     * Returns video content from its JSON representation.
     *
     * @param aJsonString A video content resource in string form
     * @return The video content
     */
    public static VideoContent fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

    /**
     * Sets the video width.
     *
     * @param aWidth The video's width
     * @return The video
     */
    @JsonSetter(Constants.WIDTH)
    private VideoContent setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the video height.
     *
     * @param aHeight The video's height
     * @return The video
     */
    @JsonSetter(Constants.HEIGHT)
    private VideoContent setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }
}
