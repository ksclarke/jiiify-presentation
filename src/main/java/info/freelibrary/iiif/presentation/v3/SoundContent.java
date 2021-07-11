
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
import com.fasterxml.jackson.core.JsonProcessingException;

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
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Sound content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.DURATION,
    JsonKeys.LANGUAGE })
public class SoundContent extends AbstractContentResource<SoundContent> implements AnnotationBody<SoundContent>,
        ContentResource<SoundContent>, TemporalContentResource<SoundContent>, Resource<SoundContent> {

    /**
     * The class of media type this content represents.
     */
    private static final String MEDIA_TYPE_CLASS = "audio";

    /**
     * The sound content's duration.
     */
    private float myDuration;

    /**
     * Creates sound content with the supplied ID.
     *
     * @param aURI A sound content ID in string form
     */
    public SoundContent(final String aURI) {
        super(ResourceTypes.SOUND, aURI);
        setFormatFromMediaType(MediaType.parse(URI.create(aURI), MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Creates sound content with the supplied ID.
     *
     * @param aURI A sound content ID
     */
    public SoundContent(final URI aURI) {
        super(ResourceTypes.SOUND, aURI);
        setFormatFromMediaType(MediaType.parse(aURI, MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND);
    }

    @Override
    @JsonIgnore
    public SoundContent setFormat(final MediaType aMediaType) {
        return (SoundContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public SoundContent setFormat(final String aMediaType) {
        return (SoundContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
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
    @JsonSetter(JsonKeys.BEHAVIOR)
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
    public SoundContent setServices(final Service<?>... aServiceArray) {
        return (SoundContent) super.setServices(aServiceArray);
    }

    @Override
    public SoundContent setServices(final List<Service<?>> aServiceList) {
        return (SoundContent) super.setServices(aServiceList);
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
    public SoundContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (SoundContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public SoundContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
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
    public SoundContent setRights(final String aRights) {
        return (SoundContent) super.setRights(aRights);
    }

    @Override
    public SoundContent setRights(final URI aRights) {
        return (SoundContent) super.setRights(aRights);
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
    public SoundContent setMetadata(final Metadata... aMetadataArray) {
        return (SoundContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public SoundContent setMetadata(final List<Metadata> aMetadataList) {
        return (SoundContent) super.setMetadata(aMetadataList);
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
    @Override
    @JsonGetter(JsonKeys.DURATION)
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
    @Override
    @JsonSetter(JsonKeys.DURATION)
    public SoundContent setDuration(final Number aDuration) {
        myDuration = convertToFinitePositiveFloat(aDuration);
        return this;
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonString A sound content resource in string form
     * @return The sound content
     */
    public static SoundContent from(final String aJsonString) {
        try {
            return JSON.getReader(SoundContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
