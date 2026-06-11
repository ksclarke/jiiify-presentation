
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Video content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.DURATION,
    JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class VideoContent extends AbstractContentResource<VideoContent> implements SpatialContentResource<VideoContent>,
        TemporalContentResource<VideoContent>, AnnotatedContentResource<VideoContent> {

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
     * @param aID A video content resource ID
     */
    public VideoContent(final String aID) {
        super(ResourceTypes.VIDEO, aID, false, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Creates a video content resource.
     *
     * @param aVideoContent A video content resource to copy
     */
    public VideoContent(final VideoContent aVideoContent) {
        this(aVideoContent.getID());

        myDuration = aVideoContent.myDuration;
        myHeight = aVideoContent.myHeight;
        myWidth = aVideoContent.myWidth;
        myLanguages = new ArrayList<>(aVideoContent.getLanguages());
        aVideoContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aVideoContent.getBehaviors());

        super.copyTo(aVideoContent);
    }

    /**
     * Creates a video content resource.
     */
    private VideoContent() {
        super(ResourceTypes.VIDEO, ResourceBehavior.class);
    }

    @Override
    public VideoContent copy() {
        return new VideoContent(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        final VideoContent other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (VideoContent) aObject;

        return Objects.equals(myHeight, other.myHeight) && Objects.equals(myWidth, other.myWidth) &&
                Objects.equals(myDuration, other.myDuration) && super.equals(other);
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myHeight, myWidth, myDuration);
    }

    @Override
    @JsonIgnore
    public final VideoContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final VideoContent setBehaviors(final List<Behavior> aBehaviorList) {
        final VideoContent videoContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            videoContent = super.setBehaviors(behaviorList);
        } else {
            videoContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return videoContent;
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
}
