
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
 * An annotation used to identify a target.
 */
public class IdentifyingAnnotation extends WebAnnotation<IdentifyingAnnotation>
        implements Annotation<IdentifyingAnnotation> {

    /**
     * The logger for identifying annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifyingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates an identifying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates an identifying annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the identifying annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a identifying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates a identifying annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the identifying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the identifying annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates a identifying annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates a identifying annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the identifying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the identifying annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> IdentifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates an identifying annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public IdentifyingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates an identifying annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public IdentifyingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates an identifying annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public IdentifyingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.IDENTIFYING.toString());
    }

    /**
     * Creates a identifying annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private IdentifyingAnnotation() {
        super();
    }

    @Override
    public IdentifyingAnnotation setID(final String aID) {
        return (IdentifyingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.IDENTIFYING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, IdentifyingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.IDENTIFYING.toString());
    }

    @Override
    public IdentifyingAnnotation setChoice(final boolean aChoice) {
        return (IdentifyingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public IdentifyingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (IdentifyingAnnotation) super.setBodies(aBody);
    }

    @Override
    public IdentifyingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public IdentifyingAnnotation setTarget(final URI aURI) {
        return (IdentifyingAnnotation) super.setTarget(aURI);
    }

    @Override
    public IdentifyingAnnotation setTarget(final String aURI) {
        return (IdentifyingAnnotation) super.setTarget(aURI);
    }

    @Override
    public IdentifyingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (IdentifyingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public IdentifyingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (IdentifyingAnnotation) super.setTimeMode(aTimeMode);
    }
}
