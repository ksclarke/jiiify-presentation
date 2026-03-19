
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationSerializer;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import java.util.List;

/**
 * A content state annotation provides a way to refer to a IIIF Presentation API resource, or part of a resource, in a
 * compact format that can be used to initialize the view of that resource in any client. For more information, see the
 * <a href="https://iiif.io/api/content-state/1.0/">IIIF Content State API</a> specification.
 */
@JsonSerialize(using = WebAnnotationSerializer.class)
@JsonDeserialize(using = WebAnnotationDeserializer.class)
public class ContentStateAnnotation extends WebAnnotation implements Annotation<WebAnnotation> {

    /** The Content State annotation's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentStateAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        this(aID, new Target(aCanvas.getID()));
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.CONTENT_STATE));
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates a new content state annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aTargetList An annotation target list
     */
    public ContentStateAnnotation(final String aID, final List<Target> aTargetList) {
        super(aID, aTargetList);
        setMotivation(Motivation.fromLabel(Purpose.CONTENT_STATE));
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest to target
     */
    public ContentStateAnnotation(final String aID, final Manifest aManifest) {
        this(aID, new Target(aManifest.getID()));
    }

    /**
     * Creates a new content state annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An annotation target array
     */
    public ContentStateAnnotation(final String aID, final Target... aTargetArray) {
        super(aID, aTargetArray);
        setMotivation(Motivation.fromLabel(Purpose.CONTENT_STATE));
    }

    /**
     * Creates a content state annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ContentStateAnnotation() {
        super();
        setMotivation(Motivation.fromLabel(Purpose.CONTENT_STATE));
    }

    @Override
    public final ContentStateAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.CONTENT_STATE.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    ContentStateAnnotation.class.getSimpleName(), Purpose.CONTENT_STATE, aMotivation));
        }

        return (ContentStateAnnotation) super.setMotivation(aMotivation);
    }
}
