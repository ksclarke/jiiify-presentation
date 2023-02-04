
package info.freelibrary.iiif.presentation.v3.annotations;

import java.net.URI;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationBody;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used to associate a link with the target.
 */
public class LinkingAnnotation extends WebAnnotation<LinkingAnnotation> implements Annotation<LinkingAnnotation> {

    /**
     * The logger for tagging annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a linking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a linking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the linking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the linking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public LinkingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public LinkingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public LinkingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.LINKING.toString());
    }

    /**
     * Creates a linking annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private LinkingAnnotation() {
        super();
    }

    @Override
    public LinkingAnnotation setID(final String aID) {
        return (LinkingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.LINKING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, LinkingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.LINKING.toString());
    }

    @Override
    public LinkingAnnotation setChoice(final boolean aChoice) {
        return (LinkingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public LinkingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (LinkingAnnotation) super.setBodies(aBody);
    }

    @Override
    public LinkingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public LinkingAnnotation setTarget(final URI aURI) {
        return (LinkingAnnotation) super.setTarget(aURI);
    }

    @Override
    public LinkingAnnotation setTarget(final String aURI) {
        return (LinkingAnnotation) super.setTarget(aURI);
    }

    @Override
    public LinkingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (LinkingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public LinkingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (LinkingAnnotation) super.setTimeMode(aTimeMode);
    }
}
