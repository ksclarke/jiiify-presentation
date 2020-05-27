
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class VideoContent extends AbstractContentResource<VideoContent> implements ContentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoContent.class, Constants.BUNDLE_NAME);

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
}
