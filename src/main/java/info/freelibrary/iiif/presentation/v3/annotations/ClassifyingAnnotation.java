
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
 * An annotation used to classify a target.
 */
public class ClassifyingAnnotation extends WebAnnotation<ClassifyingAnnotation>
        implements Annotation<ClassifyingAnnotation> {

    /**
     * The logger for classifying annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a classifying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the classifying annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a classifying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the classifying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the classifying annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the classifying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the classifying annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public ClassifyingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public ClassifyingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public ClassifyingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.CLASSIFYING.toString());
    }

    /**
     * Creates a classifying annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ClassifyingAnnotation() {
        super();
    }

    @Override
    public ClassifyingAnnotation setID(final String aID) {
        return (ClassifyingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.CLASSIFYING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, ClassifyingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.CLASSIFYING.toString());
    }

    @Override
    public ClassifyingAnnotation setChoice(final boolean aChoice) {
        return (ClassifyingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public ClassifyingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (ClassifyingAnnotation) super.setBodies(aBody);
    }

    @Override
    public ClassifyingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public ClassifyingAnnotation setTarget(final URI aURI) {
        return (ClassifyingAnnotation) super.setTarget(aURI);
    }

    @Override
    public ClassifyingAnnotation setTarget(final String aURI) {
        return (ClassifyingAnnotation) super.setTarget(aURI);
    }

    @Override
    public ClassifyingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (ClassifyingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public ClassifyingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (ClassifyingAnnotation) super.setTimeMode(aTimeMode);
    }
}
