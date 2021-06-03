
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
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Video content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.DURATION,
    JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class VideoContent extends AbstractContentResource<VideoContent>
        implements AnnotationBody<VideoContent>, ContentResource<VideoContent>, SpatialContentResource<VideoContent>,
        TemporalContentResource<VideoContent>, Resource<VideoContent> {

    /**
     * The class of media type this content represents.
     */
    private static final String MEDIA_TYPE_CLASS = "video";

    /**
     * The video content's duration.
     */
    private float myDuration;

    /**
     * The video content's width.
     */
    private int myWidth;

    /**
     * The video content's height.
     */
    private int myHeight;

    /**
     * Creates a video content resource.
     *
     * @param aURI An video content resource ID in string form
     */
    public VideoContent(final String aURI) {
        super(ResourceTypes.VIDEO, aURI);
        setFormatFromMediaType(MediaType.parse(URI.create(aURI), MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Creates a video content resource.
     *
     * @param aURI An video content resource ID
     */
    public VideoContent(final URI aURI) {
        super(ResourceTypes.VIDEO, aURI);
        setFormatFromMediaType(MediaType.parse(aURI, MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Creates a video content resource.
     */
    private VideoContent() {
        super(ResourceTypes.VIDEO);
    }

    @Override
    @JsonIgnore
    public VideoContent setFormat(final MediaType aMediaType) {
        return (VideoContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public VideoContent setFormat(final String aMediaType) {
        return (VideoContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
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
    @JsonSetter(JsonKeys.BEHAVIOR)
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
    public VideoContent setServices(final Service<?>... aServiceArray) {
        return (VideoContent) super.setServices(aServiceArray);
    }

    @Override
    public VideoContent setServices(final List<Service<?>> aServiceList) {
        return (VideoContent) super.setServices(aServiceList);
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
    public VideoContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (VideoContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public VideoContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
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
    @JsonGetter(JsonKeys.WIDTH)
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
    @JsonGetter(JsonKeys.HEIGHT)
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
    @JsonSetter(JsonKeys.WIDTH)
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
    @JsonSetter(JsonKeys.HEIGHT)
    private VideoContent setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

}
