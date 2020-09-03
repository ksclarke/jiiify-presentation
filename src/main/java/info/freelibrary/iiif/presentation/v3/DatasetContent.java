
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import info.freelibrary.iiif.presentation.v3.services.Service;

/**
 * Dataset content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.FORMAT, Constants.LANGUAGE })
public class DatasetContent extends AbstractContentResource<DatasetContent> implements Thumbnail,
        Resource<DatasetContent> {

    /**
     * Creates a dataset content resource.
     *
     * @param aID An dataset content resource ID in string form
     */
    public DatasetContent(final String aID) {
        super(ResourceTypes.DATASET, aID);
    }

    /**
     * Creates a dataset content resource.
     *
     * @param aID An dataset content resource ID
     */
    public DatasetContent(final URI aID) {
        super(ResourceTypes.DATASET, aID);
    }

    /**
     * Creates a dataset content resource.
     */
    private DatasetContent() {
        super(ResourceTypes.DATASET);
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public DatasetContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public DatasetContent setProviders(final List<Provider> aProviderList) {
        return (DatasetContent) super.setProviders(aProviderList);
    }

    @Override
    public DatasetContent clearBehaviors() {
        return (DatasetContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public DatasetContent setBehaviors(final Behavior... aBehaviorArray) {
        return (DatasetContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public DatasetContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (DatasetContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public DatasetContent addBehaviors(final Behavior... aBehaviorArray) {
        return (DatasetContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public DatasetContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (DatasetContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
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
    public DatasetContent setServices(final Service... aServiceArray) {
        return (DatasetContent) super.setServices(aServiceArray);
    }

    @Override
    public DatasetContent setServices(final List<Service> aServiceList) {
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
    public DatasetContent setThumbnails(final Thumbnail... aThumbnailArray) {
        return (DatasetContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public DatasetContent setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (DatasetContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public DatasetContent setID(final String aID) {
        return (DatasetContent) super.setID(aID);
    }

    @Override
    public DatasetContent setID(final URI aID) {
        return (DatasetContent) super.setID(aID);
    }

    @Override
    public DatasetContent setRights(final String aRights) {
        return (DatasetContent) super.setRights(aRights);
    }

    @Override
    public DatasetContent setRights(final URI aRights) {
        return (DatasetContent) super.setRights(aRights);
    }

    @Override
    public DatasetContent setRequiredStatement(final RequiredStatement aStatement) {
        return (DatasetContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public DatasetContent setSummary(final String aSummary) {
        return (DatasetContent) super.setSummary(aSummary);
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
    public DatasetContent setLabel(final String aLabel) {
        return (DatasetContent) super.setLabel(aLabel);
    }

    @Override
    public DatasetContent setLabel(final Label aLabel) {
        return (DatasetContent) super.setLabel(aLabel);
    }
}
