
package info.freelibrary.iiif.presentation.v3.annotations;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ContentResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used to associate a reply with the target.
 */
public class ReplyingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for replying annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplyingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a replying annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public ReplyingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the replying annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a replying annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the replying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the replying annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the replying annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ReplyingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public ReplyingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTarget An annotation target
     */
    public ReplyingAnnotation(final String aID, final Target aTarget) {
        super(aID, aTarget);
        setMotivation(Motivation.from(Purpose.REPLYING));
    }

    /**
     * Creates a replying annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ReplyingAnnotation() {
        super();
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    @Override
    public boolean bodyHasChoice() {
        return super.bodyHasChoice();
    }

    /**
     * Gets the resources associated with this annotation.
     *
     * @return The resources associated with this annotation
     */
    @Override
    @JsonIgnore
    public List<ContentResource<?>> getBody() {
        return super.getBody();
    }

    /**
     * Gets the annotation ID.
     *
     * @return The annotation ID
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @Override
    @JsonGetter(JsonKeys.MOTIVATION)
    public Motivation getMotivation() {
        return super.getMotivation();
    }

    /**
     * Gets the annotation target.
     *
     * @return The annotation target
     */
    @Override
    @JsonSetter(JsonKeys.TARGET)
    public Target getTarget() {
        return super.getTarget();
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The time mode
     */
    @Override
    public Optional<TimeMode> getTimeMode() {
        return super.getTimeMode();
    }

    @Override
    public ReplyingAnnotation setBody(final ContentResource<?>... aResourceArray) {
        return (ReplyingAnnotation) super.setBody(aResourceArray);
    }

    @Override
    public ReplyingAnnotation setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    @Override
    public ReplyingAnnotation setChoice(final boolean aChoice) {
        return (ReplyingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public ReplyingAnnotation setID(final String aID) {
        return (ReplyingAnnotation) super.setID(aID);
    }

    @Override
    public final ReplyingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.REPLYING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, ReplyingAnnotation.class.getSimpleName(), aMotivation));
        }

        return (ReplyingAnnotation) super.setMotivation(Motivation.from(Purpose.REPLYING));
    }

    @Override
    public ReplyingAnnotation setTarget(final Target aTarget) {
        return (ReplyingAnnotation) super.setTarget(aTarget);
    }

    @Override
    public ReplyingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (ReplyingAnnotation) super.setTimeMode(aTimeMode);
    }
}
