
package info.freelibrary.iiif.presentation.v3.annotations;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used to classify a target.
 */
public class ClassifyingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for classifying annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a classifying annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public ClassifyingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
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
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
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
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
    }

    /**
     * Creates a classifying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ClassifyingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
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
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
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
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
    }

    /**
     * Creates a classifying annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public ClassifyingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
    }

    /**
     * Creates a classifying annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTarget An annotation target
     */
    public ClassifyingAnnotation(final String aID, final Target aTarget) {
        super(aID, aTarget);
        setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
    }

    /**
     * Creates a classifying annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ClassifyingAnnotation() {
        super();
    }

    @Override
    public final ClassifyingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.CLASSIFYING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, ClassifyingAnnotation.class.getSimpleName(), aMotivation));
        }

        return (ClassifyingAnnotation) super.setMotivation(Motivation.fromLabel(Purpose.CLASSIFYING));
    }
}
