
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

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
 * Model content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class ModelContent extends AbstractContentResource<ModelContent>
        implements ContentResource<ModelContent>, Resource<ModelContent> {

    /**
     * Creates a model content resource from the supplied ID.
     *
     * @param aID An model content ID
     */
    public ModelContent(final String aID) {
        super(ResourceTypes.MODEL, aID, ResourceBehavior.class);
    }

    /**
     * Creates a model content annotation. This is used by Jackson's deserialization processes.
     */
    private ModelContent() {
        super(ResourceTypes.MODEL, ResourceBehavior.class);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public ModelContent setFormat(final MediaType aMediaType) {
        return (ModelContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public ModelContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public ModelContent setProviders(final List<Provider> aProviderList) {
        return (ModelContent) super.setProviders(aProviderList);
    }

    @Override
    @JsonIgnore
    public ModelContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public ModelContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (ModelContent) super.setBehaviors(aBehaviorList);
    }

    @Override
    public ModelContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (ModelContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public ModelContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (ModelContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    @SafeVarargs
    public final ModelContent setServices(final Service<?>... aServiceArray) {
        return (ModelContent) super.setServices(aServiceArray);
    }

    @Override
    public ModelContent setServices(final List<Service<?>> aServiceList) {
        return (ModelContent) super.setServices(aServiceList);
    }

    @Override
    public ModelContent setPartOfs(final PartOf... aPartOfArray) {
        return (ModelContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public ModelContent setPartOfs(final List<PartOf> aPartOfList) {
        return (ModelContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public ModelContent setRenderings(final Rendering... aRenderingArray) {
        return (ModelContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public ModelContent setRenderings(final List<Rendering> aRenderingList) {
        return (ModelContent) super.setRenderings(aRenderingList);
    }

    @Override
    public ModelContent setHomepages(final Homepage... aHomepageArray) {
        return (ModelContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public ModelContent setHomepages(final List<Homepage> aHomepageList) {
        return (ModelContent) super.setHomepages(aHomepageList);
    }

    @Override
    public ModelContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (ModelContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public ModelContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (ModelContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public ModelContent setID(final String aID) {
        return (ModelContent) super.setID(aID);
    }

    @Override
    public ModelContent setRights(final String aRights) {
        return (ModelContent) super.setRights(aRights);
    }

    @Override
    public ModelContent setRequiredStatement(final RequiredStatement aStatement) {
        return (ModelContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public ModelContent setSummary(final Summary aSummary) {
        return (ModelContent) super.setSummary(aSummary);
    }

    @Override
    public ModelContent setMetadata(final Metadata... aMetadataArray) {
        return (ModelContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public ModelContent setMetadata(final List<Metadata> aMetadataList) {
        return (ModelContent) super.setMetadata(aMetadataList);
    }

    @Override
    public ModelContent setLabel(final Label aLabel) {
        return (ModelContent) super.setLabel(aLabel);
    }

    @Override
    public ModelContent setLanguages(final String... aLangArray) {
        return (ModelContent) super.setLanguages(aLangArray);
    }

    /**
     * Returns model content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a model content resource
     * @return The model content
     * @throws JsonParsingException If the model content cannot be deserialized from the supplied JSON
     */
    static ModelContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(ModelContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
