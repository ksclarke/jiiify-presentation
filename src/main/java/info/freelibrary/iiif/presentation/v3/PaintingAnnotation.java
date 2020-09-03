
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.id.Minter;
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
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used for painting content resources onto a {@link Canvas}.
 */
public class PaintingAnnotation extends Annotation<PaintingAnnotation> implements Resource<PaintingAnnotation>,
        ContentAnnotation<PaintingAnnotation> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaintingAnnotation.class, MessageCodes.BUNDLE);

    private static final String MOTIVATION = "painting";

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final URI aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID in string form
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        this(URI.create(aID), aCanvas);
    }

    /**
     * Creates a painting annotation, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final URI aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID in string form
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        this(URI.create(aID), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a painting annotation, using the supplied minter to create the painting annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final URI aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID in string form
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(URI.create(aID), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a painting annotation, using the supplied minter to create the painting annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     */
    @SuppressWarnings("unused")
    private PaintingAnnotation() {
        super();
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public PaintingAnnotation setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public PaintingAnnotation setProviders(final List<Provider> aProviderList) {
        return (PaintingAnnotation) super.setProviders(aProviderList);
    }

    @Override
    protected void setMotivation(final String aMotivation) {
        if (!MOTIVATION.equals(aMotivation)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038, MOTIVATION));
        }

        myMotivation = MOTIVATION;
    }

    @Override
    public PaintingAnnotation addBody(final ContentResource... aBody) {
        return (PaintingAnnotation) super.addBody(aBody);
    }

    @Override
    public PaintingAnnotation addBody(final List<ContentResource> aBody) {
        return addBody(aBody.toArray(new ContentResource[] {}));
    }

    @Override
    public PaintingAnnotation clearBody() {
        return (PaintingAnnotation) super.clearBody();
    }

    @Override
    public PaintingAnnotation setBody(final ContentResource... aBody) {
        return (PaintingAnnotation) super.setBody(aBody);
    }

    @Override
    public PaintingAnnotation setBody(final List<ContentResource> aBody) {
        return setBody(aBody.toArray(new ContentResource[] {}));
    }

    @Override
    public PaintingAnnotation setTarget(final URI aURI) {
        return (PaintingAnnotation) super.setTarget(aURI);
    }

    @Override
    public PaintingAnnotation setTarget(final String aURI) {
        return (PaintingAnnotation) super.setTarget(aURI);
    }

    @Override
    public PaintingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (PaintingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public PaintingAnnotation clearBehaviors() {
        return (PaintingAnnotation) super.clearBehaviors();
    }

    @Override
    public PaintingAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        return (PaintingAnnotation) super.setBehaviors(aBehaviorArray);
    }

    @Override
    public PaintingAnnotation setBehaviors(final List<Behavior> aBehaviorList) {
        return (PaintingAnnotation) super.setBehaviors(aBehaviorList);
    }

    @Override
    public PaintingAnnotation addBehaviors(final Behavior... aBehaviorArray) {
        return (PaintingAnnotation) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public PaintingAnnotation addBehaviors(final List<Behavior> aBehaviorList) {
        return (PaintingAnnotation) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public PaintingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (PaintingAnnotation) super.setTimeMode(aTimeMode);
    }

    @Override
    public PaintingAnnotation setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (PaintingAnnotation) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public PaintingAnnotation setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (PaintingAnnotation) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public PaintingAnnotation setServices(final Service... aServiceArray) {
        return (PaintingAnnotation) super.setServices(aServiceArray);
    }

    @Override
    public PaintingAnnotation setServices(final List<Service> aServiceList) {
        return (PaintingAnnotation) super.setServices(aServiceList);
    }

    @Override
    public PaintingAnnotation setPartOfs(final PartOf... aPartOfArray) {
        return (PaintingAnnotation) super.setPartOfs(aPartOfArray);
    }

    @Override
    public PaintingAnnotation setPartOfs(final List<PartOf> aPartOfList) {
        return (PaintingAnnotation) super.setPartOfs(aPartOfList);
    }

    @Override
    public PaintingAnnotation setRenderings(final Rendering... aRenderingArray) {
        return (PaintingAnnotation) super.setRenderings(aRenderingArray);
    }

    @Override
    public PaintingAnnotation setRenderings(final List<Rendering> aRenderingList) {
        return (PaintingAnnotation) super.setRenderings(aRenderingList);
    }

    @Override
    public PaintingAnnotation setHomepages(final Homepage... aHomepageArray) {
        return (PaintingAnnotation) super.setHomepages(aHomepageArray);
    }

    @Override
    public PaintingAnnotation setHomepages(final List<Homepage> aHomepageList) {
        return (PaintingAnnotation) super.setHomepages(aHomepageList);
    }

    @Override
    public PaintingAnnotation setThumbnails(final Thumbnail... aThumbnailArray) {
        return (PaintingAnnotation) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public PaintingAnnotation setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (PaintingAnnotation) super.setThumbnails(aThumbnailList);
    }

    @Override
    public PaintingAnnotation setID(final String aID) {
        return (PaintingAnnotation) super.setID(aID);
    }

    @Override
    public PaintingAnnotation setID(final URI aID) {
        return (PaintingAnnotation) super.setID(aID);
    }

    @Override
    public PaintingAnnotation setRights(final String aRights) {
        return (PaintingAnnotation) super.setRights(aRights);
    }

    @Override
    public PaintingAnnotation setRights(final URI aRights) {
        return (PaintingAnnotation) super.setRights(aRights);
    }

    @Override
    public PaintingAnnotation setRequiredStatement(final RequiredStatement aStatement) {
        return (PaintingAnnotation) super.setRequiredStatement(aStatement);
    }

    @Override
    public PaintingAnnotation setSummary(final String aSummary) {
        return (PaintingAnnotation) super.setSummary(aSummary);
    }

    @Override
    public PaintingAnnotation setSummary(final Summary aSummary) {
        return (PaintingAnnotation) super.setSummary(aSummary);
    }

    @Override
    public PaintingAnnotation setMetadata(final Metadata... aMetadataArray) {
        return (PaintingAnnotation) super.setMetadata(aMetadataArray);
    }

    @Override
    public PaintingAnnotation setMetadata(final List<Metadata> aMetadataList) {
        return (PaintingAnnotation) super.setMetadata(aMetadataList);
    }

    @Override
    public PaintingAnnotation setLabel(final String aLabel) {
        return (PaintingAnnotation) super.setLabel(aLabel);
    }

    @Override
    public PaintingAnnotation setLabel(final Label aLabel) {
        return (PaintingAnnotation) super.setLabel(aLabel);
    }
}
