
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationSerializer;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import java.net.URI;
import java.util.List;

/**
 * An annotation used to associate a moderation with the target.
 */
@JsonSerialize(using = WebAnnotationSerializer.class)
@JsonDeserialize(using = WebAnnotationDeserializer.class)
public class ModeratingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for moderating annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ModeratingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a moderating annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public ModeratingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the moderating annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a moderating annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the moderating annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the moderating annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the moderating annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the moderating annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> ModeratingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied annotation ID and target list.
     *
     * @param aID An annotation ID
     * @param aTargetList A list of annotation targets
     */
    public ModeratingAnnotation(final String aID, final List<Target> aTargetList) {
        super(aID, aTargetList);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public ModeratingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a moderating annotation from the supplied annotation ID and an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An array of annotation targets
     */
    public ModeratingAnnotation(final String aID, final Target... aTargetArray) {
        super(aID, aTargetArray);
        setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }

    /**
     * Creates a linking annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ModeratingAnnotation() {
        super();
    }

    @Override
    public ModeratingAnnotation setBody(final ContentResource... aBody) {
        return (ModeratingAnnotation) super.setBody(aBody);
    }

    @Override
    public ModeratingAnnotation setBody(final List<ContentResource> aResourceList) {
        return (ModeratingAnnotation) super.setBody(aResourceList);
    }

    @Override
    public ModeratingAnnotation setChoice(final boolean aChoice) {
        return (ModeratingAnnotation) super.setChoice(aChoice);
    }

    @Override
    public ModeratingAnnotation setID(final String aID) {
        return (ModeratingAnnotation) super.setID(aID);
    }

    @Override
    public ModeratingAnnotation setLabel(final Label aLabel) {
        return (ModeratingAnnotation) super.setLabel(aLabel);
    }

    @Override
    public ModeratingAnnotation setTargets(final Target... aTargetArray) {
        return (ModeratingAnnotation) super.setTargets(aTargetArray);
    }

    @Override
    public ModeratingAnnotation setTargets(final List<Target> aTargetList) {
        return (ModeratingAnnotation) super.setTargets(aTargetList);
    }

    @Override
    public ModeratingAnnotation setStylesheet(final String aStylesheet) {
        return (ModeratingAnnotation) super.setStylesheet(aStylesheet);
    }

    @Override
    public ModeratingAnnotation setContexts(final List<URI> aContextList) {
        return (ModeratingAnnotation) super.setContexts(aContextList);
    }

    @Override
    public final ModeratingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.MODERATING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    ModeratingAnnotation.class.getSimpleName(), Purpose.MODERATING, aMotivation));
        }

        return (ModeratingAnnotation) super.setMotivation(Motivation.fromLabel(Purpose.MODERATING));
    }
}
