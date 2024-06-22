
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
 * An annotation used to associate a description with the target.
 */
public class DescribingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for describing annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DescribingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a describing annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public DescribingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the describing annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a describing annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the describing annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the describing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the describing annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the describing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public DescribingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a description annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTarget An annotation target
     */
    public DescribingAnnotation(final String aID, final Target aTarget) {
        super(aID, aTarget);
        setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }

    /**
     * Creates a describing annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private DescribingAnnotation() {
        super();
    }

    @Override
    public final DescribingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.DESCRIBING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, DescribingAnnotation.class.getSimpleName(), aMotivation));
        }

        return (DescribingAnnotation) super.setMotivation(Motivation.fromLabel(Purpose.DESCRIBING));
    }
}
