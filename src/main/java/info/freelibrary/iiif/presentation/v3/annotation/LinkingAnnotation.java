
package info.freelibrary.iiif.presentation.v3.annotation;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationSerializer;

/**
 * An annotation used to associate a link with the target.
 */
@JsonSerialize(using = WebAnnotationSerializer.class)
@JsonDeserialize(using = WebAnnotationDeserializer.class)
public class LinkingAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The logger for tagging annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a linking annotation using the supplied minter for the annotation ID and the manifest target.
     *
     * @param aMinter An ID minter for a manifest which will also be used as the target
     */
    public LinkingAnnotation(final Minter aMinter) {
        this(aMinter.getAnnotationID(), new Target(aMinter.getManifestID()));
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a linking annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the linking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the linking annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the linking annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> LinkingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied annotation ID and target list.
     *
     * @param aID An annotation ID
     * @param aTargetList A list of annotation targets
     */
    public LinkingAnnotation(final String aID, final List<Target> aTargetList) {
        super(aID, aTargetList);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied manifest using the supplied ID.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest target
     */
    public LinkingAnnotation(final String aID, final Manifest aManifest) {
        super(aID, aManifest);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation from the supplied annotation ID and an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An array of annotation targets
     */
    public LinkingAnnotation(final String aID, final Target... aTargetArray) {
        super(aID, aTargetArray);
        setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }

    /**
     * Creates a linking annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private LinkingAnnotation() {
        super();
    }

    @Override
    public final LinkingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.LINKING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    LinkingAnnotation.class.getSimpleName(), Purpose.LINKING, aMotivation));
        }

        return (LinkingAnnotation) super.setMotivation(Motivation.fromLabel(Purpose.LINKING));
    }
}
