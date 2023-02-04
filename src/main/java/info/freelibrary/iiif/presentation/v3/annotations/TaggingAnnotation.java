
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
 * An annotation used to associate a tag with the target.
 */
public class TaggingAnnotation extends WebAnnotation<TaggingAnnotation> implements Annotation<TaggingAnnotation> {

    /**
     * The logger for tagging annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaggingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a tagging annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the tagging annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a tagging annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the tagging annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the tagging annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the tagging annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the tagging annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> TaggingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public TaggingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public TaggingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public TaggingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.TAGGING.toString());
    }

    /**
     * Creates a tagging annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private TaggingAnnotation() {
        super();
    }

    @Override
    public TaggingAnnotation setID(final String aID) {
        return (TaggingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.TAGGING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, TaggingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.TAGGING.toString());
    }

    @Override
    public TaggingAnnotation setChoice(final boolean aChoice) {
        return (TaggingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public TaggingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (TaggingAnnotation) super.setBodies(aBody);
    }

    @Override
    public TaggingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public TaggingAnnotation setTarget(final URI aURI) {
        return (TaggingAnnotation) super.setTarget(aURI);
    }

    @Override
    public TaggingAnnotation setTarget(final String aURI) {
        return (TaggingAnnotation) super.setTarget(aURI);
    }

    @Override
    public TaggingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (TaggingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public TaggingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (TaggingAnnotation) super.setTimeMode(aTimeMode);
    }
}
