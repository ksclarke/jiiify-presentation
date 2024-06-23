
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Video content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.DURATION,
    JsonKeys.FORMAT, JsonKeys.LANGUAGE })
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class VideoContent extends AbstractContentResource<VideoContent> implements SpatialContentResource,
        TemporalContentResource, AnnotatedContentResource<VideoContent>, Resource<VideoContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "video";

    /** The video content's duration. */
    private float myDuration;

    /** The video content's height. */
    private int myHeight;

    /** The video content's width. */
    private int myWidth;

    /**
     * Creates a video content resource.
     *
     * @param aURI An video content resource ID
     */
    public VideoContent(final String aURI) {
        super(ResourceTypes.VIDEO, aURI, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Creates a video content resource.
     */
    private VideoContent() {
        super(ResourceTypes.VIDEO, ResourceBehavior.class);
    }

    /**
     * Gets the duration of the video content.
     *
     * @return The duration of the video content
     */
    @Override
    @JsonGetter(JsonKeys.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Gets the video's height.
     *
     * @return The video's height
     */
    @Override
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the video's width.
     *
     * @return The video's width
     */
    @Override
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    @Override
    @JsonIgnore
    public VideoContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public VideoContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
    }

    /**
     * Sets the duration of the video content. Duration must be positive and finite.
     *
     * @param aDuration A video content's duration
     * @return The video content
     */
    @Override
    @JsonSetter(JsonKeys.DURATION)
    public VideoContent setDuration(final Number aDuration) {
        myDuration = convertToFinitePositiveFloat(aDuration);
        return this;
    }

    /**
     * Sets the width and height of the video.
     *
     * @param aWidth A video width
     * @param aHeight A video height
     * @return This video content
     */
    @Override
    @JsonIgnore
    public VideoContent setWidthHeight(final int aWidth, final int aHeight) {
        setWidth(aWidth);
        setHeight(aHeight);

        return this;
    }

    /**
     * Sets the video height.
     *
     * @param aHeight The video's height
     * @return The video
     */
    @JsonSetter(JsonKeys.HEIGHT)
    private VideoContent setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Sets the video width.
     *
     * @param aWidth The video's width
     * @return The video
     */
    @JsonSetter(JsonKeys.WIDTH)
    private VideoContent setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Returns video content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of video content
     * @return The video content
     * @throws JsonParsingException If the supplied JSON string cannot be parsed into a video content resource
     */
    static VideoContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(VideoContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
