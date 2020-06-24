
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * A collection of annotations.
 */
public class AnnotationCollection extends AbstractResource<AnnotationCollection> implements
        Resource<AnnotationCollection> {

    private ViewingDirection myViewingDirection;

    /**
     * Creates a collection of annotations.
     *
     * @param aID A collection ID in string form
     * @param aLabel A descriptive label, in string form, for the collection
     */
    public AnnotationCollection(final String aID, final String aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel);
    }

    /**
     * Creates a collection of annotations.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label for the collection
     */
    public AnnotationCollection(final URI aID, final Label aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel);
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public AnnotationCollection setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public AnnotationCollection setProviders(final List<Provider> aProviderList) {
        return (AnnotationCollection) super.setProviders(aProviderList);
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The annotation collection
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public AnnotationCollection setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    @Override
    public AnnotationCollection clearBehaviors() {
        return (AnnotationCollection) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AnnotationCollection setBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationCollection) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true,
                aBehaviorArray));
    }

    @Override
    public AnnotationCollection setBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationCollection) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public AnnotationCollection addBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationCollection) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false,
                aBehaviorArray));
    }

    @Override
    public AnnotationCollection addBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationCollection) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false,
                aBehaviorList));
    }

    @Override
    public AnnotationCollection setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AnnotationCollection) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AnnotationCollection setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AnnotationCollection) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AnnotationCollection setPartOfs(final PartOf... aPartOfArray) {
        return (AnnotationCollection) super.setPartOfs(aPartOfArray);
    }

    @Override
    public AnnotationCollection setPartOfs(final List<PartOf> aPartOfList) {
        return (AnnotationCollection) super.setPartOfs(aPartOfList);
    }

    @Override
    public AnnotationCollection setRenderings(final Rendering... aRenderingArray) {
        return (AnnotationCollection) super.setRenderings(aRenderingArray);
    }

    @Override
    public AnnotationCollection setRenderings(final List<Rendering> aRenderingList) {
        return (AnnotationCollection) super.setRenderings(aRenderingList);
    }

    @Override
    public AnnotationCollection setHomepages(final Homepage... aHomepageArray) {
        return (AnnotationCollection) super.setHomepages(aHomepageArray);
    }

    @Override
    public AnnotationCollection setHomepages(final List<Homepage> aHomepageList) {
        return (AnnotationCollection) super.setHomepages(aHomepageList);
    }

    @Override
    public AnnotationCollection setThumbnails(final Thumbnail... aThumbnailArray) {
        return (AnnotationCollection) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AnnotationCollection setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (AnnotationCollection) super.setThumbnails(aThumbnailList);
    }

    @Override
    public AnnotationCollection setID(final String aID) {
        return (AnnotationCollection) super.setID(aID);
    }

    @Override
    public AnnotationCollection setID(final URI aID) {
        return (AnnotationCollection) super.setID(aID);
    }

    @Override
    public AnnotationCollection setRights(final String... aRightsArray) {
        return (AnnotationCollection) super.setRights(aRightsArray);
    }

    @Override
    public AnnotationCollection setRights(final URI... aRightsArray) {
        return (AnnotationCollection) super.setRights(aRightsArray);
    }

    @Override
    public AnnotationCollection setRights(final List<URI> aRightsList) {
        return (AnnotationCollection) super.setRights(aRightsList);
    }

    @Override
    public AnnotationCollection setRequiredStatement(final RequiredStatement aStatement) {
        return (AnnotationCollection) super.setRequiredStatement(aStatement);
    }

    @Override
    public AnnotationCollection setSummary(final String aSummary) {
        return (AnnotationCollection) super.setSummary(aSummary);
    }

    @Override
    public AnnotationCollection setSummary(final Summary aSummary) {
        return (AnnotationCollection) super.setSummary(aSummary);
    }

    @Override
    public AnnotationCollection setMetadata(final Metadata aMetadata) {
        return (AnnotationCollection) super.setMetadata(aMetadata);
    }

    @Override
    public AnnotationCollection setLabel(final String aLabel) {
        return (AnnotationCollection) super.setLabel(aLabel);
    }

    @Override
    public AnnotationCollection setLabel(final Label aLabel) {
        return (AnnotationCollection) super.setLabel(aLabel);
    }
}
