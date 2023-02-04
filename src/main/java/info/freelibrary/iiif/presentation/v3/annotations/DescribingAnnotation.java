
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
 * An annotation used to associate a description with the target.
 */
public class DescribingAnnotation extends WebAnnotation<DescribingAnnotation>
        implements Annotation<DescribingAnnotation> {

    /**
     * The logger for describing annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DescribingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a describing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> DescribingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.DESCRIBING.toString());
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
        setMotivation(Relationship.DESCRIBING.toString());
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
        setMotivation(Relationship.DESCRIBING.toString());
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
        setMotivation(Relationship.DESCRIBING.toString());
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
        setMotivation(Relationship.DESCRIBING.toString());
    }

    /**
     * Creates a describing annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public DescribingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.DESCRIBING.toString());
    }

    /**
     * Creates a description annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public DescribingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.DESCRIBING.toString());
    }

    /**
     * Creates a describing annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public DescribingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.DESCRIBING.toString());
    }

    /**
     * Creates a describing annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private DescribingAnnotation() {
        super();
    }

    @Override
    public DescribingAnnotation setID(final String aID) {
        return (DescribingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.DESCRIBING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, DescribingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.DESCRIBING.toString());
    }

    @Override
    public DescribingAnnotation setChoice(final boolean aChoice) {
        return (DescribingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public DescribingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (DescribingAnnotation) super.setBodies(aBody);
    }

    @Override
    public DescribingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public DescribingAnnotation setTarget(final URI aURI) {
        return (DescribingAnnotation) super.setTarget(aURI);
    }

    @Override
    public DescribingAnnotation setTarget(final String aURI) {
        return (DescribingAnnotation) super.setTarget(aURI);
    }

    @Override
    public DescribingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (DescribingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public DescribingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (DescribingAnnotation) super.setTimeMode(aTimeMode);
    }
}
