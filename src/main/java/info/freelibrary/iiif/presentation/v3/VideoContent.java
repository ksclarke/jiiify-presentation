
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Video content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.FORMAT, Constants.LANGUAGE })
public class VideoContent extends AbstractContentResource<VideoContent> implements Thumbnail, Resource<VideoContent>,
        SpatialContentResource<VideoContent>, TemporalContentResource<VideoContent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoContent.class, MessageCodes.BUNDLE);

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

    @Override
    @JsonSetter(Constants.PROVIDER)
    public VideoContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public VideoContent setProviders(final List<Provider> aProviderList) {
        return (VideoContent) super.setProviders(aProviderList);
    }

    @Override
    public VideoContent clearBehaviors() {
        return (VideoContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public VideoContent setBehaviors(final Behavior... aBehaviorArray) {
        return (VideoContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public VideoContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (VideoContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public VideoContent addBehaviors(final Behavior... aBehaviorArray) {
        return (VideoContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public VideoContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (VideoContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public VideoContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (VideoContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public VideoContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (VideoContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public VideoContent setPartOfs(final PartOf... aPartOfArray) {
        return (VideoContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public VideoContent setPartOfs(final List<PartOf> aPartOfList) {
        return (VideoContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public VideoContent setRenderings(final Rendering... aRenderingArray) {
        return (VideoContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public VideoContent setRenderings(final List<Rendering> aRenderingList) {
        return (VideoContent) super.setRenderings(aRenderingList);
    }

    @Override
    public VideoContent setHomepages(final Homepage... aHomepageArray) {
        return (VideoContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public VideoContent setHomepages(final List<Homepage> aHomepageList) {
        return (VideoContent) super.setHomepages(aHomepageList);
    }

    @Override
    public VideoContent setThumbnails(final Thumbnail... aThumbnailArray) {
        return (VideoContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public VideoContent setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (VideoContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public VideoContent setID(final String aID) {
        return (VideoContent) super.setID(aID);
    }

    @Override
    public VideoContent setID(final URI aID) {
        return (VideoContent) super.setID(aID);
    }

    @Override
    public VideoContent setRights(final String aRights) {
        return (VideoContent) super.setRights(aRights);
    }

    @Override
    public VideoContent setRights(final URI aRights) {
        return (VideoContent) super.setRights(aRights);
    }

    @Override
    public VideoContent setRequiredStatement(final RequiredStatement aStatement) {
        return (VideoContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public VideoContent setSummary(final String aSummary) {
        return (VideoContent) super.setSummary(aSummary);
    }

    @Override
    public VideoContent setSummary(final Summary aSummary) {
        return (VideoContent) super.setSummary(aSummary);
    }

    @Override
    public VideoContent setMetadata(final Metadata... aMetadataArray) {
        return (VideoContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public VideoContent setMetadata(final List<Metadata> aMetadataList) {
        return (VideoContent) super.setMetadata(aMetadataList);
    }

    @Override
    public VideoContent setLabel(final String aLabel) {
        return (VideoContent) super.setLabel(aLabel);
    }

    @Override
    public VideoContent setLabel(final Label aLabel) {
        return (VideoContent) super.setLabel(aLabel);
    }

    /**
     * Gets the video's width.
     *
     * @return The video's width
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    @JsonSetter(Constants.DURATION)
    public VideoContent setDuration(final Number aDuration) {
        final float tempDuration = aDuration.floatValue();
        if ((tempDuration > 0) && (Float.isFinite(tempDuration))) {
            myDuration = tempDuration;
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
