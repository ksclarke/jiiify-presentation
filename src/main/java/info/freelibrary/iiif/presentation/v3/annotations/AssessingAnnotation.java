
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
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used to associate an assessment with the target.
 */
public class AssessingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for assessing annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AssessingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates an assessing annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public AssessingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the assessing annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates an assessing annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the assessing annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the assessing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the assessing annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the assessing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> AssessingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public AssessingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates an assessing annotation from the supplied annotation ID and target URI.
     *
     * @param aID An annotation ID
     * @param aTarget A target
     */
    public AssessingAnnotation(final String aID, final Target aTarget) {
        super(aID, aTarget);
        setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    /**
     * Creates a bookmarking annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private AssessingAnnotation() {
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
     * Gets the optional annotation label.
     *
     * @return The optional annotation's label
     */
    @Override
    public Optional<Label> getLabel() {
        return super.getLabel();
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
    public AssessingAnnotation setBody(final ContentResource<?>... aResourceArray) {
        return (AssessingAnnotation) super.setBody(aResourceArray);
    }

    @Override
    public AssessingAnnotation setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    @Override
    public AssessingAnnotation setChoice(final boolean aChoice) {
        return (AssessingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public AssessingAnnotation setID(final String aID) {
        return (AssessingAnnotation) super.setID(aID);
    }

    /**
     * Sets the annotation label from its string form.
     *
     * @param aLabel A label to assign to the annotation
     * @return This annotation
     */
    @Override
    public AssessingAnnotation setLabel(final String aLabel) {
        return (AssessingAnnotation) super.setLabel(aLabel);
    }

    /**
     * Sets the annotation label.
     *
     * @param aLabel A label to assign to the annotation
     * @return This annotation
     */
    @Override
    public AssessingAnnotation setLabel(final Label aLabel) {
        return (AssessingAnnotation) super.setLabel(aLabel);
    }

    @Override
    public final AssessingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.ASSESSING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, AssessingAnnotation.class.getSimpleName(), aMotivation));
        }

        return (AssessingAnnotation) super.setMotivation(Motivation.from(Purpose.ASSESSING));
    }

    @Override
    public AssessingAnnotation setTarget(final Target aTarget) {
        return (AssessingAnnotation) super.setTarget(aTarget);
    }

    @Override
    public AssessingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return (AssessingAnnotation) super.setTimeMode(aTimeMode);
    }
}
