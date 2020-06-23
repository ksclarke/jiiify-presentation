
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.PartOf;
import info.freelibrary.iiif.presentation.properties.Rendering;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Model content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.FORMAT, Constants.LANGUAGE })
public class ModelContent extends AbstractContentResource<ModelContent> implements Thumbnail, Resource<ModelContent> {

    /**
     * Creates a model content resource.
     *
     * @param aID An model content resource ID in string form
     */
    public ModelContent(final String aID) {
        super(ResourceTypes.MODEL, aID);
    }

    /**
     * Creates a model content resource.
     *
     * @param aID An model content ID
     */
    public ModelContent(final URI aID) {
        super(ResourceTypes.MODEL, aID);
    }

    /**
     * Creates a model content annotation.
     */
    private ModelContent() {
        super(ResourceTypes.MODEL);
    }

    @Override
    public ModelContent clearBehaviors() {
        return (ModelContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public ModelContent setBehaviors(final Behavior... aBehaviorArray) {
        return (ModelContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public ModelContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (ModelContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public ModelContent addBehaviors(final Behavior... aBehaviorArray) {
        return (ModelContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public ModelContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (ModelContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
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
    public ModelContent setThumbnails(final Thumbnail... aThumbnailArray) {
        return (ModelContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public ModelContent setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (ModelContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public ModelContent setID(final String aID) {
        return (ModelContent) super.setID(aID);
    }

    @Override
    public ModelContent setID(final URI aID) {
        return (ModelContent) super.setID(aID);
    }

    @Override
    public ModelContent setLogo(final String aLogo) {
        return (ModelContent) super.setLogo(aLogo);
    }

    @Override
    public ModelContent setLogo(final Logo aLogo) {
        return (ModelContent) super.setLogo(aLogo);
    }

    @Override
    public ModelContent setRights(final String... aRightsArray) {
        return (ModelContent) super.setRights(aRightsArray);
    }

    @Override
    public ModelContent setRights(final URI... aRightsArray) {
        return (ModelContent) super.setRights(aRightsArray);
    }

    @Override
    public ModelContent setRights(final List<URI> aRightsList) {
        return (ModelContent) super.setRights(aRightsList);
    }

    @Override
    public ModelContent setRequiredStatement(final RequiredStatement aStatement) {
        return (ModelContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public ModelContent setSummary(final String aSummary) {
        return (ModelContent) super.setSummary(aSummary);
    }

    @Override
    public ModelContent setSummary(final Summary aSummary) {
        return (ModelContent) super.setSummary(aSummary);
    }

    @Override
    public ModelContent setMetadata(final Metadata aMetadata) {
        return (ModelContent) super.setMetadata(aMetadata);
    }

    @Override
    public ModelContent setLabel(final String aLabel) {
        return (ModelContent) super.setLabel(aLabel);
    }

    @Override
    public ModelContent setLabel(final Label aLabel) {
        return (ModelContent) super.setLabel(aLabel);
    }
}
