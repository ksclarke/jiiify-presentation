
package info.freelibrary.iiif.presentation.v3.annotations; // NOPMD -- ExcessiveImports

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationBody;
import info.freelibrary.iiif.presentation.v3.AnnotationResource;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ContentResource;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
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
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used for associating supplementary content resources with a {@link Canvas}.
 */
public class SupplementingAnnotation extends AnnotationResource<SupplementingAnnotation>
        implements Resource<SupplementingAnnotation>, Annotation<SupplementingAnnotation> {

    /**
     * The logger that SupplementingAnnotation uses.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a supplementing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.SUPPLEMENTING.toString());
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter that's used to create the annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter,
            final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and media fragment selector.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.SUPPLEMENTING.toString());
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.SUPPLEMENTING.toString());
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the supplementing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SupplementingAnnotation() {
        super();
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public SupplementingAnnotation setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public SupplementingAnnotation setProviders(final List<Provider> aProviderList) {
        return (SupplementingAnnotation) super.setProviders(aProviderList);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.SUPPLEMENTING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    SupplementingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.SUPPLEMENTING.toString());
    }

    @Override
    public SupplementingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (SupplementingAnnotation) super.setBodies(aBody);
    }

    @Override
    public SupplementingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public SupplementingAnnotation setTarget(final URI aURI) {
        return (SupplementingAnnotation) super.setTarget(aURI);
    }

    @Override
    public SupplementingAnnotation setTarget(final String aURI) {
        return (SupplementingAnnotation) super.setTarget(aURI);
    }

    @Override
    public SupplementingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (SupplementingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public SupplementingAnnotation clearBehaviors() {
        return (SupplementingAnnotation) super.clearBehaviors();
    }

    @Override
    public SupplementingAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        // Checked in super.setBehaviors(Behavior...)
        return (SupplementingAnnotation) super.setBehaviors(aBehaviorArray);
    }

    @Override
    public SupplementingAnnotation setBehaviors(final List<Behavior> aBehaviorList) {
        // Checked in super.setBehaviors(List<Behavior>)
        return (SupplementingAnnotation) super.setBehaviors(aBehaviorList);
    }

    @Override
    public SupplementingAnnotation addBehaviors(final Behavior... aBehaviorArray) {
        // Checked in super.addBehaviors(Behavior...)
        return (SupplementingAnnotation) super.addBehaviors(aBehaviorArray);
    }

    @Override
    public SupplementingAnnotation addBehaviors(final List<Behavior> aBehaviorList) {
        // Checked in super.addBehaviors(List<Behavior>)
        return (SupplementingAnnotation) super.addBehaviors(aBehaviorList);
    }

    @Override
    public SupplementingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (SupplementingAnnotation) super.setTimeMode(aTimeMode);
    }

    @Override
    public SupplementingAnnotation setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (SupplementingAnnotation) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public SupplementingAnnotation setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (SupplementingAnnotation) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    @SafeVarargs
    public final SupplementingAnnotation setServices(final Service<?>... aServiceArray) {
        return (SupplementingAnnotation) super.setServices(aServiceArray);
    }

    @Override
    public SupplementingAnnotation setServices(final List<Service<?>> aServiceList) {
        return (SupplementingAnnotation) super.setServices(aServiceList);
    }

    @Override
    public SupplementingAnnotation setPartOfs(final PartOf... aPartOfArray) {
        return (SupplementingAnnotation) super.setPartOfs(aPartOfArray);
    }

    @Override
    public SupplementingAnnotation setPartOfs(final List<PartOf> aPartOfList) {
        return (SupplementingAnnotation) super.setPartOfs(aPartOfList);
    }

    @Override
    public SupplementingAnnotation setRenderings(final Rendering... aRenderingArray) {
        return (SupplementingAnnotation) super.setRenderings(aRenderingArray);
    }

    @Override
    public SupplementingAnnotation setRenderings(final List<Rendering> aRenderingList) {
        return (SupplementingAnnotation) super.setRenderings(aRenderingList);
    }

    @Override
    public SupplementingAnnotation setHomepages(final Homepage... aHomepageArray) {
        return (SupplementingAnnotation) super.setHomepages(aHomepageArray);
    }

    @Override
    public SupplementingAnnotation setHomepages(final List<Homepage> aHomepageList) {
        return (SupplementingAnnotation) super.setHomepages(aHomepageList);
    }

    @Override
    public SupplementingAnnotation setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (SupplementingAnnotation) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public SupplementingAnnotation setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (SupplementingAnnotation) super.setThumbnails(aThumbnailList);
    }

    @Override
    public SupplementingAnnotation setID(final String aID) {
        return (SupplementingAnnotation) super.setID(aID);
    }

    @Override
    public SupplementingAnnotation setRights(final String aRights) {
        return (SupplementingAnnotation) super.setRights(aRights);
    }

    @Override
    public SupplementingAnnotation setRequiredStatement(final RequiredStatement aStatement) {
        return (SupplementingAnnotation) super.setRequiredStatement(aStatement);
    }

    @Override
    public SupplementingAnnotation setSummary(final String aSummary) {
        return (SupplementingAnnotation) super.setSummary(aSummary);
    }

    @Override
    public SupplementingAnnotation setSummary(final Summary aSummary) {
        return (SupplementingAnnotation) super.setSummary(aSummary);
    }

    @Override
    public SupplementingAnnotation setMetadata(final Metadata... aMetadataArray) {
        return (SupplementingAnnotation) super.setMetadata(aMetadataArray);
    }

    @Override
    public SupplementingAnnotation setMetadata(final List<Metadata> aMetadataList) {
        return (SupplementingAnnotation) super.setMetadata(aMetadataList);
    }

    @Override
    public SupplementingAnnotation setLabel(final String aLabel) {
        return (SupplementingAnnotation) super.setLabel(aLabel);
    }

    @Override
    public SupplementingAnnotation setLabel(final Label aLabel) {
        return (SupplementingAnnotation) super.setLabel(aLabel);
    }
}
