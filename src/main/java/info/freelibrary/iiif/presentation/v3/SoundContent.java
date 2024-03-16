
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Sound content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.DURATION,
    JsonKeys.LANGUAGE })
public class SoundContent extends AbstractContentResource<SoundContent> implements ContentResource<SoundContent>,
        TemporalContentResource<SoundContent>, Resource<SoundContent>, AnnotatedContent<SoundContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "audio";

    /** The sound content's duration. */
    private float myDuration;

    /**
     * Creates sound content with the supplied ID.
     *
     * @param aID A sound content ID
     */
    public SoundContent(final String aID) {
        super(ResourceTypes.SOUND, aID, ResourceBehavior.class);
        setFormat(MediaType.parse(aID, MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND, ResourceBehavior.class);
    }

    @Override
    public final SoundContent setFormat(final MediaType aMediaType) {
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
    @JsonIgnore
    public SoundContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public SoundContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (SoundContent) super.setBehaviors(aBehaviorList);
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
    @SafeVarargs
    public final SoundContent setServices(final Service<?>... aServiceArray) {
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
    public SoundContent setRights(final String aRights) {
        return (SoundContent) super.setRights(aRights);
    }

    @Override
    public SoundContent setRequiredStatement(final RequiredStatement aStatement) {
        return (SoundContent) super.setRequiredStatement(aStatement);
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
    public SoundContent setLabel(final Label aLabel) {
        return (SoundContent) super.setLabel(aLabel);
    }

    @Override
    public SoundContent setLanguages(final String... aLangArray) {
        return (SoundContent) super.setLanguages(aLangArray);
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

    @Override
    public List<AnnotationPage<WebAnnotation>> getAnnotations() {
        return super.getAnnotations();
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public SoundContent setAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray) {
        return (SoundContent) super.setAnnotations(aAnnotationArray);
    }

    @Override
    public SoundContent setAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationArray) {
        return (SoundContent) super.setAnnotations(aAnnotationArray);
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of sound content
     * @return The sound content
     * @throws JsonParsingException If there is trouble parsing the JSON
     */
    static SoundContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(SoundContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
