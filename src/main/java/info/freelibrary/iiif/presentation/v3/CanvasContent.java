
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

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
 * Canvas content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
public class CanvasContent extends AbstractContentResource<CanvasContent>
        implements ContentResource, Resource<CanvasContent> {

    /**
     * Creates a canvas content resource.
     *
     * @param aID A canvas content resource ID in string form
     */
    public CanvasContent(final String aID) {
        super(ResourceTypes.CANVAS, aID);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

    /**
     * Creates a canvas content resource.
     *
     * @param aID A canvas content resource ID
     */
    public CanvasContent(final URI aID) {
        super(ResourceTypes.CANVAS, aID);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

    /**
     * Creates a canvas content resource for Jackson's deserialization.
     */
    private CanvasContent() {
        super(ResourceTypes.CANVAS);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public CanvasContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public CanvasContent setProviders(final List<Provider> aProviderList) {
        return (CanvasContent) super.setProviders(aProviderList);
    }

    @Override
    public CanvasContent clearBehaviors() {
        return (CanvasContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public CanvasContent setBehaviors(final Behavior... aBehaviorArray) {
        return (CanvasContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public CanvasContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (CanvasContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public CanvasContent addBehaviors(final Behavior... aBehaviorArray) {
        return (CanvasContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public CanvasContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (CanvasContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public CanvasContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (CanvasContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public CanvasContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (CanvasContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public CanvasContent setServices(final Service... aServiceArray) {
        return (CanvasContent) super.setServices(aServiceArray);
    }

    @Override
    public CanvasContent setServices(final List<Service> aServiceList) {
        return (CanvasContent) super.setServices(aServiceList);
    }

    @Override
    public CanvasContent setPartOfs(final PartOf... aPartOfArray) {
        return (CanvasContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public CanvasContent setPartOfs(final List<PartOf> aPartOfList) {
        return (CanvasContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public CanvasContent setRenderings(final Rendering... aRenderingArray) {
        return (CanvasContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public CanvasContent setRenderings(final List<Rendering> aRenderingList) {
        return (CanvasContent) super.setRenderings(aRenderingList);
    }

    @Override
    public CanvasContent setHomepages(final Homepage... aHomepageArray) {
        return (CanvasContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public CanvasContent setHomepages(final List<Homepage> aHomepageList) {
        return (CanvasContent) super.setHomepages(aHomepageList);
    }

    @Override
    public CanvasContent setThumbnails(final Thumbnail... aThumbnailArray) {
        return (CanvasContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public CanvasContent setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (CanvasContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public CanvasContent setID(final String aID) {
        return (CanvasContent) super.setID(aID);
    }

    @Override
    public CanvasContent setID(final URI aID) {
        return (CanvasContent) super.setID(aID);
    }

    @Override
    public CanvasContent setRights(final String aRights) {
        return (CanvasContent) super.setRights(aRights);
    }

    @Override
    public CanvasContent setRights(final URI aRights) {
        return (CanvasContent) super.setRights(aRights);
    }

    @Override
    public CanvasContent setRequiredStatement(final RequiredStatement aStatement) {
        return (CanvasContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public CanvasContent clearRequiredStatement() {
        return (CanvasContent) super.clearRequiredStatement();
    }

    @Override
    public CanvasContent setSummary(final String aSummary) {
        return (CanvasContent) super.setSummary(aSummary);
    }

    @Override
    public CanvasContent setSummary(final Summary aSummary) {
        return (CanvasContent) super.setSummary(aSummary);
    }

    @Override
    public CanvasContent setMetadata(final Metadata... aMetadataArray) {
        return (CanvasContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public CanvasContent setMetadata(final List<Metadata> aMetadataList) {
        return (CanvasContent) super.setMetadata(aMetadataList);
    }

    @Override
    public CanvasContent setLabel(final String aLabel) {
        return (CanvasContent) super.setLabel(aLabel);
    }

    @Override
    public CanvasContent setLabel(final Label aLabel) {
        return (CanvasContent) super.setLabel(aLabel);
    }
}
