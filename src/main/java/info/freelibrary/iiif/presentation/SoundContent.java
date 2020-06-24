
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.PartOf;
import info.freelibrary.iiif.presentation.properties.Provider;
import info.freelibrary.iiif.presentation.properties.Rendering;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Sound content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.DURATION, Constants.FORMAT,
    Constants.LANGUAGE })
public class SoundContent extends AbstractContentResource<SoundContent> implements Thumbnail, Resource<SoundContent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundContent.class, Constants.BUNDLE_NAME);

    private float myDuration;

    /**
     * Creates sound content with the supplied ID URI.
     *
     * @param aURI A sound content ID in string form
     */
    public SoundContent(final String aURI) {
        super(ResourceTypes.SOUND, aURI);
        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates sound content with the supplied ID URI.
     *
     * @param aURI A sound content ID
     */
    public SoundContent(final URI aURI) {
        super(ResourceTypes.SOUND, aURI);
        setMediaTypeFromExt(aURI.toString());
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND);
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public SoundContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public SoundContent setProviders(final List<Provider> aProviderList) {
        return (SoundContent) super.setProviders(aProviderList);
    }

    @Override
    public SoundContent clearBehaviors() {
        return (SoundContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public SoundContent setBehaviors(final Behavior... aBehaviorArray) {
        return (SoundContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public SoundContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (SoundContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public SoundContent addBehaviors(final Behavior... aBehaviorArray) {
        return (SoundContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public SoundContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (SoundContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public SoundContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (SoundContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public SoundContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (SoundContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public SoundContent setPartOfs(final PartOf... aPartOfArray) {
        return (SoundContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public SoundContent setPartOfs(final List<PartOf> aPartOfList) {
        return (SoundContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public SoundContent setRenderings(final Rendering... aRenderingArray) {
        return (SoundContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public SoundContent setRenderings(final List<Rendering> aRenderingList) {
        return (SoundContent) super.setRenderings(aRenderingList);
    }

    @Override
    public SoundContent setHomepages(final Homepage... aHomepageArray) {
        return (SoundContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public SoundContent setHomepages(final List<Homepage> aHomepageList) {
        return (SoundContent) super.setHomepages(aHomepageList);
    }

    @Override
    public SoundContent setThumbnails(final Thumbnail... aThumbnailArray) {
        return (SoundContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public SoundContent setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (SoundContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public SoundContent setID(final String aID) {
        return (SoundContent) super.setID(aID);
    }

    @Override
    public SoundContent setID(final URI aID) {
        return (SoundContent) super.setID(aID);
    }

    @Override
    public SoundContent setRights(final String... aRightsArray) {
        return (SoundContent) super.setRights(aRightsArray);
    }

    @Override
    public SoundContent setRights(final URI... aRightsArray) {
        return (SoundContent) super.setRights(aRightsArray);
    }

    @Override
    public SoundContent setRights(final List<URI> aRightsList) {
        return (SoundContent) super.setRights(aRightsList);
    }

    @Override
    public SoundContent setRequiredStatement(final RequiredStatement aStatement) {
        return (SoundContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public SoundContent setSummary(final String aSummary) {
        return (SoundContent) super.setSummary(aSummary);
    }

    @Override
    public SoundContent setSummary(final Summary aSummary) {
        return (SoundContent) super.setSummary(aSummary);
    }

    @Override
    public SoundContent setMetadata(final Metadata aMetadata) {
        return (SoundContent) super.setMetadata(aMetadata);
    }

    @Override
    public SoundContent setLabel(final String aLabel) {
        return (SoundContent) super.setLabel(aLabel);
    }

    @Override
    public SoundContent setLabel(final Label aLabel) {
        return (SoundContent) super.setLabel(aLabel);
    }

    /**
     * Gets the duration of the sound content.
     *
     * @return The duration of the sound content
     */
    @JsonGetter(Constants.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Sets the duration of the sound content. Duration must be positive and finite.
     *
     * @param aDuration A sound content duration
     * @return The sound content
     */
    @JsonSetter(Constants.DURATION)
    public SoundContent setDuration(final float aDuration) {
        if ((aDuration > 0) && (Float.isFinite(aDuration))) {
            myDuration = aDuration;
            return this;
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_024, aDuration));
        }
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonObject A sound content resource in JSON form
     * @return The sound content
     */
    public static SoundContent fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), SoundContent.class);
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonString A sound content resource in string form
     * @return The sound content
     */
    public static SoundContent fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

}
