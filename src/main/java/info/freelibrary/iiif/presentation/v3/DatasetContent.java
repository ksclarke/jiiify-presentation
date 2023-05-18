
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
 * Dataset content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class DatasetContent extends AbstractContentResource<DatasetContent>
        implements ContentResource<DatasetContent>, Resource<DatasetContent> {

    /**
     * Creates a dataset content resource from the supplied ID.
     *
     * @param aID A dataset content ID
     */
    public DatasetContent(final String aID) {
        super(ResourceTypes.DATASET, aID, ResourceBehavior.class);
    }

    /**
     * Creates a dataset content resource. This is used by Jackson for its deserialization processes.
     */
    private DatasetContent() {
        super(ResourceTypes.DATASET, ResourceBehavior.class);
    }

    @Override
    public DatasetContent setFormat(final MediaType aMediaType) {
        return (DatasetContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public DatasetContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public DatasetContent setProviders(final List<Provider> aProviderList) {
        return (DatasetContent) super.setProviders(aProviderList);
    }

    @Override
    @JsonIgnore
    public DatasetContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public DatasetContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (DatasetContent) super.setBehaviors(aBehaviorList);
    }

    @Override
    public DatasetContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (DatasetContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public DatasetContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (DatasetContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    @SafeVarargs
    public final DatasetContent setServices(final Service<?>... aServiceArray) {
        return (DatasetContent) super.setServices(aServiceArray);
    }

    @Override
    public DatasetContent setServices(final List<Service<?>> aServiceList) {
        return (DatasetContent) super.setServices(aServiceList);
    }

    @Override
    public DatasetContent setPartOfs(final PartOf... aPartOfArray) {
        return (DatasetContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public DatasetContent setPartOfs(final List<PartOf> aPartOfList) {
        return (DatasetContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public DatasetContent setRenderings(final Rendering... aRenderingArray) {
        return (DatasetContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public DatasetContent setRenderings(final List<Rendering> aRenderingList) {
        return (DatasetContent) super.setRenderings(aRenderingList);
    }

    @Override
    public DatasetContent setHomepages(final Homepage... aHomepageArray) {
        return (DatasetContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public DatasetContent setHomepages(final List<Homepage> aHomepageList) {
        return (DatasetContent) super.setHomepages(aHomepageList);
    }

    @Override
    public DatasetContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (DatasetContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public DatasetContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (DatasetContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public DatasetContent setID(final String aID) {
        return (DatasetContent) super.setID(aID);
    }

    @Override
    public DatasetContent setRights(final String aRights) {
        return (DatasetContent) super.setRights(aRights);
    }

    @Override
    public DatasetContent setRequiredStatement(final RequiredStatement aStatement) {
        return (DatasetContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public DatasetContent setSummary(final Summary aSummary) {
        return (DatasetContent) super.setSummary(aSummary);
    }

    @Override
    public DatasetContent setMetadata(final Metadata... aMetadataArray) {
        return (DatasetContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public DatasetContent setMetadata(final List<Metadata> aMetadataList) {
        return (DatasetContent) super.setMetadata(aMetadataList);
    }

    @Override
    public DatasetContent setLabel(final Label aLabel) {
        return (DatasetContent) super.setLabel(aLabel);
    }

    @Override
    public DatasetContent setLanguages(final String... aLangArray) {
        return (DatasetContent) super.setLanguages(aLangArray);
    }

    /**
     * Returns dataset content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a dataset content resource
     * @return The dataset content
     * @throws JsonParsingException If the dataset content cannot be deserialized from the supplied JSON
     */
    static DatasetContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(DatasetContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
