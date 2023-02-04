
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
 * An annotation used to associate a bookmark with the target.
 */
public class BookmarkingAnnotation extends WebAnnotation<BookmarkingAnnotation>
        implements Annotation<BookmarkingAnnotation> {

    /**
     * The logger for bookmarking annotations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a bookmarking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the bookmarking annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a bookmarking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the bookmarking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the bookmarking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the bookmarking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the bookmarking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> BookmarkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public BookmarkingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTargetURI A target URI
     */
    public BookmarkingAnnotation(final String aID, final String aTargetURI) {
        super(aID, aTargetURI);
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public BookmarkingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), aMinter.getManifestID());
        setMotivation(Relationship.BOOKMARKING.toString());
    }

    /**
     * Creates a bookmarking annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private BookmarkingAnnotation() {
        super();
    }

    @Override
    public BookmarkingAnnotation setID(final String aID) {
        return (BookmarkingAnnotation) super.setID(aID);
    }

    @Override
    public final void setMotivation(final String aMotivation) {
        if (!Relationship.BOOKMARKING.toString().equalsIgnoreCase(aMotivation)) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, BookmarkingAnnotation.class.getSimpleName(), aMotivation));
        }

        super.setMotivation(Relationship.BOOKMARKING.toString());
    }

    @Override
    public BookmarkingAnnotation setChoice(final boolean aChoice) {
        return (BookmarkingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public BookmarkingAnnotation setBodies(final AnnotationBody<?>... aBody) {
        return (BookmarkingAnnotation) super.setBodies(aBody);
    }

    @Override
    public BookmarkingAnnotation setBodies(final List<AnnotationBody<?>> aBody) {
        return setBodies(aBody.toArray(new AnnotationBody[0]));
    }

    @Override
    public BookmarkingAnnotation setTarget(final URI aURI) {
        return (BookmarkingAnnotation) super.setTarget(aURI);
    }

    @Override
    public BookmarkingAnnotation setTarget(final String aURI) {
        return (BookmarkingAnnotation) super.setTarget(aURI);
    }

    @Override
    public BookmarkingAnnotation setTarget(final SpecificResource aSpecificResource) {
        return (BookmarkingAnnotation) super.setTarget(aSpecificResource);
    }

    @Override
    public BookmarkingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (BookmarkingAnnotation) super.setTimeMode(aTimeMode);
    }
}
