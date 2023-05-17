
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A grouping of {@link AnnotationPage}(s) that should be managed together as a collection of {@link Annotation}(s).
 */
public class AnnotationCollection extends AbstractResource<AnnotationCollection>
        implements Resource<AnnotationCollection> {

    /** The collection's first AnnotationPage. */
    private AnnotationPage<?> myFirstAnnotationPage;

    /** The collection's last AnnotationPage. */
    private AnnotationPage<?> myLastAnnotationPage;

    /** The collection's viewingDirection. */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a collection of annotations from the supplied ID and label.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label for the collection
     */
    public AnnotationCollection(final String aID, final Label aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel, ResourceBehavior.class);
    }

    /**
     * Gets the collection's first annotation page.
     *
     * @return An optional annotation page
     */
    public Optional<AnnotationPage<?>> getFirstPage() {
        return Optional.ofNullable(myFirstAnnotationPage);
    }

    /**
     * Gets the collection's last annotation page.
     *
     * @return An optional annotation page
     */
    public Optional<AnnotationPage<?>> getLastPage() {
        return Optional.ofNullable(myLastAnnotationPage);
    }

    /**
     * Gets the viewing direction of the annotation collection.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    @Override
    @JsonIgnore
    public AnnotationCollection setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AnnotationCollection setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (AnnotationCollection) super.setBehaviors(aBehaviorList);
    }

    /**
     * Sets the collection's first annotation page.
     *
     * @param anAnnotationPage The first annotation page
     * @return This collection
     */
    public AnnotationCollection setFirstPage(final AnnotationPage<?> anAnnotationPage) {
        myFirstAnnotationPage = anAnnotationPage;
        return this;
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
    public AnnotationCollection setID(final String aID) {
        return (AnnotationCollection) super.setID(aID);
    }

    @Override
    public AnnotationCollection setLabel(final Label aLabel) {
        return (AnnotationCollection) super.setLabel(aLabel);
    }

    /**
     * Sets this collection's last annotation page.
     *
     * @param anAnnotationPage An annotation page
     * @return This collection
     */
    public AnnotationCollection setLastPage(final AnnotationPage<?> anAnnotationPage) {
        myLastAnnotationPage = anAnnotationPage;
        return this;
    }

    @Override
    public AnnotationCollection setMetadata(final List<Metadata> aMetadataList) {
        return (AnnotationCollection) super.setMetadata(aMetadataList);
    }

    @Override
    public AnnotationCollection setMetadata(final Metadata... aMetadataArray) {
        return (AnnotationCollection) super.setMetadata(aMetadataArray);
    }

    @Override
    public AnnotationCollection setPartOfs(final List<PartOf> aPartOfList) {
        return (AnnotationCollection) super.setPartOfs(aPartOfList);
    }

    @Override
    public AnnotationCollection setPartOfs(final PartOf... aPartOfArray) {
        return (AnnotationCollection) super.setPartOfs(aPartOfArray);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public AnnotationCollection setProviders(final List<Provider> aProviderList) {
        return (AnnotationCollection) super.setProviders(aProviderList);
    }

    @Override
    @JsonIgnore
    public AnnotationCollection setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public AnnotationCollection setRenderings(final List<Rendering> aRenderingList) {
        return (AnnotationCollection) super.setRenderings(aRenderingList);
    }

    @Override
    public AnnotationCollection setRenderings(final Rendering... aRenderingArray) {
        return (AnnotationCollection) super.setRenderings(aRenderingArray);
    }

    @Override
    public AnnotationCollection setRequiredStatement(final RequiredStatement aStatement) {
        return (AnnotationCollection) super.setRequiredStatement(aStatement);
    }

    @Override
    public AnnotationCollection setRights(final String aRights) {
        return (AnnotationCollection) super.setRights(aRights);
    }

    @Override
    public AnnotationCollection setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AnnotationCollection) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AnnotationCollection setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AnnotationCollection) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AnnotationCollection setServices(final List<Service<?>> aServiceList) {
        return (AnnotationCollection) super.setServices(aServiceList);
    }

    @Override
    public AnnotationCollection setServices(final Service<?>... aServiceArray) {
        return (AnnotationCollection) super.setServices(aServiceArray);
    }

    @Override
    public AnnotationCollection setSummary(final Summary aSummary) {
        return (AnnotationCollection) super.setSummary(aSummary);
    }

    @Override
    public AnnotationCollection setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (AnnotationCollection) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AnnotationCollection setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (AnnotationCollection) super.setThumbnails(aThumbnailList);
    }

    /**
     * Sets the viewing direction of the annotation collection.
     *
     * @param aViewingDirection A viewing direction
     * @return The annotation collection
     */
    @JsonSetter(JsonKeys.VIEWING_DIRECTION)
    public AnnotationCollection setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

}
